package com.ggwp.announceservice.dto.response;

import com.ggwp.announceservice.entity.Announce;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

//공지사항 조회 DTO
@Data
@Accessors(chain = true)
public class ResponseAnnounceDto {
    private Long id;

    private String title;

    private String content;

    private Timestamp updatedAt;

    public static ResponseAnnounceDto fromEntity(Announce announce) {
        return new ResponseAnnounceDto()
                .setId(announce.getId())
                .setTitle(announce.getTitle())
                .setContent(announce.getContent())
                .setUpdatedAt(announce.getUpdatedAt());
    }
}
