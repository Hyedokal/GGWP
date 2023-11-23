package com.ggwp.announceservice.service;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.dto.response.ResponseAnnounceDto;

import java.util.List;

public interface AnnounceService {
    public void writeAnnounce(RequestAnnounceDto dto);

    public void editAnnounce(Long aId, RequestAnnounceDto dto);

    public void deleteAnnounce(Long aId);

    public List<ResponseAnnounceDto> getAllAnnounce();

    public ResponseAnnounceDto getOneAnnounce(Long aId);
}
