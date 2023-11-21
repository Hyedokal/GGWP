package com.ggwp.memberservice.global.exception;

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
  WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요."),

  //게시판
  NOT_FOUND_RECIPE(HttpStatus.NOT_FOUND, "레시피를 찾을 수 없습니다."),

  //공지사항
  NOT_FOUND_NOTICE(HttpStatus.NOT_FOUND, "공지를 찾을 수 없습니다."),

  //나눔 게시판
  NOT_FOUND_SHARE_BOARD(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),

  //좋아요
  NOT_FOUND_LIKE(HttpStatus.NOT_FOUND, "좋아요를 찾을 수 없습니다."),
  ALREADY_LIKES_RECIPE(HttpStatus.NOT_FOUND, "이미 좋아요를 누른 레시피 입니다."),

  //즐겨찾기
  NOT_FOUND_FAVORITE(HttpStatus.NOT_FOUND, "츨겨찾기를 찾을 수 없습니다."),
  ALREADY_ADDED_FAVORITE(HttpStatus.NOT_FOUND, "이미 즐겨찾기된 유저 입니다."),

  //레시피 저장
  NOT_FOUND_SAVED_RECIPE(HttpStatus.NOT_FOUND, "저장된 레시피를 찾을 수 없습니다."),
  ALREADY_ADDED_SAVED_RECIPE(HttpStatus.NOT_FOUND, "이미 저장된 레시피 입니다."),

  //공통
  NOT_NULL_TITLE(HttpStatus.BAD_REQUEST, "제목을 입력해 주세요."),
  NOT_NULL_CONTENT(HttpStatus.BAD_REQUEST, "내용을 입력해 주세요."),
  NO_CONTENT(HttpStatus.NO_CONTENT, "일치하는 내용이 없습니다."),
  NO_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다."),
  NOT_EXIST_USER_ATTRIBUTE_IN_WEBSOCKET_SESSION(HttpStatus.NO_CONTENT, "웹소켓 세션에 유저값이 없습니다."),
  INVALID_TOKEN(HttpStatus.FORBIDDEN, "만료된 토큰입니다."),
  NOT_VERIFY_EMAIL(HttpStatus.FORBIDDEN, "이메일 인증을 하지 않아 작성 권한이 없습니다."),
  //댓글
  NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),

  //대댓글
  NOT_FOUND_REPLY_COMMENT(HttpStatus.NOT_FOUND, "대댓글을 찾을 수 없습니다.");

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
  public static final String LONG_COMMENT_MESSAGE = "댓글은 200자를 초과할 수 없습니다..";

  //레시피
  public static final String INTRO_NOT_NULL_MESSAGE = "레시피 소개를 입력해 주세요.";
  public static final String INGREDIENT_NOT_NULL_MESSAGE = "재료를 입력해 주세요.";
  public static final String COOKING_STEP_NOT_NULL_MESSAGE = "조리 순서를 입력해 주세요.";
  public static final String COOKING_TIME_NOT_NULL_MESSAGE = "조리 시간을 입력해 주세요.";
  public static final String SERVING_NOT_NULL_MESSAGE = "몇 인분인지 입력해 주세요.";
  public static final String CATEGORY_NOT_NULL_MESSAGE = "카테고리를 입력해 주세요.";


  //나눔게시판
  public static final String LAT_NOT_NULL_MESSAGE = "위도를 입력해 주세요.";
  public static final String LON_NOT_NULL_MESSAGE = "경도을 입력해 주세요.";
  public static final String LAT_AVERAGE_MESSAGE = "위도의 범위는 -90 ~ 90 입니다.";
  public static final String LON_AVERAGE_MESSAGE = "경도의 범위는 -180 ~ 180 입니다.";
  public static final String LONG_CONTENT_MESSAGE = "글 내용은 100자를 초과할 수 없습니다.";
  public static final String LONG_PLACE_MESSAGE = "거래장소는 50자를 초과할 수 없습니다.";

  private final HttpStatus httpStatus;
  private final String detail;
}