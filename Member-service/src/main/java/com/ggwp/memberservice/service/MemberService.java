package com.ggwp.memberservice.service;

import com.ggwp.memberservice.dto.request.user.PatchLolNickNameRequestDto;
import com.ggwp.memberservice.dto.request.user.PatchTag;
import com.ggwp.memberservice.dto.request.user.PersonalitiesRequestDto;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.user.*;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String uuid);
    ResponseEntity<? super GetUserResponseDto> getUser(String uuid);


    ResponseEntity<? super PatchLolNickNameResponseDto> patchLolNickName(PatchLolNickNameRequestDto dto , String uuid);

    void patchTag(String uuid , PatchTag patchTag);


    ResponseEntity<? super PersonalitiesResponseDto> addPersonalities(String uuid, PersonalitiesRequestDto dto);

    ResponseEntity<? super  PersonalitiesInfoResponseDto> getPersonalities(String uuid);


    ResponseEntity<? super GetMatchInfoResponseDto> getMatchInfo(String uuid);
}
