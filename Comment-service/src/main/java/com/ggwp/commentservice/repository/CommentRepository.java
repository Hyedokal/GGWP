package com.ggwp.commentservice.repository;

import com.ggwp.commentservice.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllBysId(Long sId);


    List<Comment> findBySummonerNameAndTagLine(String summonerName, String tagLine);


    @Query("SELECT c.sId FROM Comment c WHERE c.summonerName = :summonerName AND c.tagLine = :tagLine")
    List<Long> findSidsBySummonerNameAndTagLine(@Param("summonerName") String summonerName, @Param("tagLine") String tagLine);



}
