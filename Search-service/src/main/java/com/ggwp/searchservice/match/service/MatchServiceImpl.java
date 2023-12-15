package com.ggwp.searchservice.match.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.dto.CreateAccountDto;
import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.account.service.AccountService;
import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.common.enums.GameMode;
import com.ggwp.searchservice.common.exception.CustomException;
import com.ggwp.searchservice.common.exception.ErrorCode;
import com.ggwp.searchservice.league.service.LeagueService;
import com.ggwp.searchservice.match.MatchRepository.MatchRepository;
import com.ggwp.searchservice.match.domain.*;
import com.ggwp.searchservice.match.dto.MatchDto;
import com.ggwp.searchservice.match.dto.MatchSummonerDto;
import com.ggwp.searchservice.match.dto.RequestPageDto;
import com.ggwp.searchservice.match.feign.LoLToMatchFeign;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.dto.CreateSummonerDto;
import com.ggwp.searchservice.summoner.service.SummonerService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchServiceImpl implements MatchService {

    private final LoLToMatchFeign matchFeign;
    private final MatchRepository matchRepository;

    private final LeagueService leagueService;
    private final AccountService accountService;
    private final MatchSummonerService matchSummonerService;
    private final SummonerService summonerService;
    private final EntityManager entityManager;

    @Value("${LOL.apikey}")
    private String apiKey;

    @Override
    public void createMatches(FrontDto frontDto) { // 매치 갱신

        List<String> matchIds = getMatchIdsToFeign(frontDto); // Feign 1번

        for (String matchId : matchIds) {
            // matchId 갯수만큼 반복  -> 일단 5개
            if (!matchRepository.existsByMatchId(matchId)) {
                createMatch(matchId);
            }
        }
    }

    // 가져온 matchId로 전적 db에서 조회해서 가져오기 // matchDto 가져오기
    @Override
    @Transactional(readOnly = true)
    public List<MatchDto> getMatchList(FrontDto frontDto) {
        Account account = accountService.findAccount(frontDto);
        Summoner summoner = summonerService.findSummoner(account);
        List<MatchSummonerDto> matchSummonerDtoList = matchSummonerService.getMatchSummonerBySummonerId(summoner.getId());

        List<MatchDto> matchDtoList = new ArrayList<>();
        for (MatchSummonerDto matchSummonerDto : matchSummonerDtoList) {
            Match match = matchRepository.findByMatchId(matchSummonerDto.getMatchId());
            MatchDto matchDto = matchToDto(match);
            matchDtoList.add(matchDto);
        }
        return matchDtoList;
    }

    // queryDsl을 사용해서 5개 가져오기 프론트에서 페이지 처리를 해줘야함 지금은 0~5 가져오게 되어있고 이후에 가져오려면 page를 1로 줘야겠지요
    @Transactional(readOnly = true)
    public Page<MatchDto> pagedMatches(FrontDto frontDto, RequestPageDto.Search dto) {
        Account account = accountService.findAccount(frontDto);
        Summoner summoner = summonerService.findSummoner(account);

        // MatchSummoner 엔터티를 사용하여 소환사의 매치 ID 목록 가져오기
        List<MatchSummonerDto> matchSummonerDtoList = matchSummonerService.getMatchSummonerBySummonerId(summoner.getId());

        // 가져온 매치 ID 목록을 리스트로 변환
        List<String> matchIds = matchSummonerDtoList.stream()
                .map(MatchSummonerDto::getMatchId)
                .toList();

        QMatch qMatch = QMatch.match;

        int total = matchIds.size();


        long offset = (long) dto.getPage() * dto.getSize();

        List<Match> matches = query()
                .selectFrom(qMatch)
                .where(qMatch.matchId.in(matchIds))
                .orderBy(qMatch.gameEndTimestamp.desc())
                .offset(offset)
                .limit(dto.getSize())
                .fetch();

        List<MatchDto> dtoList = matches.stream()
                .map(this::matchToDto)
                .toList();

        return new PageImpl<>(dtoList, PageRequest.of(dto.getPage(), dto.getSize()), total);
    }

    private JPAQueryFactory query() {
        return new JPAQueryFactory(entityManager);
    }

    private List<String> getMatchIdsToFeign(FrontDto frontDto) {

        String puuid = accountService.existAccount(frontDto); // Feign 1번 (있으면 호출 안함)

        String summonerId = summonerService.summonerFeign(puuid, apiKey).getId();
        // 페인 써서 summoner 가져오고 summonerId로 리그 페인
        if (leagueService.existLeague(summonerId)) {
            leagueService.createLeague(summonerId);
        } else {
            leagueService.updateLeagues(summonerId);
        }
        // puuid를 이용하여 matchIds를 가져온다.
        return matchFeign.getMatchIds(puuid, apiKey)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFeignMatchIds));
    }

    // MatchId로 Match를 가져오는 Fegin
    private MatchDto getMatchToFegin(String matchId) { // Match를 롤 api에서 Feign
        return matchFeign.getMatch(matchId, apiKey).orElseThrow(() -> new CustomException(ErrorCode.NotFeignMatch));
    }

    // MatchDto를 Entity로 바꾸는 메소드
    public Match matchToEntity(MatchDto matchDto) {

        MatchDto.MetadataDto metadataDto = matchDto.getMetadataDto();
        MatchDto.InfoDto info = matchDto.getInfo();

        Match match = Match.builder()
                .matchId(metadataDto.getMatchId())
                .platformId(info.getPlatformId())
                .queueId(GameMode.getByQueueId(info.getQueueId()))
                .gameCreation(info.getGameCreation())
                .gameDuration(info.getGameDuration())
                .gameStartTimestamp(info.getGameStartTimestamp())
                .gameEndTimestamp(info.getGameEndTimestamp())
                .participants(new ArrayList<>())
                .teams(new ArrayList<>())
                .build();

        List<Team> teamEntities = info.getTeams().stream()
                .map(teamDto -> {
                    Team team = teamToEntity(teamDto);
                    List<Participant> participants = info.getParticipants().stream()
                            .filter(participantDto -> participantDto.getTeamId() == team.getTeamId())
                            .map(participantDto -> participantToEntity(participantDto, team, match))
                            .toList();
                    // 참가자 연관 매핑
                    team.addParticipants(participants);
                    match.addParticipants(participants);
                    // 팀과 매치 연관 매핑
                    team.setMatch(match);
                    return team;
                })
                .toList();

        match.addTeams(teamEntities); // team 연관 매핑

        return match;
    }

    public void createMatch(String matchId) {

        MatchDto matchdto = getMatchToFegin(matchId);

        Match match = matchToEntity(matchdto);

        matchRepository.save(match);
        // Participant에서 소환사, Account 값 추출해서 저장
        if (matchdto.getInfo().getParticipants() == null) {
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
    }

    private CreateSummonerDto createSummonerDto(MatchDto.ParticipantDto participantDto) { // 소환사를 생성하는 DTO
        return CreateSummonerDto.builder()
                .id(participantDto.getSummonerId())
                .profileIconId(participantDto.getProfileIcon())
                .name(participantDto.getSummonerName())
                .summonerLevel(participantDto.getSummmonerLevel())
                .puuid(participantDto.getPuuid())
                .revisionDate(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
                .build();
    }

    private CreateAccountDto createAccountDto(MatchDto.ParticipantDto participantDto) { // Account를 생성하는 DTO
        if (participantDto.getRiotIdName() == null) {
            return CreateAccountDto.builder()
                    .puuid(participantDto.getPuuid())
                    .gameName(participantDto.getSummonerName())
                    .tagLine(participantDto.getRiotIdTagline())
                    .build();
        } else {
            return CreateAccountDto.builder()
                    .puuid(participantDto.getPuuid())
                    .gameName(participantDto.getRiotIdName())
                    .tagLine(participantDto.getRiotIdTagline())
                    .build();
        }
    }

    private Summoner summonertoEntity(CreateSummonerDto createSummonerDto) { // SummonrDto를 엔티티로 바꾸는 메소드
        return Summoner.builder()
                .id(createSummonerDto.getId())
                .profileIconId(createSummonerDto.getProfileIconId())
                .puuid(createSummonerDto.getPuuid())
                .name(createSummonerDto.getName())
                .revisionDate(createSummonerDto.getRevisionDate())
                .summonerLevel(createSummonerDto.getSummonerLevel())
                .build();
    }

    private List<Summoner> extractParticipantList(List<MatchDto.ParticipantDto> participantDtoList) { //Participant를 추출하여 Summoner와 Account를 추출 하여 저장
        List<Summoner> summonerList = new ArrayList<>();

        for (MatchDto.ParticipantDto participantDto : participantDtoList) {
            CreateSummonerDto createSummonerDto = createSummonerDto(participantDto);
            CreateAccountDto createAccountDto = createAccountDto(participantDto);

            // 업데이트만 한다.
            if (summonerService.existSummoner(createSummonerDto)) { // 소환사가 있으면 업데이트
                Summoner existingSummoner = summonerService.findSummonerByPuuid(createSummonerDto);
                // updateLeagues // 소환사가 있으면 계정이 있고, 계정이 있다는 뜻이니 업데이트
                ResponseAccountDto accountDto = accountService.updateAccount(createAccountDto);
                existingSummoner.updateSummoner(createSummonerDto, accountDto); // 예전 닉네임 아닌 새로 바뀐 닉네임으로 업데이트
                summonerService.saveSummoner(existingSummoner);
                summonerList.add(existingSummoner);
            } else {
                // 생성한다.
                Summoner newSummoner = summonertoEntity(createSummonerDto);
                summonerService.saveSummoner(newSummoner); // 저장 예전 닉네임이 저장된다.
                accountService.createAccount(createAccountDto); //
                summonerList.add(newSummoner);
            }
        }
        return summonerList; // 소환사 리스트를 전달
    }


    private MatchDto matchToDto(Match match) { // Match 엔티티를 DTO로 변환

        MatchDto.MetadataDto metadataDto = MatchDto.MetadataDto.builder()
                .matchId(match.getMatchId())
                .build();

        List<MatchDto.TeamDto> teamDtoList = new ArrayList<>();
        List<MatchDto.ParticipantDto> participantDtoList = new ArrayList<>();

        List<Team> teamList = match.getTeams();

        for (Team team : teamList) {
            Gson gson = new Gson();
            List<MatchDto.BanDto> bans = gson.fromJson(team.getBanList(), new TypeToken<List<MatchDto.BanDto>>() {
            }.getType());
            MatchDto.ObjectivesDto objectives = gson.fromJson(team.getObjectList(), new TypeToken<MatchDto.ObjectivesDto>() {
            }.getType());
            MatchDto.TeamDto teamDto = MatchDto.TeamDto.builder()
                    .teamId(team.getTeamId())
                    .win(team.isWin())
                    .bans(bans)
                    .objectives(objectives)
                    .build();
            teamDtoList.add(teamDto);

            List<Participant> participantList = team.getParticipants();
            for (Participant participant : participantList) {

                MatchDto.PerkStyleDto primaryPerkStyleDto = MatchDto.PerkStyleDto.builder()
                        .style(participant.getPrimaryStyle())
                        .build();

                MatchDto.PerkStyleDto subPerkStyleDto = MatchDto.PerkStyleDto.builder()
                        .style(participant.getSubStyle())
                        .build();

                MatchDto.PerksDto perksDto = MatchDto.PerksDto.builder()
                        .styles(Arrays.asList(primaryPerkStyleDto, subPerkStyleDto))
                        .build();

                MatchDto.ParticipantDto participantDto = MatchDto.ParticipantDto.builder()
                        .participantId(participant.getParticipantId())
                        .summonerId(participant.getSummonerId())
                        .profileIcon(participant.getProfileIcon())
                        .puuid(participant.getPuuid())
                        .summmonerLevel(participant.getChampLevel())
                        .summonerName(participant.getSummonerName())
                        .riotIdName(participant.getRiotIdName())
                        .riotIdTagline(participant.getRiotIdTagline())
                        .kills(participant.getKills())
                        .assists(participant.getAssists())
                        .deaths(participant.getDeaths())
                        .champExperience(participant.getChampExperience())
                        .champLevel(participant.getChampLevel())
                        .championId(participant.getChampionId())
                        .championName(participant.getChampionName())
                        .item0(participant.getItem0())
                        .item1(participant.getItem1())
                        .item2(participant.getItem2())
                        .item3(participant.getItem3())
                        .item4(participant.getItem4())
                        .item5(participant.getItem5())
                        .item6(participant.getItem6())
                        .summoner1Id(participant.getSummoner1Id())
                        .summoner2Id(participant.getSummoner2Id())
                        .neutralMinionsKilled(participant.getNeutralMinionsKilled())
                        .totalMinionsKilled(participant.getTotalMinionsKilled())
                        .totalDamageDealtToChampions(participant.getTotalDamageDealtToChampions())
                        .totalDamageTaken(participant.getTotalDamageTaken())
                        .teamId(participant.getTeam().getTeamId())
                        .perks(perksDto)
                        .teamPosition(participant.getTeamPosition())
                        .build();

                participantDtoList.add(participantDto);
            }
        }

        MatchDto.InfoDto infoDto = MatchDto.InfoDto.builder()
                .gameCreation(match.getGameCreation())
                .gameDuration(match.getGameDuration())
                .gameStartTimestamp(match.getGameStartTimestamp())
                .gameEndTimestamp(match.getGameEndTimestamp())
                .platformId(match.getPlatformId())
                .queueId(match.getQueueId().getQueueId())
                .teams(teamDtoList)
                .participants(participantDtoList)
                .build();

        return MatchDto.builder()
                .info(infoDto)
                .metadataDto(metadataDto)
                .build();
    }

    private Participant participantToEntity(MatchDto.ParticipantDto participantDto, Team team, Match match) {
        if (participantDto.getRiotIdName() == null) {
            return Participant.builder()
                    .participantId(participantDto.getParticipantId())
                    .summonerId(participantDto.getSummonerId()) // Summoner
                    .profileIcon(participantDto.getProfileIcon()) // Summoner
                    .puuid(participantDto.getPuuid()) // Summoner // Account
                    .summonerLevel(participantDto.getSummmonerLevel()) // Summoner
                    .summonerName(participantDto.getSummonerName()) // Summoner
                    .riotIdName(participantDto.getSummonerName()) // Account
                    .riotIdTagline(participantDto.getRiotIdTagline()) // Account
                    .kills(participantDto.getKills())
                    .assists(participantDto.getAssists())
                    .deaths(participantDto.getDeaths())
                    .champExperience(participantDto.getChampExperience())
                    .champLevel(participantDto.getChampLevel())
                    .championId(participantDto.getChampionId())
                    .championName(participantDto.getChampionName())
                    .item0(participantDto.getItem0())
                    .item1(participantDto.getItem1())
                    .item2(participantDto.getItem2())
                    .item3(participantDto.getItem3())
                    .item4(participantDto.getItem4())
                    .item5(participantDto.getItem5())
                    .item6(participantDto.getItem6())
                    .summoner1Id(participantDto.getSummoner1Id())
                    .summoner2Id(participantDto.getSummoner2Id())
                    .neutralMinionsKilled(participantDto.getNeutralMinionsKilled())
                    .totalMinionsKilled(participantDto.getTotalMinionsKilled())
                    .totalDamageDealtToChampions(participantDto.getTotalDamageDealtToChampions())
                    .totalDamageTaken(participantDto.getTotalDamageTaken())
                    .primaryStyle(participantDto.getPerks().getStyles().get(0).getStyle())
                    .subStyle(participantDto.getPerks().getStyles().get(1).getStyle())
                    .teamPosition(participantDto.getTeamPosition())
                    .team(team)
                    .match(match)
                    .build();
        } else {
            return Participant.builder()
                    .participantId(participantDto.getParticipantId())
                    .summonerId(participantDto.getSummonerId()) // Summoner
                    .profileIcon(participantDto.getProfileIcon()) // Summoner
                    .puuid(participantDto.getPuuid()) // Summoner // Account
                    .summonerLevel(participantDto.getSummmonerLevel()) // Summoner
                    .summonerName(participantDto.getSummonerName()) // Summoner
                    .riotIdName(participantDto.getRiotIdName()) // Account
                    .riotIdTagline(participantDto.getRiotIdTagline()) // Account
                    .kills(participantDto.getKills())
                    .assists(participantDto.getAssists())
                    .deaths(participantDto.getDeaths())
                    .champExperience(participantDto.getChampExperience())
                    .champLevel(participantDto.getChampLevel())
                    .championId(participantDto.getChampionId())
                    .championName(participantDto.getChampionName())
                    .item0(participantDto.getItem0())
                    .item1(participantDto.getItem1())
                    .item2(participantDto.getItem2())
                    .item3(participantDto.getItem3())
                    .item4(participantDto.getItem4())
                    .item5(participantDto.getItem5())
                    .item6(participantDto.getItem6())
                    .summoner1Id(participantDto.getSummoner1Id())
                    .summoner2Id(participantDto.getSummoner2Id())
                    .neutralMinionsKilled(participantDto.getNeutralMinionsKilled())
                    .totalMinionsKilled(participantDto.getTotalMinionsKilled())
                    .totalDamageDealtToChampions(participantDto.getTotalDamageDealtToChampions())
                    .totalDamageTaken(participantDto.getTotalDamageTaken())
                    .primaryStyle(participantDto.getPerks().getStyles().get(0).getStyle())
                    .subStyle(participantDto.getPerks().getStyles().get(1).getStyle())
                    .teamPosition(participantDto.getTeamPosition())
                    .team(team)
                    .match(match)
                    .build();
        }


    }

    private Team teamToEntity(MatchDto.TeamDto teamDto) {
        List<MatchDto.BanDto> bans = teamDto.getBans();
        MatchDto.ObjectivesDto objectivesDtos = teamDto.getObjectives();

        // Gson 객체 생성
        Gson gson = new Gson();
        String banList = gson.toJson(bans);
        String objectList = gson.toJson(objectivesDtos);

        return Team.builder()
                .win(teamDto.isWin())
                .teamId(teamDto.getTeamId())
                .banList(banList)
                .objectList(objectList)
                .build();
    }
}


