package com.ggwp.memberservice.dto.response.user;
import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.dto.response.ResponseCode;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetUserResponseDto extends ResponseDto{
    private String email;
    private String lolNickname;

        private GetUserResponseDto(String code, String message, Member member) {
            super(code, message);
            this.email = member.getEmail();
            this.lolNickname = member.getLolNickname();
        }

    public static ResponseEntity<GetUserResponseDto> success(Member member) {
        GetUserResponseDto result = new GetUserResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, member);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    public static ResponseEntity<ResponseDto> notExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}

