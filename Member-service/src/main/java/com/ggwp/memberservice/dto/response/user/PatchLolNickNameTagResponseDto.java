package com.ggwp.memberservice.dto.response.user;

import com.ggwp.memberservice.dto.response.ResponseCode;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
public class PatchLolNickNameTagResponseDto extends ResponseDto {


private PatchLolNickNameTagResponseDto(ResponseCode code, ResponseMessage message) {super(code, message);}

public static ResponseEntity<PatchLolNickNameTagResponseDto> success() {
PatchLolNickNameTagResponseDto result = new PatchLolNickNameTagResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
return  ResponseEntity.status(HttpStatus.OK).body(result);
}

    public static ResponseEntity<ResponseDto> notExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    public static ResponseEntity<ResponseDto> nicknameAndTagAlreadyTaken() {
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATED_LOL_NICKNAME_TAG, ResponseMessage.DUPLICATED_LOL_NICKNAME_TAG);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    public static ResponseEntity<ResponseDto> duplicateEmail() {
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATED_EMAIL, ResponseMessage.DUPLICATED_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }


}
