package com.ggwp.memberservice.controller;


import com.ggwp.memberservice.dto.request.user.PatchLolNickNameTagRequestDto;
import com.ggwp.memberservice.dto.request.user.PatchProfileImageRequestDto;
import com.ggwp.memberservice.dto.request.user.PersonalitiesRequestDto;
import com.ggwp.memberservice.dto.response.user.*;
import com.ggwp.memberservice.service.FileService;
import com.ggwp.memberservice.service.MemberService;
import jakarta.validation.Valid;
import jakarta.ws.rs.PATCH;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/member-service/v1/member")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;
    private  final FileService fileService;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Member Service is Healthy");
    }

    @GetMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(@AuthenticationPrincipal String uuid) {
        ResponseEntity<? super GetSignInUserResponseDto> response = memberService.getSignInUser(uuid);
        return response;
    }


        @GetMapping("/userInfo")
        public ResponseEntity<? super GetUserResponseDto> getUser(
                @AuthenticationPrincipal String uuid
        ) {
            ResponseEntity<? super GetUserResponseDto> response = memberService.getUser(uuid);
            return response;
        }

        @GetMapping("match/list")
        public ResponseEntity<? super GetMatchInfoResponseDto> getMatchList(
                @AuthenticationPrincipal String uuid
        ) {
            ResponseEntity<? super GetMatchInfoResponseDto> response = memberService.getMatchInfo(uuid);
            return response;
        }







    @PutMapping("/lolNicknameTag")
    public ResponseEntity<? super PatchLolNickNameTagResponseDto> patchLolNickNameTag(
            @RequestBody @Valid PatchLolNickNameTagRequestDto requestBody,
            @AuthenticationPrincipal String uuid
            ){
        ResponseEntity<? super PatchLolNickNameTagResponseDto> response = memberService.patchLolNickTag(requestBody, uuid);
        return response;
    }


        @PostMapping("/personalities")
    public ResponseEntity<? super PersonalitiesResponseDto> addMemberPersonalities(
            @RequestBody @Valid PersonalitiesRequestDto requestBody,
            @AuthenticationPrincipal String uuid
    ){
        ResponseEntity<? super PersonalitiesResponseDto> response = memberService.addPersonalities(uuid, requestBody);
        return response;
    }


    @GetMapping("/personalities")
    public ResponseEntity<? super PersonalitiesInfoResponseDto> getPersonalities(@AuthenticationPrincipal String uuid) {
        return memberService.getPersonalities(uuid);
    }


    //프사 추가
    @PatchMapping("/profile-image")
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(
            @RequestBody @Valid PatchProfileImageRequestDto requestBody,
            @AuthenticationPrincipal String uuid
    ) {
        ResponseEntity<? super PatchProfileImageResponseDto> response = memberService.patchProfileImage(requestBody, uuid);
        return response;
    }

    @PostMapping("profile-image/upload")
    public String upload(
            @RequestParam("file") MultipartFile file

    ) {
        String url = fileService.upload(file);
        return url;
    }
    @GetMapping(value="profile-image/{fileName}", produces={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getFile(
            @PathVariable("fileName") String fileName
    ) {
        Resource resource = fileService.getFile(fileName);
        return resource;
    }


}

