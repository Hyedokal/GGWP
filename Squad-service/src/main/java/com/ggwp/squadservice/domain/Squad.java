package com.ggwp.squadservice.domain;

import com.ggwp.squadservice.dto.request.RequestSquadDto;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
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
    private Boolean sMic = false;

    @Column(nullable = false)
    private String summonerName;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String sMemo;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    //생성자를 담당하는 정적 메서드
    public static Squad CREATE(Position myPos, Position wantPos, QType qType,
                               Boolean sMic, String summonerName, String sMemo) {
        return new Squad()
                .setMyPos(myPos)
                .setWantPos(wantPos)
                .setQType(qType)
                .setSMic(sMic)
                .setSummonerName(summonerName)
                .setSMemo(sMemo)
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());
    }

    //엔티티 수정을 위한 메서드
    public void updateSquad(RequestSquadDto dto) {
        this.myPos = dto.getMyPos();
        this.wantPos = dto.getWantPos();
        this.qType = dto.getQType();
        this.sMic = dto.getSMic();
        this.sMemo = dto.getSMemo();
        this.updatedAt = LocalDateTime.now();
    }
}
