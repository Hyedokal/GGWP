package com.ggwp.memberservice.service;

import com.ggwp.memberservice.dto.response.user.GetSignInUserResponseDto;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);

}
