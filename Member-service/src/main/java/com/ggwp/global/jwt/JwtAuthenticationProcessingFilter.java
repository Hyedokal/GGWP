package com.ggwp.global.jwt;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ggwp.domain.Member;
import com.ggwp.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


/**
 * 로그인 이외의 요청이 오면 처리하는 필터 1. RefreshToken이 없고, AccessToken이 유효한 경우 -> 인증 성공 처리, RefreshToken을 재발급하지는
 * 않는다. 2. RefreshToken이 없고, AccessToken이 없거나 유효하지 않은 경우 -> 인증 실패 처리, 403 ERROR 3. RefreshToken이 있는
 * 경우 -> DB의 RefreshToken과 비교하여 일치하면 AccessToken 재발급, RefreshToken 재발급(RTR 방식) 인증 성공 처리는 하지 않고 실패
 * 처리
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

  private static final String NO_CHECK_URL = "/v1/users/login"; // "/v1/users/login"으로 들어오는 요청은 Filter 작동 X

  private final JwtTokenProvider jwtTokenProvider;
  private final MemberRepository memberRepository;

  private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (request.getRequestURI().equals(NO_CHECK_URL)) {
      filterChain.doFilter(request, response);
      return;
    }

    // 사용자 요청 헤더에서 RefreshToken 추출
    String refreshToken = jwtTokenProvider.extractRefreshToken(request)
        .filter(jwtTokenProvider::isTokenValid)
        .orElse(null);

    if (refreshToken != null) {
      checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
      return; //재발급 하고 인증 처리는 하지 않게 하기위해 바로 return으로 필터 진행 막기
    }

    //로그아웃된 토큰인지 확인하는 로직
//    if (isTokenBlacklisted(HeaderUtil.getAccessToken(request))) {
//      response.setStatus(ALREADY_LOGOUT_TOKEN.getHttpStatus().value());
//      response.setCharacterEncoding("UTF-8");
//      response.setContentType("application/json;charset=UTF-8");
//
//      String errorMessage = "{\"status\": " + ALREADY_LOGOUT_TOKEN.getHttpStatus().value() +
//          ", \"code\": \"" + "ALREADY_LOGOUT_TOKEN" +
//          "\", \"message\": \"" + ALREADY_LOGOUT_TOKEN.getDetail() + "\"}";
//
//      response.getWriter().write(errorMessage);
//    } else }

      // RefreshToken이 없거나 유효하지 않다면, AccessToken을 검사하고 인증을 처리하는 로직 수행
      checkAccessTokenAndAuthentication(request, response, filterChain);


  }

  //리프레시 토큰으로 유저 정보 찾기 & 액세스 토큰/리프레시 토큰 재발급
  public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response,
      String refreshToken) {
    memberRepository.findByRefreshToken(refreshToken)
        .ifPresent(member -> {
          String reIssuedRefreshToken = reIssueRefreshToken(member);
          try {
            jwtTokenProvider.sendAccessAndRefreshToken(response,
                jwtTokenProvider.createAccessToken(member.getEmail()),
                reIssuedRefreshToken);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  //리프레시 재발급
  private String reIssueRefreshToken(Member member) {
    String reIssuedRefreshToken = jwtTokenProvider.createRefreshToken();
    member.updateRefreshToken(reIssuedRefreshToken);
    memberRepository.saveAndFlush(member);
    return reIssuedRefreshToken;
  }

  //액세스 토큰 체크 & 인증 처리
  public void checkAccessTokenAndAuthentication(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String accessToken = jwtTokenProvider.extractAccessToken(request).orElse(null);
    if (accessToken != null) {
      try {
        if (jwtTokenProvider.isTokenValid(accessToken)) {
          jwtTokenProvider.extractEmail(accessToken)
              .flatMap(memberRepository::findByEmail)
              .ifPresent(this::saveAuthentication);
        } else {
          // 유효하지 않은 토큰 401
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
          return;
        }
      } catch (TokenExpiredException e) {
        // 토큰이 만료 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  //인증 허가
  public void saveAuthentication(Member myMember) {
    String password = myMember.getPassword();
    if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
      password = UUID.randomUUID().toString().substring(1, 14);
    }

    UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
        .username(myMember.getEmail())
        .password(password)
        .roles(myMember.getRole().getKey())
        .build();

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(userDetailsUser, null,
            authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

//  //블랙리스트 토큰 판별
//  private boolean isTokenBlacklisted(String token) {
//    BlacklistToken blacklistToken = blacklistTokenRepository.findByBlacklistToken(token);
//    return blacklistToken != null;
//  }
}
