package com.ggwp.memberservice.dto.response.user;


import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.domain.UserRole;
import com.ggwp.memberservice.dto.response.ResponseCode;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetSignInUserResponseDto extends ResponseDto {

    private String email;
    private GetSignInUserResponseDto(String code, String message, Member member) {
        super(code, message);
        this.email = member.getEmail();
    }

    public static ResponseEntity<GetSignInUserResponseDto> success(Member member) {
        GetSignInUserResponseDto result = new GetSignInUserResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, member);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
