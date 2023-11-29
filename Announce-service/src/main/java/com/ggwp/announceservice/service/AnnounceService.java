package com.ggwp.announceservice.service;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.dto.request.RequestDto;
import com.ggwp.announceservice.dto.response.ResponseAnnounceDto;
import com.ggwp.announceservice.entity.Announce;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AnnounceService {
    Announce writeAnnounce(RequestAnnounceDto dto);

    public void editAnnounce(Long aId, RequestAnnounceDto dto);

    public void deleteAnnounce(Long aId);

    public List<ResponseAnnounceDto> getAllAnnounce();

    Page<ResponseAnnounceDto> searchPagedAnnounce(RequestDto.Search dto);

    public ResponseAnnounceDto getOneAnnounce(Long aId);
}
