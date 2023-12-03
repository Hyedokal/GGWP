package com.ggwp.searchservice.match.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.dto.CreateAccountDto;
import com.ggwp.searchservice.account.service.AccountService;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;
import com.ggwp.searchservice.common.exception.CustomException;
import com.ggwp.searchservice.common.exception.ErrorCode;
import com.ggwp.searchservice.league.service.LeagueService;
import com.ggwp.searchservice.match.MatchRepository.MatchRepository;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.match.domain.MatchSummoner;
import com.ggwp.searchservice.match.domain.Participant;
import com.ggwp.searchservice.match.domain.Team;
import com.ggwp.searchservice.match.dto.MatchDto;
import com.ggwp.searchservice.match.dto.MatchSummonerDto;
import com.ggwp.searchservice.match.feign.LoLToMatchFeign;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.dto.CreateSummonerDto;
import com.ggwp.searchservice.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final LoLToMatchFeign matchFeign;
    private final MatchRepository matchRepository;

    private final LeagueService leagueService;
    private final AccountService accountService;
    private final MatchSummonerService matchSummonerService;
    private final SummonerService summonerService;

    @Value("${LOL.apikey}")
    private String apiKey;

    public List<String> getMatchIdsToFeign(TokenDto tokenDto) {

        String puuid = accountService.existAccount(tokenDto);
        // puuid를 이용하여 matchIds를 가져온다.
        return matchFeign.getMatchIds(puuid, apiKey)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFeignMatchIds));
    }

    public MatchDto getMatchToFegin(String matchId) { // Match를 롤 api에서 Feign
        return matchFeign.getMatch(matchId, apiKey).orElseThrow(() -> new CustomException(ErrorCode.NotFeignMatch));
    }

    public ResponseDto<String> createMatches(TokenDto tokenDto) throws InterruptedException {

        List<String> matchIds = getMatchIdsToFeign(tokenDto);

        for (String matchId : matchIds) { // matchId 갯수만큼 반복  -> 일단 5개
            createMatch(matchId);
            Thread.sleep(1000);
        }
        return ResponseDto.success("Match 전적 가져오기 성공!");
    }

    public Match matchToEntity(MatchDto matchDto) {

        MatchDto.MetadataDto metadataDto = matchDto.getMetadataDto();
        MatchDto.InfoDto info = matchDto.getInfo();

        Match match = Match.builder()
                .matchId(metadataDto.getMatchId())
                .platformId(info.getPlatformId())
                .queueId(info.getQueueId())
                .gameCreation(info.getGameCreation())
                .gameDuration(info.getGameDuration())
                .gameStartTimestamp(info.getGameStartTimestamp())
                .gameEndTimestamp(info.getGameEndTimestamp())
                .teams(new ArrayList<>())
                .build();

        List<Team> teamEntities = info.getTeams().stream()
                .map(teamDto -> {
                    Team team = teamDto.toEntity();

                    // 이 부분에서 Team에 Participant를 추가
                    List<Participant> participantEntities = info.getParticipants().stream()
                            .filter(participantDto -> participantDto.getTeamId() == team.getTeamId())
                            .map(participantDto -> {
                                Participant participant = participantDto.toEntity(team);
                                team.addParticipant(participant);
                                return participant;
                            })
                            .toList();
                    team.setMatch(match); // match 연관 매핑
                    return team;
                })
                .collect(Collectors.toList());

        match.addTeams(teamEntities); // team 연관 매핑

        return match;
    }

    public ResponseDto<String> createMatch(String matchId) {

        MatchDto matchdto = getMatchToFegin(matchId);

        Match match = matchToEntity(matchdto);

        matchRepository.save(match);

        // Participant에서 소환사, Account 값 추출해서 저장
        if (matchdto.getInfo().getParticipants().isEmpty()) {
            throw new CustomException(ErrorCode.NotFindParticipants);
        } else {
            List<MatchDto.ParticipantDto> participantDtoList = matchdto.getInfo().getParticipants();
            List<Summoner> summonerList = extractParticipantList(participantDtoList);

            List<MatchSummoner> matchSummonerList = new ArrayList<>();
            for (Summoner summoner : summonerList) {
                MatchSummoner matchSummoner = matchSummonerService.createMatchSummoner(match, summoner);
                matchSummonerList.add(matchSummoner);
            }

            match.addMatchSummoners(matchSummonerList);
        }
        return ResponseDto.success("match 생성!");
    }

    private CreateSummonerDto createSummonerDto(MatchDto.ParticipantDto participantDto) {
        return CreateSummonerDto.builder()
                .id(participantDto.getSummonerId())
                .profileIconId(participantDto.getProfileIcon())
                .name(participantDto.getSummonerName())
                .summonerLevel(participantDto.getSummmonerLevel())
                .puuid(participantDto.getPuuid())
                .revisionDate(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
                .build();
    }

    private CreateAccountDto createAccountDto(MatchDto.ParticipantDto participantDto) {
        return CreateAccountDto.builder()
                .puuid(participantDto.getPuuid())
                .gameName(participantDto.getSummonerName())
                .tagLine(participantDto.getRiotIdTagline())
                .build();
    }

    private Summoner summonertoEntity(CreateSummonerDto createSummonerDto) {
        return Summoner.builder()
                .id(createSummonerDto.getId())
                .profileIconId(createSummonerDto.getProfileIconId())
                .puuid(createSummonerDto.getPuuid())
                .name(createSummonerDto.getName())
                .revisionDate(createSummonerDto.getRevisionDate())
                .summonerLevel(createSummonerDto.getSummonerLevel())
                .build();
    }

    private List<Summoner> extractParticipantList(List<MatchDto.ParticipantDto> participantDtoList) {
        List<Summoner> summonerList = new ArrayList<>();

        for (MatchDto.ParticipantDto participantDto : participantDtoList) {
            CreateSummonerDto createSummonerDto = createSummonerDto(participantDto);
            CreateAccountDto createAccountDto = createAccountDto(participantDto);

            if (summonerService.existSummoner(createSummonerDto)) { // 소환사가 있으면 업데이트
                Summoner existingSummoner = summonerService.findSummonerByPuuid(createSummonerDto);
                existingSummoner.updateSummoner(createSummonerDto);
                summonerService.saveSummoner(existingSummoner);
                // updateLeagues
                leagueService.updateLeagues(existingSummoner);
                summonerList.add(existingSummoner);
            } else {
                // 소환사가 없으면 새로운 소환사 생성
                Summoner newSummoner = summonertoEntity(createSummonerDto);
                summonerService.saveSummoner(newSummoner);
                newSummoner.addAccount(accountService.createAccount(createAccountDto, newSummoner));
                newSummoner.addLeagues(leagueService.createLeague(newSummoner));
                summonerList.add(newSummoner);
            }
        }
        return summonerList;
    }

    // 가져온 matchId로 전적 db에서 조회해서 가져오기
    public List<MatchDto> getMatchList(TokenDto tokenDto) {
        Account account = accountService.findAccount(tokenDto);
        Summoner summoner = summonerService.findSummoner(account);
        List<MatchSummonerDto> matchSummonerDtoList = matchSummonerService.getMatchSummonerBySummonerId(summoner.getId());

        List<MatchDto> matchDtoList = new ArrayList<>();
        for (MatchSummonerDto matchSummonerDto : matchSummonerDtoList) {
            Match match = matchRepository.findByMatchId(matchSummonerDto.getMatchId());
            MatchDto matchDto = MatchDto.toDto(match);
            matchDtoList.add(matchDto);
        }
        return matchDtoList;
    }


}


