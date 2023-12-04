package com.ggwp.memberservice.service;

import com.ggwp.memberservice.dto.request.user.PatchEmailRequestDto;
import com.ggwp.memberservice.dto.response.user.GetSignInUserResponseDto;
import com.ggwp.memberservice.dto.response.user.GetUserResponseDto;
import com.ggwp.memberservice.dto.response.user.PatchEmailResponseDto;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
    ResponseEntity<? super GetUserResponseDto> getUser(String email);


    ResponseEntity<? super PatchEmailResponseDto> patchEmail(PatchEmailRequestDto dto , String email);
}
