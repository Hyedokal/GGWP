package com.ggwp.memberservice.service.impl;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.dto.feign.request.FeignLolNickNameTagResponseDto;
import com.ggwp.memberservice.dto.feign.request.RequestMatchDto;
import com.ggwp.memberservice.dto.feign.response.FeignLolNickNameTagRequestDto;
import com.ggwp.memberservice.dto.feign.response.ResponseMatchDto;
import com.ggwp.memberservice.dto.request.user.PatchLolNickNameTagRequestDto;
import com.ggwp.memberservice.dto.request.user.PersonalitiesRequestDto;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.user.*;
import com.ggwp.memberservice.feign.SquadFeignClient;
import com.ggwp.memberservice.repository.MemberRepository;
import com.ggwp.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final SquadFeignClient squadFeignClient;

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
    public ResponseEntity<? super PatchLolNickNameTagResponseDto> patchLolNickTag(PatchLolNickNameTagRequestDto dto, String uuid) {
        try {
            Member member = memberRepository.findByUuid(uuid);
            if (member == null) {
                return PatchLolNickNameTagResponseDto.notExistUser();
            }
            Member existingMember = memberRepository.findByLolNickNameAndTag(dto.getLolNickName(), dto.getTag());
            if (existingMember != null && !existingMember.getUuid().equals(uuid)) {
                return PatchLolNickNameTagResponseDto.nicknameAndTagAlreadyTaken();
            }


            FeignLolNickNameTagRequestDto requestBody = new FeignLolNickNameTagRequestDto(member.getLolNickname(), member.getTag(), dto.getLolNickName(), dto.getTag());
            ResponseEntity<FeignLolNickNameTagResponseDto> feignResponse = squadFeignClient.feignLolNickNameTag(requestBody);

            if (feignResponse.getStatusCode() != HttpStatus.OK) {
                return PatchLolNickNameTagResponseDto.notExistUser();
            }else{
                member.patchLolNickNameTag(dto);
                memberRepository.save(member);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchLolNickNameTagResponseDto.success();
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


    //todo    //uuid로 맴버를 찾음 없으면 notExistUser
    //        // 맴버가 있으면 lolNickname, tag를 가져옴
    //        //feignClient로 lolNickname, tag를 보내서 매치정보를 받아옴
    //        //ResponseMatchDto에 lolNickname, tag, sIdList를 넣어서 반환
    @Override
    public ResponseEntity<? super GetMatchInfoResponseDto> getMatchInfo(String uuid) {
        Member member = memberRepository.findByUuid(uuid); // uuid로 맴버를 찾음 없으면 notExistUser
        if (member == null) {
            return GetMatchInfoResponseDto.notExistUser();
        }

        String lolNickname = member.getLolNickname(); // 맴버가 있으면 lolNickname, tag를 가져옴
        String tag = member.getTag();

        RequestMatchDto requestMatchDto = new RequestMatchDto(); // feignClient로 lolNickname, tag를 보내서 매치정보를 받아옴
        requestMatchDto.setSummonerName(lolNickname);
        requestMatchDto.setTagLine(tag);
        ResponseEntity<ResponseMatchDto> response = squadFeignClient.getMatchInfo(requestMatchDto); //todo feignClient로 lolNickname, tag를 보내서 매치정보를 받아옴

        if (response.getStatusCode() == HttpStatus.OK) {
            ResponseMatchDto responseMatchDto = response.getBody();
            return GetMatchInfoResponseDto.success(responseMatchDto); //ResponseMatchDto에 lolNickname, tag, sIdList를 넣어서 반환
        }else{
            return GetMatchInfoResponseDto.notExistUser();
        }
    }

}