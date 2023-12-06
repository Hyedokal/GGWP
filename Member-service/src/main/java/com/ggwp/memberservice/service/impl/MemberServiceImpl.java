package com.ggwp.memberservice.service.impl;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.dto.request.user.PatchLolNickNameRequestDto;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.user.GetSignInUserResponseDto;
import com.ggwp.memberservice.dto.response.user.GetUserResponseDto;
import com.ggwp.memberservice.dto.response.user.PatchLolNickNameResponseDto;
import com.ggwp.memberservice.repository.MemberRepository;
import com.ggwp.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String uuid) {
        Member member = null;
        try {

            member = memberRepository.findByUuid(uuid);
            if (member == null) return GetSignInUserResponseDto.notExistUser();

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSignInUserResponseDto.success(member);

    }


    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String uuid) {

        Member member = null;

        try {

            member = memberRepository.findByUuid(uuid);
            if (member == null) return GetUserResponseDto.notExistUser();

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetUserResponseDto.success(member);

    }
    @Override
    public ResponseEntity<? super PatchLolNickNameResponseDto> patchLolNickName(PatchLolNickNameRequestDto dto, String uuid) {
        try{
            Member member = memberRepository.findByUuid(uuid);
            if(member == null){
                return PatchLolNickNameResponseDto.notExistUser();
            }
            member.patchLolNickName(dto);

            memberRepository.save(member);


        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchLolNickNameResponseDto.success();
    }
















}

