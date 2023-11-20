package com.ggwp.squadservice.dto;

import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RequestSquadDto {
    @NotBlank(message = "반드시 하나의 포지션을 선택하셔야 합니다.")
    public Position myPos;

    @NotBlank(message = "반드시 하나의 포지션을 선택하셔야 합니다.")
    public Position wantPos;

    @NotBlank(message = "반드시 하나의 게임 유형을 선택하셔야 합니다.")
    public QType qType;
    
    @NotBlank
    public Boolean sMic;
    
    @Length(max = 100, message = "메모는 최대 100자까지 작성할 수 있습니다.")
    @NotBlank
    public String sMemo;

    public Squad toEntity(){
        return Squad.builder()
                .myPos(this.myPos)
                .wantPos(this.wantPos)
                .qType(this.qType)
                .sMic(this.sMic)
                .sMemo(this.sMemo)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
