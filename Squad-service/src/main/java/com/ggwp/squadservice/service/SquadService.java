package com.ggwp.squadservice.service;

import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.dto.request.RequestSquadDto;
import com.ggwp.squadservice.dto.request.RequestSquadPageDto;
import com.ggwp.squadservice.dto.response.ResponseSquadDto;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import org.springframework.data.domain.Page;

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
}
