package com.ggwp.global.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ggwp.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Getter
@Slf4j
public class JwtTokenProvider {

  //환경변수에 키값 지정
  @Value("${spring.jwt.secret-key}")
  private String secretKey;

  @Value("${spring.jwt.access.expiration}")
  private Long accessTokenExpirationPeriod;

  @Value("${spring.jwt.refresh.expiration}")
  private Long refreshTokenExpirationPeriod;

  @Value("${spring.jwt.access.header}")
  private String accessHeader;

  @Value("${spring.jwt.refresh.header}")
  private String refreshHeader;
  /**
   * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "email"으로 설정 JWT의 헤더에 들어오는 값 : 'Authorization(Key)
   * = Bearer {토큰} (Value)' 형식
   */
  private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
  private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
  private static final String EMAIL_CLAIM = "email";
  private static final String BEARER = "Bearer ";

  private final MemberRepository memberRepository;

  public String createAccessToken(String email) {
    Date now = new Date();
    return JWT.create() // JWT 토큰을 생성하는 빌더 반환
        .withSubject(ACCESS_TOKEN_SUBJECT) // JWT의 Subject 지정 -> AccessToken이므로 AccessToken
        .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) // 토큰 만료 시간 설정

        .withClaim(EMAIL_CLAIM, email)
        .sign(Algorithm.HMAC512(
            secretKey)); // HMAC512 알고리즘 사용, application-jwt.yml에서 지정한 secret 키로 암호화
  }


  public String createRefreshToken() {
    Date now = new Date();
    return JWT.create()
        .withSubject(REFRESH_TOKEN_SUBJECT)
        .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
        .sign(Algorithm.HMAC512(secretKey));
  }

  public void sendAccessToken(HttpServletResponse response, String accessToken) {
    response.setStatus(HttpServletResponse.SC_OK);

    response.setHeader(accessHeader, accessToken);
    log.info("재발급된 Access Token : {}", accessToken);
  }

  public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken,
      String refreshToken) throws IOException {
    response.setStatus(HttpServletResponse.SC_OK);

    setAccessTokenHeader(response, "Bearer " + accessToken);
    setRefreshTokenHeader(response, "Bearer " + refreshToken);

    response.setStatus(HttpStatus.OK.value());
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json;charset=UTF-8");

    String successMessage = "{\"AccessToken\": \"" + accessToken +
        "\", \"refreshToken\": \"" + refreshToken + "\"}";
    response.getWriter().write(successMessage);
    log.info("Access Token, Refresh Token 헤더 설정 완료");
  }

  public Optional<String> extractRefreshToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(refreshHeader))
        .filter(refreshToken -> refreshToken.startsWith(BEARER))
        .map(refreshToken -> refreshToken.replace(BEARER, ""));
  }

  public Optional<String> extractAccessToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(accessHeader))
        .filter(refreshToken -> refreshToken.startsWith(BEARER))
        .map(refreshToken -> refreshToken.replace(BEARER, ""));
  }

  public Optional<String> extractEmail(String accessToken) {
    try {
      // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
      return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
          .build() // 반환된 빌더로 JWT verifier 생성
          .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
          .getClaim(EMAIL_CLAIM) // claim(Emial) 가져오기
          .asString());
    } catch (Exception e) {
      log.error("액세스 토큰이 유효하지 않습니다.");
      return Optional.empty();
    }
  }
  public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
    response.setHeader(accessHeader, accessToken);
  }

  public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
    response.setHeader(refreshHeader, refreshToken);
  }

  public void updateRefreshToken(String email, String refreshToken) {
    memberRepository.findByEmail(email)
        .ifPresentOrElse(
            member -> member.updateRefreshToken(refreshToken),
            () -> new Exception("일치하는 회원이 없습니다.")
        );
  }

  public boolean isTokenValid(String token) {
    try {
      JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
      return true;
    } catch (SignatureVerificationException e) {
      log.error("Signature verification failed: {}", e.getMessage());
    } catch (TokenExpiredException e) {
      log.error("Token expired: {}", e.getMessage());
    } catch (Exception e) {
      log.error("Invalid token: {}", e.getMessage());
    }
    return false;
  }
}
