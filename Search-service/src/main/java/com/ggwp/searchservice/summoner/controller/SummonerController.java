package com.ggwp.searchservice.summoner.controller;
import com.ggwp.searchservice.summoner.dto.ResponseGetSummonerDto;
import com.ggwp.searchservice.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/summoner")
public class SummonerController {

    private final SummonerService summonerService;

    @GetMapping("get/{name}") // 롤 닉네임으로 Summoner 가져오기 USE API
    public ResponseEntity<?> getSummoner(@PathVariable String name){
        ResponseGetSummonerDto summonerDto = summonerService.getSummonerAndSave(name);
        return ResponseEntity.ok(summonerDto);
    }

    @GetMapping("get/{name}/no-api") // 롤 닉네임으로 Summoner 가져오기 No-API
    public ResponseEntity<?> getSummonerNoApi(@PathVariable String name){
        ResponseGetSummonerDto summonerDto = summonerService.getSummonerNoApi(name);
        return ResponseEntity.ok(summonerDto);
    }

    // ----------------------------------------------------------------------------
    // summoner 전체를 가져가서 사용하실지, FK만 가져가서 쓰실 지 몰라서 만들었습니다.
    // ----------------------------------------------------------------------------

    @GetMapping("get/summoner-id/{name}") // 롤 닉네임으로 Encrypted된 SummonerId 가져오기
    public ResponseEntity<?> getEncryptedSummonerId(@PathVariable String name){
        ResponseGetSummonerDto summonerDto = summonerService.getSummonerNoApi(name);
        return ResponseEntity.ok(summonerDto.getId());
    }

    @GetMapping("get/puuid/{name}") // 롤 닉네임으로 puuid 가져오기
    public ResponseEntity<?> getPuuid(@PathVariable String name){
        ResponseGetSummonerDto summonerDto = summonerService.getSummonerNoApi(name);
        return ResponseEntity.ok(summonerDto.getPuuid());
    }

}
