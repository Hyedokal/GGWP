package com.ggwp.memberservice.service.impl;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.dto.request.user.PatchEmailRequestDto;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.user.GetSignInUserResponseDto;
import com.ggwp.memberservice.dto.response.user.GetUserResponseDto;
import com.ggwp.memberservice.dto.response.user.PatchEmailResponseDto;
import com.ggwp.memberservice.repository.MemberRepository;
import com.ggwp.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {
        Member member = null;
        try {

            member = memberRepository.findByEmail(email);
            if (member == null) return GetSignInUserResponseDto.notExistUser();

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSignInUserResponseDto.success(member);

    }


    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String email) {

        Member member = null;

        try {

            member = memberRepository.findByEmail(email);
            if (member == null) return GetUserResponseDto.notExistUser();

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetUserResponseDto.success(member);

    }
    @Override
    public ResponseEntity<? super PatchEmailResponseDto> patchEmail(PatchEmailRequestDto dto, String email) {
        try{
           String mail =  dto.getEmail();
           boolean existEmail = memberRepository.existsByEmail(mail);
             if(existEmail){
                return PatchEmailResponseDto.duplicateEmail();
              }
            Member member = memberRepository.findByEmail(email);
            if(member == null){
                return PatchEmailResponseDto.notExistUser();
            }
            member.patchEmail(dto);

            memberRepository.save(member);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchEmailResponseDto.success();
    }

















}

