package com.ggwp.announceservice.dto;

import com.ggwp.announceservice.entity.Announce;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

//공지사항 작성, 수정용 DTO
@Getter @Setter @AllArgsConstructor
@RequiredArgsConstructor
public class RequestAnnounceDto {

    @NotBlank(message = "제목은 반드시 입력되어야 합니다.")
    @Length(max = 50, message = "제목의 길이는 50자를 초과할 수 없습니다.")
    private String aTitle;
    @NotBlank(message = "내용은 반드시 입력되어야 합니다.")
    @Length(max = 1000, message = "내용의 길이는 1000자를 초과할 수 없습니다.")
    private String aContent;

    public Announce toEntity(){
        return Announce.builder()
                .aTitle(this.aTitle)
                .aContent(this.aContent)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
