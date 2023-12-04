package com.ggwp.announceservice.controller;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.dto.response.ResponseAnnounceDto;
import com.ggwp.announceservice.repository.AnnounceRepository;
import com.ggwp.announceservice.service.AnnounceService;
import com.ggwp.announceservice.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trx")
@RequiredArgsConstructor
public class TransactionTestController {

    private final TransactionService transactionService;

    private final AnnounceRepository repository;

    @GetMapping("/trx1")
    public String transaction1() {
        var id = transactionService.create(
                new RequestAnnounceDto()
                        .setATitle("헬로")
                        .setAContent("월드")
        );

        var entity = repository.findById(id).orElseThrow();
        return entity.toString();
    }

    @GetMapping("/trx2")
    public String transaction2() {
        var id = transactionService.create(
                new RequestAnnounceDto()
                        .setATitle("헬로")
                        .setAContent("월드")
        );

        transactionService.modifyWithTransaction(id, "헬로-변경된 제목");

        var entity = repository.findById(id).orElseThrow();
        return entity.toString();
    }

    @GetMapping("/trx3")
    public String transaction3() {
        var id = transactionService.create(
                new RequestAnnounceDto()
                        .setATitle("헬로")
                        .setAContent("월드")
        );

        transactionService.modifyWithoutTransaction(id, "헬로-변경된 제목");

        var entity = repository.findById(id).orElseThrow();
        return entity.toString();
    }

    @GetMapping("/trx4")
    public String transaction4() {
        var id = transactionService.create(
                new RequestAnnounceDto()
                        .setATitle("헬로")
                        .setAContent("월드")
        );

        transactionService.modifyWithoutTransactionAndSave(id, "헬로-변경된 제목");

        var entity = repository.findById(id).orElseThrow();
        return entity.toString();
    }

    @GetMapping("/trx5")
    public String transaction5() {
        Long id = null;
        Long id2 = null;
        try {
            id = transactionService.create(
                    new RequestAnnounceDto()
                            .setATitle("헬로")
                            .setAContent("월드")
            );



            id2 = transactionService.writeWithException(
                    new RequestAnnounceDto()
                            .setATitle("헬로222")
                            .setAContent("월드222")
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            var entity = repository.findById(id);
            if (entity.isPresent()) {
                System.out.println(entity.get().toString());
                System.out.println("트랜잭션 1 완료!!!");
            } else {
                System.out.println("트랜잭션 1 실패!!!");
            }


            return "hello";
        }

    }

    @GetMapping("/trx6")
    @Transactional
    public String transaction6() {
        Long id = null;
        Long id2 = null;
        try {
            id = transactionService.create(
                    new RequestAnnounceDto()
                            .setATitle("헬로")
                            .setAContent("월드")
            );



            id2 = transactionService.writeWithException(
                    new RequestAnnounceDto()
                            .setATitle("헬로222")
                            .setAContent("월드222")
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            var entity = repository.findById(id);
            if (entity.isPresent()) {
                System.out.println(entity.get().toString());
                System.out.println("트랜잭션 1 완료!!!");
            } else {
                System.out.println("트랜잭션 1 실패!!!");
            }


            return "hello";
        }

    }
}
