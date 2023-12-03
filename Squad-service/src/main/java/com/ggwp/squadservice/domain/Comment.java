package com.ggwp.squadservice.domain;

import com.ggwp.squadservice.enums.Position;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Comment {

    private Long cId;

    private Position cMyPos;

    private String cMemo;

    private Boolean cMic;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
