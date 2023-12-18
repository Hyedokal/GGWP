package com.ggwp.searchservice.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@Builder
public class FrontDto implements Serializable {

    @NotBlank(message = "이름은 비워 둘 수 없습니다")
    @Size(max = 63, message = "이름은 63자를 초과할 수 없습니다")
    private String gameName;

    @NotBlank(message = "tag는 비워 둘 수 없습니다.")
    private String tagLine;
}
