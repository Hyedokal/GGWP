package com.ggwp.searchservice.summoner.dto;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.summoner.domain.Summoner;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestSummonerDto {

    @NotBlank(message = "Summoner의 ID는 비워 둘 수 없습니다")
    @Size(max = 63, message = "ID는 63자를 초과할 수 없습니다")
    private String id;

    @Min(value = 0, message = "프로필 아이콘 ID는 반드시 0 이상입니다.")
    private int profileIconId;

    @NotBlank(message = "PUUID는 비워 둘 수 없습니다")
    @Size(max = 78, message = "PUUID는 78자를 초과할 수 없습니다")
    private String puuid;

    @NotBlank(message = "이름은 비워 둘 수 없습니다")
    @Size(max = 63, message = "이름은 63자를 초과할 수 없습니다")
    private String name;

    @NotNull(message = "수정 일자는 null일 수 없습니다")
    private Long revisionDate;

    @Min(value = 0, message = "소환사 레벨은 반드시 0 이상입니다.")
    private int summonerLevel;

    private Account account;

    private League league;

    // DTO를 -> Entity 로 변경
    public Summoner toEntity() {
        return Summoner.builder()
                .id(this.id)
                .profileIconId(this.profileIconId)
                .puuid(this.puuid)
                .name(this.name)
                .revisionDate(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli())
                .summonerLevel(this.summonerLevel)
                .build();
    }
}
