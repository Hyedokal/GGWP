package com.ggwp.squadservice.dto.memberfeign.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchLolNickNameTagResponseDto  {
    private boolean success;
    private String message;

}
