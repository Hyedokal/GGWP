package com.ggwp.memberservice.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchLolNickNameRequestDto {
    @NotBlank
    String lolNickName;
    String tag;
}
