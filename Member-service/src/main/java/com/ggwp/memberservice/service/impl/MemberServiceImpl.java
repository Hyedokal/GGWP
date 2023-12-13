package com.ggwp.memberservice.service.impl;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.dto.request.user.PatchLolNickNameRequestDto;
import com.ggwp.memberservice.dto.request.user.PatchTag;
import com.ggwp.memberservice.dto.request.user.PersonalitiesRequestDto;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.user.*;
import com.ggwp.memberservice.repository.MemberRepository;
import com.ggwp.memberservice.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

        } catch (Exception exception) {
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

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetUserResponseDto.success(member);

    }

    @Override
    public ResponseEntity<? super PatchLolNickNameResponseDto> patchLolNickName(PatchLolNickNameRequestDto dto, String uuid) {
        try {
            Member member = memberRepository.findByUuid(uuid);
            if (member == null) {
                return PatchLolNickNameResponseDto.notExistUser();
            }
            member.patchLolNickName(dto);

            memberRepository.save(member);


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchLolNickNameResponseDto.success();
    }

    @Override
    public void patchTag(String uuid, PatchTag patchTagDto) {

        try {
            Member member = memberRepository.findByUuid(uuid);
            if (member == null) {
                throw new EntityNotFoundException();
            }
            member.patchTag(patchTagDto);
            memberRepository.save(member); // Save the updated member

        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    @Override
    public ResponseEntity<? super PersonalitiesResponseDto> addPersonalities(String uuid, PersonalitiesRequestDto dto) {
        try {
            Member member = memberRepository.findByUuid(uuid);

            // Check if the member exists
            if (member == null) {
                return PersonalitiesResponseDto.notExistUser();
            }

            // Set the personalities and save the member
            member.setPersonalities(dto.getPersonalities());
            memberRepository.save(member);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        // Return success response
        return PersonalitiesResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PersonalitiesInfoResponseDto> getPersonalities(String uuid) {
        Member member;

        try {
            member = memberRepository.findByUuid(uuid);
            if (member == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(PersonalitiesInfoResponseDto.notExistUser());
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(PersonalitiesInfoResponseDto.databaseError()); // Assuming you have a databaseError method in PersonalitiesInfoResponseDto
        }

        List<String> personalities = member.getPersonalities(); // Assuming getPersonalities() returns List<String>
        PersonalitiesInfoResponseDto responseDto = PersonalitiesInfoResponseDto.success(personalities);

        return ResponseEntity.ok(responseDto);
    }


}