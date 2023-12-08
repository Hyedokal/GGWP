package com.ggwp.squadservice.dto.request;

import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.enums.Position;
import com.ggwp.squadservice.enums.QType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "자신의 포지션을 선택하셔야 합니다.")
    public Position myPos;

    @NotNull(message = "듀오의 포지션을 선택하셔야 합니다.")
    public Position wantPos;

    @NotNull(message = "원하는 게임 유형을 선택하지 않았습니다.")
    public QType qType;

    @NotNull(message = "마이크 사용 여부를 선택하지 않았습니다.")
    public boolean useMic;

    @NotBlank(message = "닉네임을 반드시 입력해야 합니다.")
    public String summonerName;

    @NotBlank(message = "Riot 태그를 반드시 입력해야 합니다.")
    public String tagLine;

    @Length(max = 100, message = "메모는 최대 100자까지 작성할 수 있습니다.")
    @NotBlank(message = "내용은 반드시 작성되어야 합니다.")
    public String memo;

    private String summonerRank;

    public Squad toEntity() {
        return Squad.create(this.myPos, this.wantPos, this.qType,
                this.useMic, this.summonerName, this.tagLine, this.summonerRank, this.memo);
    }
}
