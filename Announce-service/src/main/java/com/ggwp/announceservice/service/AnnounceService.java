package com.ggwp.announceservice.service;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.dto.request.RequestPageDto;
import com.ggwp.announceservice.dto.response.ResponseAnnounceDto;
import com.ggwp.announceservice.entity.Announce;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AnnounceService {
    public Announce writeAnnounce(RequestAnnounceDto dto);

    public Announce editAnnounce(Long aId, RequestAnnounceDto dto);

    public void deleteAnnounce(Long aId);

    public List<ResponseAnnounceDto> getAllAnnounce();

    Page<ResponseAnnounceDto> searchPagedAnnounce(RequestPageDto.Search dto);

    public ResponseAnnounceDto getOneAnnounce(Long aId);
}
