package com.ggwp.memberservice.dto.response.user;

import com.ggwp.memberservice.dto.feign.response.ResponseMatchDto;
import com.ggwp.memberservice.dto.response.ResponseCode;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Getter
public class GetMatchInfoResponseDto extends ResponseDto {

    private String lolNickname;
    private String tag;
    private List<Long> sId;

    private GetMatchInfoResponseDto(ResponseCode code, ResponseMessage message, ResponseMatchDto responseMatchDto) {
        super(code, message);
        this.lolNickname = responseMatchDto.getLolNickname();
        this.tag = responseMatchDto.getTag();
        //리스트
        this.sId = responseMatchDto.getSIdList();
    }

    public static ResponseEntity<GetMatchInfoResponseDto> success(ResponseMatchDto responseMatchDto) {
        GetMatchInfoResponseDto result = new GetMatchInfoResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseMatchDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> notExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
