package com.ggwp.announceservice.service.impl;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.entity.Announce;
import com.ggwp.announceservice.repository.AnnounceRepository;
import com.ggwp.announceservice.service.TransactionService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionTestServiceImpl implements TransactionService {

    private final AnnounceRepository announceRepository;

    private final EntityManager em;

    @Transactional
    @Override
    public long create(RequestAnnounceDto dto) {
        Announce announce = dto.toEntity();
        var id = announceRepository.save(announce).getAId();
        em.flush();
        return id;
    }

    @Transactional
    @Override
    public long modifyWithTransaction(long id, String title) {
        var announce = announceRepository.findById(id).orElseThrow();
        announce.setATitle(title);

        // repository.save 없음
        return announce.getAId();
    }

    @Override
    public long modifyWithoutTransaction(long id, String title) {
        var announce = announceRepository.findById(id).orElseThrow();
        announce.setATitle(title);

        // repository.save 없음
        return announce.getAId();
    }

    @Override
    public long modifyWithoutTransactionAndSave(long id, String title) {
        var announce = announceRepository.findById(id).orElseThrow();
        announce.setATitle(title);

        // repository.save 있음
        return announceRepository.save(announce).getAId();
    }

    @Transactional
    @Override
    public long writeWithException(RequestAnnounceDto dto) {
        throw new RuntimeException("exception");
    }
}
