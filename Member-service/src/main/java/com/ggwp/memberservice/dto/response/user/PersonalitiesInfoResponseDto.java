package com.ggwp.memberservice.dto.response.user;

import com.ggwp.memberservice.dto.response.ResponseCode;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.ResponseMessage;
import lombok.Getter;

import java.util.List;
@Getter
public class PersonalitiesInfoResponseDto extends ResponseDto {
    private List<String> personalities;

    private PersonalitiesInfoResponseDto(ResponseCode code, ResponseMessage message) {super(code, message);}


    public static PersonalitiesInfoResponseDto success(List<String> personalities) {
        PersonalitiesInfoResponseDto result = new PersonalitiesInfoResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        result.personalities = personalities;
        return result;
    }

    public static PersonalitiesInfoResponseDto notExistUser() {
        PersonalitiesInfoResponseDto result = new PersonalitiesInfoResponseDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        return result;
    }
}
