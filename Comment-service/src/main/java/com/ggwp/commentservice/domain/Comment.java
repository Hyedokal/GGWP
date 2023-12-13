package com.ggwp.commentservice.domain;

import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.enums.Position;
import com.ggwp.commentservice.enums.QType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
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
    private Position myPos;

    //게시글에서 받아온 QType
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QType qType;

    @Column(columnDefinition = "BIT(1)", nullable = false)
    private boolean useMic;

    @Column(nullable = false)
    private String summonerName;

    @Column(nullable = false)
    private String tag_line;

    @Column(name = "summoner_rank", nullable = false)
    private String summonerRank;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String memo;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());

    //생성자 호출을 대신할 정적 메서드 선언
    public static Comment create(Long sId, Position myPos, QType qType, boolean useMic,
                                 String summonerName, String tag_line, String cMemo, String rank) {
        return new Comment()
                .setSId(sId)
                .setMyPos(myPos)
                .setQType(qType)
                .setUseMic(useMic)
                .setSummonerName(summonerName)
                .setTag_line(tag_line)
                .setSummonerRank(rank)
                .setMemo(cMemo)
                .setCreatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    //엔티티 수정을 위한 메서드
    public void updateComment(RequestCommentDto dto) {
        this.myPos = dto.getMyPos();
        this.memo = dto.getMemo();
        this.useMic = dto.isUseMic();
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
