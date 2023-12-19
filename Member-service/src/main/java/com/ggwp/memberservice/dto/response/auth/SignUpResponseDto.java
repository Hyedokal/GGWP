package com.ggwp.memberservice.dto.response.auth;

import com.ggwp.memberservice.dto.response.ResponseCode;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class SignUpResponseDto extends ResponseDto {

    private SignUpResponseDto(ResponseCode code, ResponseMessage message) {
        super(code, message);
    }

    public static ResponseEntity<SignUpResponseDto> success() {
        SignUpResponseDto result = new SignUpResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> duplicateEmail() {
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATED_EMAIL, ResponseMessage.DUPLICATED_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> duplicateLolNickTag() {
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATED_LOL_NICKNAME_TAG, ResponseMessage.DUPLICATED_LOL_NICKNAME_TAG);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
