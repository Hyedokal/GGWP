package com.ggwp.squadservice.service;

import com.ggwp.squadservice.dto.request.RequestSquadDto;
import com.ggwp.squadservice.dto.response.ResponseSquadDto;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;

import java.util.List;

public interface SquadService {
    public void writeSquad(RequestSquadDto dto);

    public void editSquad(Long sId, RequestSquadDto dto);

    public void deleteSquad(Long sId);

    public List<ResponseSquadDto> getAllSquad();

    public ResponseSquadDto getOneSquad(Long sId);

    public List<ResponseSquadDto> getSquadWithFilters(Position myPos, QType qType);
}
