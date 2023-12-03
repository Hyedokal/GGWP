package com.ggwp.searchservice.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    AccountAfterSummonerCreation(HttpStatus.BAD_GATEWAY, "비정상적인 접근입니다. Account는 소환사가 생성한 이후에 생성됩니다."),

    NotFindAccount(HttpStatus.NOT_FOUND, "Account를 찾을 수 없습니다."),

    NotFeignAccount(HttpStatus.NOT_FOUND, "Account Feign한 값을 받지 못했습니다."),

    NotFeignMatchIds(HttpStatus.NOT_FOUND, "matchId Feign한 값을 받지 못했습니다"),
    NotFeignMatch(HttpStatus.NOT_FOUND, "Match Feign한 값을 받지 못했습니다."),
    NotFindSummoner(HttpStatus.NOT_FOUND, "Summoner를 찾을 수 없습니다."),

    NotFindLeagues(HttpStatus.NOT_FOUND, "Leagues를 찾을 수 없습니다."),

    NotFindParticipants(HttpStatus.NOT_FOUND, "Participants를 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
