package com.ggwp.noticeservice.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestStatusDto {

    @NotBlank(message = "이름은 비워 둘 수 없습니다")
    @Size(max = 63, message = "이름은 63자를 초과할 수 없습니다")
    private String receiverName;

    @NotBlank(message = "태그는 비워 둘 수 없습니다")
    private String receiverTag;

    @NotBlank(message = "상태를 비워 둘 수 없습니다.")
    private String status;
}
