package com.ggwp.squadservice.domain;

import com.ggwp.squadservice.enums.Position;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor @Getter
@ToString @Builder
public class Comment {

    private Long cId;

    private Position cMyPos;

    private String cMemo;

    private Boolean cMic;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
