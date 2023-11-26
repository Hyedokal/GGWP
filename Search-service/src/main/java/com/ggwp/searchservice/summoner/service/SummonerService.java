package com.ggwp.searchservice.summoner.service;

import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.dto.RequestCreateSummonerDto;
import com.ggwp.searchservice.summoner.dto.ResponseGetSummonerDto;
import com.ggwp.searchservice.summoner.feign.LOLToSummonerFeign;
import com.ggwp.searchservice.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {
    private final SummonerRepository summonerRepository;
    private final LOLToSummonerFeign summonerFeign;
    @Value("${LOL.apikey}")
    private String apiKey;

    // 롤 api를 통해서 소환사 정보 가져오기 및 DB 저장
    public ResponseGetSummonerDto getSummonerAndSave(String name) {
        ResponseGetSummonerDto summonerDto = summonerFeign.getSummoner(name, apiKey);
        Summoner summoner = summonerDto.toEntity();
        summonerRepository.save(summoner);
        return summonerDto;
    }

    // DB에 저장한 소환사 정보 가져오기
    public ResponseGetSummonerDto getSummonerNoApi(String name) {
        Summoner summoner = summonerRepository.findSummonerByName(name);
        ResponseGetSummonerDto summonerDto = summoner.toDto(summoner);
        return summonerDto;
    }

    // match 값에서 가져온 정보로 summoner 저장
    public Summoner createSummoner(RequestCreateSummonerDto summonerDto) {
        if (!summonerRepository.existsById(summonerDto.getId())) {
            Summoner summoner = summonerDto.toEntity();
            summonerRepository.save(summoner);

            return summoner;
        }
        return null;
    }


//    public void createSummoners(List<RequestCreateSummonerDto> summonerDtos) {
//        List<Summoner> summoners = new ArrayList<>();
//
//        for (RequestCreateSummonerDto summonerDto : summonerDtos) {
//            // 검색
//            if (!summonerRepository.existsById(summonerDto.getId())) {
//                Summoner summoner = summonerDto.toEntity();
//                summoners.add(summoner);
//            }
//        }
//        summonerRepository.saveAll(summoners);
//    }

}
