package com.ggwp.searchservice.league.service;


import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.service.AccountService;
import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.common.exception.CustomException;
import com.ggwp.searchservice.common.exception.ErrorCode;
import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.league.dto.CreateLeagueDto;
import com.ggwp.searchservice.league.dto.ResponseLeagueDto;
import com.ggwp.searchservice.league.feign.LoLToLeagueFeign;
import com.ggwp.searchservice.league.repository.LeagueRepository;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeagueServiceImpl implements LeagueService {
    // 레포지토리
    private final LeagueRepository leagueRepository;
    // 페인
    private final LoLToLeagueFeign leagueFeign;
    // 서비스
    private final AccountService accountService;
    private final SummonerService summonerService;

    @Value("${LOL.apikey}")
    private String apiKey;

    private List<CreateLeagueDto> leagueFeign(Summoner summoner) {
        return leagueFeign.getLeagues(summoner.getId(), apiKey);
    }

    @Override
    public List<League> createLeague(Summoner summoner) {
        List<League> leagueList = new ArrayList<>();
        List<CreateLeagueDto> leagueDtoList = leagueFeign(summoner);
        for (CreateLeagueDto createLeagueDto : leagueDtoList) {
            if (createLeagueDto.getLeagueId() == null) {
                break; // CHERRY -> 아레나 모드도 전적에 담김. https://github.com/RiotGames/developer-relations/issues/861
            }
            leagueList.add(leagueRepository.save(leaguetoEntity(createLeagueDto, summoner)));
        }
        return leagueList;
    }

    @Override
    public void updateLeagues(Summoner summoner) {
        List<League> leagueList = findLeagues(summoner);
        List<CreateLeagueDto> leagueDtoList = leagueFeign(summoner);

        for (League league : leagueList) {
            for (CreateLeagueDto createLeagueDto : leagueDtoList) {
                if (createLeagueDto.getQueueType().equals(league.getQueueType())) {
                    league.updateLeague(createLeagueDto);
                } else {
                    if (createLeagueDto.getLeagueId() == null) {
                        break; // CHERRY -> 아레나 모드도 전적에 담김. https://github.com/RiotGames/developer-relations/issues/861
                    }
                    leagueRepository.save(leaguetoEntity(createLeagueDto, summoner));
                }
            }
        }
    }

    // 리그 정보 얻기 No - API
    @Override
    @Transactional(readOnly = true)
    public List<ResponseLeagueDto> getLeague(FrontDto frontDto) {

        Account account = accountService.findAccount(frontDto); // 토크으로 Account
        Summoner summoner = summonerService.findSummoner(account); // Summoner 가져오기

        List<League> leagueList = findLeagues(summoner); // 리그 가져오기

        return leagueToDto(leagueList);
    }

    private List<League> findLeagues(Summoner summoner) {
        return leagueRepository.findLeaguesBySummonerId(summoner.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NotFindLeagues));
    }

    private List<ResponseLeagueDto> leagueToDto(List<League> leagueList) {
        List<ResponseLeagueDto> leagueDtoList = new ArrayList<>();
        for (League league : leagueList) {
            ResponseLeagueDto leagueDto = league.toDto(league);
            leagueDtoList.add(leagueDto);
        }
        return leagueDtoList;
    }

    private League leaguetoEntity(CreateLeagueDto leagueDto, Summoner summoner) {
        return League.builder()
                .leagueId(leagueDto.getLeagueId())
                .queueType(leagueDto.getQueueType())
                .tier(leagueDto.getTier())
                .ranks(leagueDto.getRank())
                .leaguePoints(leagueDto.getLeaguePoints())
                .wins(leagueDto.getWins())
                .losses(leagueDto.getLosses())
                .summoner(summoner)
                .build();
    }
}
