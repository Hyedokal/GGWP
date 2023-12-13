package com.ggwp.memberservice.dto.response.user;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.dto.response.ResponseCode;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PersonalitiesResponseDto extends ResponseDto {

    private PersonalitiesResponseDto(ResponseCode code, ResponseMessage message) {super(code, message);}


    public static ResponseEntity<PersonalitiesResponseDto> success() {
        PersonalitiesResponseDto result = new PersonalitiesResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
