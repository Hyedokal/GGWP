package com.ggwp.memberservice.global.handler;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.global.jwt.JwtTokenProvider;
import com.ggwp.memberservice.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtTokenProvider jwtTokenProvider;
  private final MemberRepository memberRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    String email = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
    String accessToken = jwtTokenProvider.createAccessToken(
        email); // JwtService의 createAccessToken을 사용하여 AccessToken 발급
    String refreshToken = jwtTokenProvider.createRefreshToken(); // JwtService의 createRefreshToken을 사용하여 RefreshToken 발급

    jwtTokenProvider.sendAccessAndRefreshToken(response, accessToken,
        refreshToken); // 응답 헤더에 AccessToken, RefreshToken 실어서 응답

    memberRepository.findByEmail(email)
        .ifPresent(member -> {
          member.updateRefreshToken(refreshToken);
          memberRepository.saveAndFlush(member);
        });
  }

  private String extractUsername(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return userDetails.getUsername();
  }
}