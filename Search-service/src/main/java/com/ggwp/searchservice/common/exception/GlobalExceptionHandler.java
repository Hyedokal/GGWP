package com.ggwp.searchservice.common.exception;

import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.match.service.MatchService;
import com.ggwp.searchservice.summoner.dto.ResponseSummonerDto;
import com.ggwp.searchservice.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MatchService matchService;
    private final SummonerService summonerService;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto.Error> handlerException(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new ResponseDto.Error(e.getErrorCode().getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto.Error> validationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseEntity.status(ErrorCode.InValidException.getHttpStatus())
                .body(new ResponseDto.Error(errors.get(0)));
    }

    @ExceptionHandler(CustomException.FeignClientError.class)
    public ResponseEntity<ResponseDto.Error> excelErrorException(CustomException.FeignClientError e) {
        return ResponseEntity.status(ErrorCode.NotFeginException.getHttpStatus())
                .body(new ResponseDto.Error("Feign Exception: " + e.getFeignException().getMessage()));
    }

    @ExceptionHandler(CustomException.NotFoundAccountException.class)
    public ResponseDto<ResponseSummonerDto> handleNotFoundException(CustomException.NotFoundAccountException ex) {

        matchService.createMatches(ex.getFrontDto());

        return ResponseDto.success(summonerService.getSummoner(ex.getFrontDto()));
    }

}
