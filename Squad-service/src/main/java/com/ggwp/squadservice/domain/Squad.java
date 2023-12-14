package com.ggwp.squadservice.domain;

import com.ggwp.squadservice.dto.request.RequestSquadDto;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Accessors(chain = true)
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position myPos;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position wantPos;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QType qType;

    @Column(columnDefinition = "BIT(1)", nullable = false)
    private boolean useMic = false;

    @Column(nullable = false)
    private String summonerName;

    @Column(nullable = false)
    private String tagLine;

    @Column(name = "summoner_rank", nullable = false)
    private String summonerRank;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String memo;

    @Column(columnDefinition = "BIT(1)", nullable = false)
    private boolean outdated = false;

    @Column(columnDefinition = "BIT(1)", nullable = false)
    private boolean approved = false;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());

    //생성자를 담당하는 정적 메서드
    public static Squad create(Position myPos, Position wantPos, QType qType,
                               boolean useMic, String summonerName, String tagLine, String rank, String memo) {
        return new Squad()
                .setMyPos(myPos)
                .setWantPos(wantPos)
                .setQType(qType)
                .setUseMic(useMic)
                .setSummonerName(summonerName)
                .setTagLine(tagLine)
                .setSummonerRank(rank)
                .setMemo(memo)
                .setCreatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    //엔티티 수정을 위한 메서드
    public void updateSquad(RequestSquadDto dto) {
        this.myPos = dto.getMyPos();
        this.wantPos = dto.getWantPos();
        this.qType = dto.getQType();
        this.useMic = dto.isUseMic();
        this.memo = dto.getMemo();
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public RequestSquadDto toDto() {
        return new RequestSquadDto()
                .setMyPos(this.myPos)
                .setWantPos(this.wantPos)
                .setQType(this.qType)
                .setUseMic(this.useMic)
                .setSummonerName(this.summonerName)
                .setSummonerRank(this.summonerRank)
                .setTagLine(this.tagLine)
                .setMemo(this.memo)
                .setApproved(this.approved)
                .setSummonerRank(this.summonerRank);
    }
}
