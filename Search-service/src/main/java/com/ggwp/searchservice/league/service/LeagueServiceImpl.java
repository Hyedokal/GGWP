package com.ggwp.searchservice.league.service;


import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.service.AccountService;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;
import com.ggwp.searchservice.common.exception.CustomException;
import com.ggwp.searchservice.common.exception.ErrorCode;
import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.league.dto.ResponseLeagueDto;
import com.ggwp.searchservice.league.repository.LeagueRepository;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository leagueRepository;

    private final AccountService accountService;
    private final SummonerService summonerService;

    // 리그 정보 얻기 API 및 DB 저장
//    public List<ResponseGetLeagueDto> getLeaguesAndSave(@PathVariable String summonerId) {
//        List<ResponseGetLeagueDto> leagueDto = leagueFeign.getLeagues(summonerId, apiKey);
//        Summoner summoner = summonerRepository.findSummonerById(summonerId);
//        List<League> leagueList = new ArrayList<>();
//        for (ResponseGetLeagueDto dto : leagueDto) {
//
//            leagueList.add(dto.toEntity(summoner));
//        }
//        leagueRepository.saveAll(leagueList);
//        return leagueDto;
//    }

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
}
