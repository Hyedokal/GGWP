package com.ggwp.announceservice.service;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import com.ggwp.announceservice.dto.request.RequestDto;
import com.ggwp.announceservice.entity.Announce;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("junit")
@Transactional
@RequiredArgsConstructor
class AnnounceServiceSearchTest {

    @Autowired
    private AnnounceService announceService;

    @Test
    @DisplayName("공지사항 제목으로 검색이 되고, 페이징되어야 한다.")
    void searchTest() {
        //given
        createAnnounce("공지사항 aaa 111", "블라블라");
        var secondPageSecondItem = createAnnounce("공지사항 aaa 222", "블라블라");
        var secondPageFirstItem = createAnnounce("공지사항 aaa 333", "블라블라");
        createAnnounce("공지사항 aaa 444", "블라블라");
        createAnnounce("공지사항 aab 555", "블라블라");
        createAnnounce("공지사항 bbb 666", "블라블라");

        //when
        var result = announceService.searchPagedAnnounce(
                new RequestDto.Search()
                        .setPage(1)
                        .setSize(2)
                        .setTitle("aa")
        );

        //then
        assertEquals(5, result.getTotalElements());
        assertEquals(3, result.getTotalPages());
        assertEquals(2, result.getSize());
        var content = result.getContent();
        assertEquals(secondPageFirstItem.getATitle(), content.get(0).getATitle());
        assertEquals(secondPageSecondItem.getATitle(), content.get(1).getATitle());
    }

    private Announce createAnnounce(String title, String content) {
        return announceService.writeAnnounce(new RequestAnnounceDto()
                .setATitle(title)
                .setAContent(content)
        );
    }
}