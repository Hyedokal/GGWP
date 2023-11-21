package com.ggwp.squadservice.domain;

import com.ggwp.squadservice.dto.RequestSquadDto;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter @ToString @Builder
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sId;

    //Member 객체를 Join해 올 칼럼. member-service 작성 완료시 활성화.
//    @ManyToOne
//    @JoinColumn(name = "uuid")
//    private Member member;

    @Enumerated(EnumType.STRING)
    private Position myPos;

    @Enumerated(EnumType.STRING)
    private Position wantPos;

    @Enumerated(EnumType.STRING)
    private QType qType;

    private Boolean sMic = false;

    @Column
    @Length(max = 100, message = "메모는 100자 이하까지 작성 가능합니다.")
    private String sMemo;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    //엔티티 수정을 위한 메서드
    public void updateSquad(RequestSquadDto dto){
        this.myPos = dto.getMyPos();
        this.wantPos = dto.getWantPos();
        this.qType = dto.getQType();
        this.sMic = dto.getSMic();
        this.sMemo = dto.getSMemo();
        this.updatedAt = LocalDateTime.now();
    }
}
