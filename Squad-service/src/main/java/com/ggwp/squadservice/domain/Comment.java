package com.ggwp.squadservice.domain;

import com.ggwp.squadservice.enums.Position;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class Comment {

    private Long cId;

    private Position myPos;

    private String memo;

    private Boolean useMic;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
