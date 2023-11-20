package com.ggwp.commentservice.domain;

import com.ggwp.commentservice.dto.RequestCommentDto;
import com.ggwp.commentservice.enums.Position;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cId;

    //Member 객체를 Join해 올 칼럼. member-service 작성 완료시 활성화.
//    @ManyToOne
//    @JoinColumn(name = "uuid")
//    private Member member;

//    @ManyToOne
//    @JoinColumn(name = "s_id")
//    private Squad squad;

    @Enumerated(EnumType.STRING)
    private Position cMyPos;

    @Column
    @Length(max = 100, message = "메모는 100자 이하까지 작성 가능합니다.")
    private String cMemo;

    private Boolean cMic;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    //엔티티 수정을 위한 메서드
    public void updateComment(RequestCommentDto dto){
        this.cMyPos = dto.getCMyPos();
        this.cMemo = dto.getCMemo();
        this.cMic = dto.getCMic();
        this.updatedAt = LocalDateTime.now();
    }
}
