package com.ggwp.commentservice.domain;

import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.enums.Position;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Accessors(chain = true)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cId;

    //해당 댓글의 게시글 번호 칼럼
    @Column(nullable = false)
    private Long sId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position cMyPos;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String cMemo;

    @Column(columnDefinition = "BIT(1)", nullable = false)
    private Boolean cMic;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;

    //생성자 호출을 대신할 정적 메서드 선언
    public static Comment CREATE(Long sId, Position cMyPos, Boolean cMic, String cMemo) {
        return new Comment()
                .setSId(sId)
                .setCMyPos(cMyPos)
                .setCMic(cMic)
                .setCMemo(cMemo)
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());
    }

    //엔티티 수정을 위한 메서드
    public void updateComment(RequestCommentDto dto) {
        this.cMyPos = dto.getCMyPos();
        this.cMemo = dto.getCMemo();
        this.cMic = dto.getCMic();
        this.updatedAt = LocalDateTime.now();
    }
}
