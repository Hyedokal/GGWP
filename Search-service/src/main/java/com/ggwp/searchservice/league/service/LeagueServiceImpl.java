package com.ggwp.searchservice.league.service;


import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.service.AccountService;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository leagueRepository;
    private final LoLToLeagueFeign leagueFeign;
    private final AccountService accountService;
    private final SummonerService summonerService;

    @Value("${LOL.apikey}")
    private String apiKey;

    public List<CreateLeagueDto> leagueFeign(Summoner summoner) {
        return leagueFeign.getLeagues(summoner.getId(), apiKey);
    }

    @Override
    public List<League> createLeague(Summoner summoner) {
        List<League> leagueList = new ArrayList<>();
        List<CreateLeagueDto> leagueDtoList = leagueFeign(summoner);

        for (CreateLeagueDto createLeagueDto : leagueDtoList) {
            leagueList.add(leagueRepository.save(LeaguetoEntity(createLeagueDto, summoner)));
        }
        return leagueList;
    }

    public void updateLeagues(Summoner summoner) {
        List<CreateLeagueDto> leagueDtoList = leagueFeign(summoner);
        for (CreateLeagueDto createLeagueDto : leagueDtoList) {
            leagueRepository.save(LeaguetoEntity(createLeagueDto, summoner));
        }

    }

    // 리그 정보 얻기 No - API
    public ResponseDto<List<ResponseLeagueDto>> getLeague(TokenDto tokenDto) {

        Account account = accountService.findAccount(tokenDto); // 토크으로 Account
        Summoner summoner = summonerService.findSummoner(account); // Summoner 가져오기

        List<League> leagueList = findLeagues(summoner); // 리그 가져오기

        return ResponseDto.success(leagueToDto(leagueList));
    }

    @Override // find League Entity
    public List<League> findLeagues(Summoner summoner) {
        return leagueRepository.findLeaguesBySummonerId(summoner.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NotFindLeagues));
    }

    @Override // League Entity -> DTO
    public List<ResponseLeagueDto> leagueToDto(List<League> leagueList) {
        List<ResponseLeagueDto> leagueDtoList = new ArrayList<>();
        for (League league : leagueList) {
            ResponseLeagueDto leagueDto = league.toDto(league);
            leagueDtoList.add(leagueDto);
        }
        return leagueDtoList;
    }

    @Override
    public League LeaguetoEntity(CreateLeagueDto leagueDto, Summoner summoner) {
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
