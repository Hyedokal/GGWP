package com.ggwp.commentservice.dto.squadFeign.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeignLolNickNameTagRequestDto {

    @NotBlank
    String existLolNickName;
    @NotBlank
    String existTag;
    @NotBlank
    String lolNickName;
    @NotBlank
    String tag;

}
