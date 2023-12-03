package com.ggwp.searchservice.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAccountDto {

    @NotBlank(message = "PUUID는 비워 둘 수 없습니다")
    @Size(max = 78, message = "PUUID는 78자를 초과할 수 없습니다")
    private String puuid;

    @NotBlank(message = "이름은 비워 둘 수 없습니다")
    @Size(max = 63, message = "이름은 63자를 초과할 수 없습니다")
    private String gameName;

    private String tagLine;
}
