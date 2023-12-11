package com.ggwp.noticeservice.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class RequestFeignDto {

    @NotNull(message = "comment의 ID는 null 값이 되어선 안됩니다.")
    private Long cId;

    //해당 댓글의 게시글 번호 칼럼
    @NotNull(message = "squad의 ID는 null 값이 되어선 안됩니다.")
    private Long sId;
}
