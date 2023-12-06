package com.ggwp.memberservice.service;

import com.ggwp.memberservice.dto.request.user.PatchLolNickNameRequestDto;
import com.ggwp.memberservice.dto.response.user.GetSignInUserResponseDto;
import com.ggwp.memberservice.dto.response.user.GetUserResponseDto;
import com.ggwp.memberservice.dto.response.user.PatchLolNickNameResponseDto;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String uuid);
    ResponseEntity<? super GetUserResponseDto> getUser(String uuid);


    ResponseEntity<? super PatchLolNickNameResponseDto> patchLolNickName(PatchLolNickNameRequestDto dto , String uuid);
}
