package com.ggwp.memberservice.dto.response.user;


import com.ggwp.memberservice.dto.response.ResponseCode;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PatchProfileImageResponseDto extends ResponseDto {

        private PatchProfileImageResponseDto(ResponseCode code, ResponseMessage message) {
            super(code, message);
        }

        public static ResponseEntity<PatchProfileImageResponseDto> success() {
            PatchProfileImageResponseDto result = new PatchProfileImageResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

        public static ResponseEntity<ResponseDto> notExistUser() {
            ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

}
