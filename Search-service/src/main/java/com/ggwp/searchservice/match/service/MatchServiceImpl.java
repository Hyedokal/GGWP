package com.ggwp.searchservice.match.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.account.service.AccountService;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;
import com.ggwp.searchservice.common.exception.CustomException;
import com.ggwp.searchservice.common.exception.ErrorCode;
import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.league.dto.RequestLeagueDto;
import com.ggwp.searchservice.league.feign.LoLToLeagueFeign;
import com.ggwp.searchservice.league.repository.LeagueRepository;
import com.ggwp.searchservice.league.service.LeagueService;
import com.ggwp.searchservice.match.MatchRepository.MatchRepository;
import com.ggwp.searchservice.match.MatchRepository.MatchSummonerRepository;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.match.domain.MatchSummoner;
import com.ggwp.searchservice.match.dto.MatchDto;
import com.ggwp.searchservice.match.dto.MatchSummonerDto;
import com.ggwp.searchservice.match.feign.LoLToMatchFeign;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.dto.RequestSummonerDto;
import com.ggwp.searchservice.summoner.repository.SummonerRepository;
import com.ggwp.searchservice.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl {

    private final LoLToMatchFeign matchFeign;
    private final MatchRepository matchRepository;
    private final SummonerRepository summonerRepository;
    private final MatchSummonerRepository matchSummonerRepository;
    private final LoLToLeagueFeign leagueFeign;
    private final LeagueRepository leagueRepository;
    private final LeagueService leagueService;
    private final SummonerService summonerService;
    private final AccountService accountService;

    @Value("${LOL.apikey}")
    private String apiKey;

    public List<String> getMatchIdsToFeign(TokenDto tokenDto) {
        Account account = accountService.findAccount(tokenDto);
        Summoner summoner = summonerService.findSummoner(account);
        return matchFeign.getMatchIds(summoner.getPuuid(), apiKey).orElseThrow(() -> new CustomException(ErrorCode.NotFeignMatchIds));
    }

    public MatchDto getMatchToFegin(String matchId) {
        return matchFeign.getMatch(matchId, apiKey).orElseThrow(() -> new CustomException(ErrorCode.NotFeignMatch));
    }

    public ResponseDto<MatchDto> getMatch() // Match DB에서 조회
    {
        MatchDto matchDto = new MatchDto();
        return ResponseDto.success(matchDto);
    }
    public ResponseDto<String> createMatch(String matchId) throws InterruptedException {

        MatchDto matchdto = getMatchToFegin(matchId);

        Match match = matchdto.toEntity();


        // matchdto.toEntity();
        //
        // toDTo(); service에서 private로 처리한다.

        matchRepository.save(match);



        // 1. Match 연관관계 매핑
        // Team, MatchSummoner
        // MappedMatch
        // 2. feign으로 넘어온 matchDto 속 Summoner, Account 정보 추출

        // extractSummoner
        // extractAccount

        // 3. Summoner 연관 매핑
        // Account, League, MatchSummoner
        // CreateSummonerFromMatch
        // 4. Account 연관 매핑 및 저장
        // Summoner
        // CreateAccountFromSummoner

        // 5. MatchSummoner 저장
        // summoner와 Match

        if(matchdto.getInfo().getParticipants().isEmpty()){
            throw new CustomException(ErrorCode.NotFindParticipants);
        }
        else {
            List<MatchDto.ParticipantDto> participantDtoList = matchdto.getInfo().getParticipants();
            for (MatchDto.ParticipantDto participantDto : participantDtoList) {
                RequestSummonerDto createSummonerDto = participantDto.createSummonerDto();
                ResponseAccountDto requestAccountDto = participantDto.createAccountDto();

                Optional<Summoner> optionalSummoner = summonerRepository.findByPuuid(createSummonerDto.getPuuid());

                if (optionalSummoner.isPresent()) {
                    Summoner existingSummoner = optionalSummoner.get();
                    existingSummoner.updateSummoner(createSummonerDto);
                    summonerRepository.save(existingSummoner);
                } else {
                    // 소환사가 없으면 새로운 소환사 생성
                    Summoner newSummoner = createSummonerDto.toEntity();
                    summonerRepository.save(newSummoner);
                }


            }
        }






            if (!summonerRepository.existsByPuuid(summoner.getPuuid())) {
                // 중복되지 않을 경우에만 저장
                summonerRepository.save(summoner);

                List<RequestLeagueDto> leagueDtoList = leagueFeign.getLeagues(participantDto.getSummonerId(), apiKey);
                List<League> leagueList = new ArrayList<>();

                for (RequestLeagueDto requestCreateLeagueDto : leagueDtoList) {
                    // League 중복 확인
                    if (!leagueRepository.existsByQueueTypeAndSummoner_Puuid(requestCreateLeagueDto.getQueueType(), summoner.getPuuid())) {
                        League league = requestCreateLeagueDto.toEntity(summoner);

                        leagueRepository.save(league);
                        leagueList.add(league);
                    }
                }

                summoner.addLeagues(leagueList);
                Account account = requestAccountDto.toEntity(summoner);
                summoner.addAccount(account);

                MatchSummoner matchSummoner = MatchSummoner.builder()
                        .summoner(summoner)
                        .match(match)
                        .build();


                matchSummonerRepository.save(matchSummoner);
                summoner.addMatchSummoner(matchSummoner);
                summonerRepository.save(summoner);
                matchSummonerList.add(matchSummoner);
            }
        }

        match.addMatchSummoners(matchSummonerList);
        matchSummonerRepository.saveAll(matchSummonerList);


        Thread.sleep(1000);


        return ResponseDto.success("Match 생성 성공!");
    }

    public ResponseDto<String> createMatchs(TokenDto tokenDto) {

        List<String> matchIds = getMatchIdsToFeign(tokenDto);

        for (String matchId : matchIds) { // matchId 갯수만큼 반복  -> 일단 5개
            createMatch(matchId);
        }
    }


    // 소환사의 matchId들 matchSummoner에서 가져오기
    public List<MatchSummonerDto> getMatchSummonerBySummonerId(String summonerId) {
        List<MatchSummoner> matchSummoners = matchSummonerRepository.findBySummonerId(summonerId);

        List<MatchSummonerDto> matchSummonerDtoList = new ArrayList<>();

        for (MatchSummoner matchSummoner : matchSummoners) {
            MatchSummonerDto matchSummonerDto = MatchSummonerDto.toDto(matchSummoner);
            matchSummonerDtoList.add(matchSummonerDto);
        }
        return matchSummonerDtoList;
    }

    // 가져온 matchId로 전적 db에서 조회해서 가져오기
    public List<MatchDto> getMatchList(String summonerId) {
        List<MatchSummonerDto> matchSummonerDtoList = getMatchSummonerBySummonerId(summonerId);

        List<MatchDto> matchDtoList = new ArrayList<>();
        for (MatchSummonerDto matchSummonerDto : matchSummonerDtoList) {
            Match match = matchRepository.findByMatchId(matchSummonerDto.getMatchId());
            MatchDto matchDto = MatchDto.toDto(match);
            matchDtoList.add(matchDto);
        }
        return matchDtoList;
    }
}


