package com.ggwp.searchservice.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestAccountDto {

    @NotBlank(message = "PUUID는 비워 둘 수 없습니다")
    @Size(max = 78, message = "PUUID는 78자를 초과할 수 없습니다")
    private String puuid; // summoner의 puuid와 같다.

    @NotBlank(message = "이름은 비워 둘 수 없습니다")
    @Size(max = 63, message = "이름은 63자를 초과할 수 없습니다")
    private String gameName; // 롤 닉네임이다.

    // 토큰에는 태그가 무조건 있는데, match를 통해서 account를 생성할 때는 태그가 없을 수 있다.
    private String tagLine;
}
