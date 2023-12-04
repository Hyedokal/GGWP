package com.ggwp.memberservice.dto.response.user;

import com.ggwp.memberservice.dto.response.ResponseCode;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
public class PatchEmailResponseDto extends ResponseDto {


private PatchEmailResponseDto(String code, String message) {super(code, message);}

public static ResponseEntity<PatchEmailResponseDto> success() {
PatchEmailResponseDto result = new PatchEmailResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
return  ResponseEntity.status(HttpStatus.OK).body(result);
}

    public static ResponseEntity<ResponseDto> notExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> duplicateEmail() {
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATED_EMAIL, ResponseMessage.DUPLICATED_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }


}
