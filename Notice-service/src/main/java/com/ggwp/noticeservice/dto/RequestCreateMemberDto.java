package com.ggwp.noticeservice.dto;

import com.ggwp.noticeservice.domain.Member;
import com.ggwp.noticeservice.domain.Notice;
import com.ggwp.noticeservice.enums.NoticeEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCreateMemberDto {

    private String username;

    private String email;

    public Member toEntity(){
        return Member.builder()
                .username(this.username)
                .email(this.email)
                .build();
    }
}
