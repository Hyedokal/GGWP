package com.ggwp.announceservice.service;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;

public interface TransactionService {

    long create(RequestAnnounceDto dto);

    // 트랜잭션 애노테이션 사용
    long modifyWithTransaction(long id, String title);

    // 트랜잭션 애노테이션 사용
    long modifyWithoutTransaction(long id, String title);

    // 트랜잭션 애노테이션 사용
    long modifyWithoutTransactionAndSave(long id, String title);

    // exception 발생
    long writeWithException(RequestAnnounceDto dto);
}
