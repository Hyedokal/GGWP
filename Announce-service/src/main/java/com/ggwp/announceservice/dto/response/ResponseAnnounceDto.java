package com.ggwp.announceservice.dto.response;

import com.ggwp.announceservice.entity.Announce;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

//공지사항 조회 DTO
@Data
@Accessors(chain = true)
public class ResponseAnnounceDto {

    private String aTitle;

    private String aContent;

    private LocalDateTime updatedAt;

    public static ResponseAnnounceDto fromEntity(Announce announce) {
        return new ResponseAnnounceDto()
                .setATitle(announce.getATitle())
                .setAContent(announce.getAContent())
                .setUpdatedAt(announce.getUpdatedAt());
    }
}
