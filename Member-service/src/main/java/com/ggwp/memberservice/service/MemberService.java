package com.ggwp.memberservice.service;

import com.ggwp.memberservice.dto.request.user.PatchLolNickNameTagRequestDto;
import com.ggwp.memberservice.dto.request.user.PersonalitiesRequestDto;
import com.ggwp.memberservice.dto.response.user.*;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String uuid);
    ResponseEntity<? super GetUserResponseDto> getUser(String uuid);


    ResponseEntity<? super PatchLolNickNameTagResponseDto> patchLolNickTag(PatchLolNickNameTagRequestDto dto , String uuid);


    ResponseEntity<? super PersonalitiesResponseDto> addPersonalities(String uuid, PersonalitiesRequestDto dto);

    ResponseEntity<? super  PersonalitiesInfoResponseDto> getPersonalities(String uuid);


    ResponseEntity<? super GetMatchInfoResponseDto> getMatchInfo(String uuid);
}
