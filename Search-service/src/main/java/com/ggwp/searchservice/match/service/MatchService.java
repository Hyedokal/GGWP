package com.ggwp.searchservice.match.service;

import com.ggwp.searchservice.match.MatchRepository.MatchRepository;
import com.ggwp.searchservice.match.MatchRepository.MatchSummonerRepository;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.match.domain.MatchSummoner;
import com.ggwp.searchservice.match.dto.MatchDto;
import com.ggwp.searchservice.match.dto.MatchSummonerDto;
import com.ggwp.searchservice.match.feign.LoLToMatchFeign;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.dto.RequestCreateSummonerDto;
import com.ggwp.searchservice.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchService {

    private final LoLToMatchFeign matchFeign;
    private final MatchRepository matchRepository;
    private final SummonerRepository summonerRepository;
    private final MatchSummonerRepository matchSummonerRepository;

    @Value("${LOL.apikey}")
    private String apiKey;

    public void createMatch(String puuid) {
        List<String> matchIds = matchFeign.getMatchIds(puuid, apiKey); // matchIds 5개 가져오기

        for (String matchId : matchIds) { // 5번 반복한다.
            MatchDto matchDto = matchFeign.getMatch(matchId, apiKey); // matchId로 전적 조회

            Match match = matchDto.toEntity(); // 엔티티로 변환
            matchRepository.save(match); // match 저장
            // match는 summoner가 여러개를 가질 수 있다. @ManyToMany
            // summoner는 match를 여러개 가질 수 있다. @ManyToMany
            // @ManyToMany를 사용하지 않고 OneToMany(Match) - ManyToOne(MatchSummoner) - OneToMany(Summoner) 로 매핑하기로 함
            // match에다가 MatchSummoner를 저장해야함 -> 저장하기 위해선, 우선 summoner부터 먼저 저장해야함
            // Summoner에다가도 MatchSummoner를 저장해야함
            List<MatchDto.ParticipantDto> participantDtoList = matchDto.getInfo().getParticipants();

            List<MatchSummoner> matchSummonerList = new ArrayList<>(); // matchSummoner를 한 번에 저장해줄 리스트 선언, db에 저장을 한 번에 하기 위함
            for (MatchDto.ParticipantDto participantDto : participantDtoList) {
                RequestCreateSummonerDto createSummonerDto = participantDto.createSummonerDto();
                Summoner summoner = createSummonerDto.toEntity();
                // summoner를 저장 하기 전 MatchSummoner를 만들어 summoner에 넣어줘야 한다.
                MatchSummoner matchSummoner = MatchSummoner.builder()
                        .summoner(summoner)
                        .match(match)
                        .build();
                summoner.addMatchSummoner(matchSummoner); // summoner에다가 matchsummoner 넣어주기
                summonerRepository.save(summoner); // summoner 저장
                matchSummonerList.add(matchSummoner); // matchSummoner List에다가 저장
            }
            // match에 들어갈 matchSummoner
            match.addMatchSummoners(matchSummonerList);

            matchSummonerRepository.saveAll(matchSummonerList); // matchSummoner List 저장
        }
    }


    // 소환사의 matchId들 matchSummoner에서 가져오기
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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


