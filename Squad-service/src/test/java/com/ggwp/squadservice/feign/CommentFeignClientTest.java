//package com.ggwp.squadservice.feign;
//
//import com.ggwp.squadservice.dto.memberfeign.request.RequestCommentDto;
//import com.ggwp.squadservice.dto.memberfeign.response.ResponseCommentInfoDto;
//import com.ggwp.squadservice.dto.memberfeign.response.ResponseMatchDto;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class CommentFeignClientTest {
//
//    @Mock
//    private CommentFeignClient commentFeignClient;
//
//    @Test
//    void getSummonerDetails() {
//        // Arrange
//        RequestCommentDto requestCommentDto = new RequestCommentDto();
//        // Populate requestCommentDto as needed
//
//        ResponseCommentInfoDto responseCommentInfoDto = new ResponseCommentInfoDto();
//        // Populate responseMatchDto as needed
//
//
//
//        // Act
//        ResponseEntity<List<ResponseMatchDto>> response = commentFeignClient.getSummonerDetails(requestCommentDto);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(1, response.getBody().size());
//        verify(commentFeignClient, times(1)).getSummonerDetails(requestCommentDto);
//    }
//}