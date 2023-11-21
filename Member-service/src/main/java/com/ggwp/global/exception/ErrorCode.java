package com.ggwp.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  // 유저
  ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "이미 등록 되어있는 사용자입니다."),
  NOT_FOUND_OTHER_USER(HttpStatus.NOT_FOUND, "상대방이 존재하지 않습니다."),
  NOT_FOUND_CHATROOM(HttpStatus.NOT_FOUND, "해당하는 채팅방을 찾을 수 없습니다."),
  NOT_FOUND_CHAT(HttpStatus.NOT_FOUND, "해당하는 채팅을 찾을 수 없습니다."),
  CANNOT_OPEN_CHATROOM_YOURSELF(HttpStatus.FORBIDDEN, "자신과의 채팅방은 생성이 불가합니다."),
  ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이미 등록 되어있는 이메일입니다."),
  ALREADY_EXIST_NICKNAME(HttpStatus.BAD_REQUEST, "이미 등록 되어있는 닉네임입니다."),
  ALREADY_EXIST_PHONENUMBER(HttpStatus.BAD_REQUEST, "이미 등록 되어있는 휴대폰 번호입니다."),
  ALREADY_LOGOUT_TOKEN(HttpStatus.BAD_REQUEST, "로그아웃된 회원입니다. 로그인을 다시 해주세요."),
  NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
  ALREADY_VERIFIED(HttpStatus.BAD_REQUEST, "이미 인증이 완료된 사용자입니다."),
  WRONG_VERIFY_CODE(HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다."),
  EXPIRED_CODE(HttpStatus.BAD_REQUEST, "인증시간이 만료되었습니다."),
  NOT_OWNER_OF_THE_POST(HttpStatus.FORBIDDEN, "게시물 소유자가 아닙니다."),
  CHECK_ID_AND_PW(HttpStatus.NOT_FOUND, "이메일 혹은 비밀번호를 확인하세요."),
  CHECK_SOCIAL_SERVER(HttpStatus.NOT_FOUND, "소셜로그인에 실패하였습니다. 서버를 확인 하세요."),
  SHORT_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상이여야 합니다."),
  WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요.");


  //ExceptionHandler 에서 MethodArgumentNotValidException 용으로 사용
  //유저
  public static final String NAME_NOT_NULL_MESSAGE = "이름을 입력해 주세요.";
  public static final String LONG_NAME_MESSAGE = "이름의 길이는 10글자를 초과할 수 없습니다.";
  public static final String NICKNAME_NOT_NULL_MESSAGE = "닉네임을 입력해 주세요.";
  public static final String LONG_NICKNAME_MESSAGE = "닉네임 길이는 15글자를 초과할 수 없습니다.";
  public static final String SHORT_PASSWORD_LENGTH_MESSAGE = "비밀번호는 8자 이상이여야 합니다.";
  public static final String PASSWORD_NOT_NULL_MESSAGE = "비밀번호를 입력해 주세요.";
  public static final String PASSWORD_CHECK_MESSAGE = "비밀번호를 확인 주세요.";
  public static final String PHONE_NUMBER_NOT_NULL_MESSAGE = "휴대폰 번호를 입력해 주세요.";
  public static final String PHONE_NUMBER_FORMAT_MESSAGE = "휴대폰 번호의 형식은 ex:010-1234-1234 형식이여야 합니다.";
  public static final String EMAIL_NOT_NULL_MESSAGE = "이메일을 입력해 주세요.";
  public static final String EMAIL_FORMAT_NOT_CORRECT_MESSAGE = "이메일 형식은 abc@def.gh 형식이여야 합니다.";

  //공통
  public static final String TITLE_NOT_NULL_MESSAGE = "제목을 입력해 주세요.";
  public static final String CONTENT_NOT_NULL_MESSAGE = "내용을 입력해 주세요.";
  private final HttpStatus httpStatus;
  private final String detail;
}