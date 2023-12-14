package com.ggwp.commentservice.service;

import com.ggwp.commentservice.domain.Comment;
import com.ggwp.commentservice.dto.request.RequestCommentDto;
import com.ggwp.commentservice.dto.request.RequestPageDto;
import com.ggwp.commentservice.enums.Position;
import com.ggwp.commentservice.enums.QType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService sut;

    private static RequestPageDto.Search provideSearchDtos() {
        RequestPageDto.Search search = new RequestPageDto.Search()
                .setPage(0)
                .setSize(2)
                .setSId(1L);
        // 필요한 검색 조건 등을 설정
        return search;
    }
//    @Test
//    @DisplayName("댓글 페이징 처리 테스트")
//    void searchPagedComment() throws Exception{
//        var dto1 = new RequestCommentDto(1L, Position.MID, QType.SOLO_RANK,
//                true, "닉네임1", "#KR1", "1번 게시글 첫 번째 댓글", null, false);
//        var dto2 = new RequestCommentDto(1L, Position.MID,
//                true, "닉네임2", "#KR1", "1번 게시글 두 번째 댓글");
//        var dto3 = new RequestCommentDto(2L, Position.MID,
//                true, "닉네임3", "#KR1", "2번 게시글 첫 번째 댓글");
//        var dto4 = new RequestCommentDto(1L, Position.MID,
//                true, "닉네임4", "#KR1", "1번 게시글 세 번째 댓글");
//        //given
//        sut.writeComment(dto1);
//        Thread.sleep(1000);
//        var firstPageSecondItem = sut.writeComment(dto2);
//        Thread.sleep(1000);
//        sut.writeComment(dto3);
//        Thread.sleep(1000);
//        var firstPageFirstItem = sut.writeComment(dto4);
//
//
//        //when
//        var result = sut.searchPagedComment(
//                new RequestPageDto.Search()
//                        .setPage(0)
//                        .setSize(2)
//                        .setSId(1L)
//        );
//
//        //then
//        assertEquals(3, result.getTotalElements());
//        assertEquals(2, result.getTotalPages());
//        assertEquals(2, result.getSize());
//        var content = result.getContent();
//        assertEquals(firstPageFirstItem.getMemo(), content.get(0).getMemo());
//        assertEquals(firstPageSecondItem.getMemo(), content.get(1).getMemo());
//    }


    @Test
    void approved기본값테스트() {
        //given
        var dto = new RequestCommentDto(4L, Position.FLEX,
                QType.SOLO_RANK, true, "HyedokaL", "8731", "메모메모",
                null, false);

        //when
        Comment comment = sut.writeComment(dto);

        //then
        Assertions.assertFalse(comment.isApproved());
    }
}