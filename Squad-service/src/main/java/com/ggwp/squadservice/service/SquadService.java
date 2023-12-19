package com.ggwp.squadservice.service;

import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.dto.memberfeign.request.PatchLolNickNameTagRequestDto;
import com.ggwp.squadservice.dto.memberfeign.request.RequestMatchDto;
import com.ggwp.squadservice.dto.memberfeign.response.PatchLolNickNameTagResponseDto;
import com.ggwp.squadservice.dto.memberfeign.response.ResponseMatchDto;
import com.ggwp.squadservice.dto.request.RequestSquadDto;
import com.ggwp.squadservice.dto.request.RequestSquadPageDto;
import com.ggwp.squadservice.dto.response.ResponseSquadDto;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SquadService {
    Squad writeSquad(RequestSquadDto dto);

    Squad editSquad(Long sId, RequestSquadDto dto);

    void deleteSquad(Long sId);

    ResponseSquadDto getOneSquad(Long sId);

    List<ResponseSquadDto> getSquadWithFilters(Position myPos, QType qType, String rank);

    Map<QType, String> getSummonerRank(String summonerName);

    Page<ResponseSquadDto> searchPagedSquad(RequestSquadPageDto.Search dto);

    Squad approveSquad(Long sId);


    ResponseMatchDto getMatchInfo(RequestMatchDto dto);

    ResponseEntity<PatchLolNickNameTagResponseDto> patchLolNickTag(PatchLolNickNameTagRequestDto requestBody);
}
