package com.ggwp.squadservice.dto.memberfeign.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchLolNickNameTagRequestDto {
    @NotBlank
    String existLolNickName;
    @NotBlank
    String existTag;
    @NotBlank
    String lolNickName;
    @NotBlank
    String tag;


}