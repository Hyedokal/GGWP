package com.ggwp.squadservice.dto.request;

import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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

    @NotBlank(message = "반드시 마이크 사용 여부를 체크하셔야 합니다.")
    public Boolean sMic;

    @NotBlank(message = "Riot ID를 반드시 입력해야 합니다.")
    public String summonerName;

    @Length(max = 100, message = "메모는 최대 100자까지 작성할 수 있습니다.")
    @NotBlank(message = "내용은 반드시 작성되어야 합니다.")
    public String sMemo;
    
    private String rank;

    public Squad toEntity() {
        return Squad.CREATE(this.myPos, this.wantPos, this.qType,
                this.sMic, this.summonerName, this.rank, this.sMemo);
    }
}
