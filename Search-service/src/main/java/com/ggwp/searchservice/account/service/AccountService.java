package com.ggwp.searchservice.account.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.dto.AccountDto;
import com.ggwp.searchservice.account.feign.LOLToAccountFeign;
import com.ggwp.searchservice.account.repository.AccountRepository;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final LOLToAccountFeign accountFeign;
    private final SummonerRepository summonerRepository;

    @Value("${LOL.apikey}")
    private String apiKey;

    public void createAccount(String lol_name, String tagLine) { // api 호출
        AccountDto accountDto = accountFeign.getAccount(lol_name, tagLine, apiKey);

        if (summonerRepository.existsByPuuid(accountDto.getPuuid())) {
            Summoner summoner = summonerRepository.findSummonerByPuuid(accountDto.getPuuid());
            accountRepository.save(accountDto.toEntity(summoner));
        }

        // summoner가 없다면 롤 api를 호출하여 summoner를 가져온다.

    }

    public AccountDto getAccount(String lol_name, String tagLine) { // DB 검색
        Account account = accountRepository.findByGameNameAndTagLine(lol_name, tagLine);

        return AccountDto.builder()
                .puuid(account.getPuuid())
                .gameName(account.getGameName())
                .tagLine(account.getTagLine())
                .build();
    }
}
