package com.ggwp.memberservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String userId; // 이멜

    @NotBlank
    private String password;


    private  String nickname;


    @Column(unique = true)
    private String uuid; //이 필드가 유일한 값임을 보장

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String refreshToken; // 리프레시 토큰
    @Builder
    public Member(Long id, String userId, String password, String nickname,
                  String uuid, UserRole role, String refreshToken) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.uuid = uuid;
        this.role = role != null ? role : UserRole.USER;
        this.refreshToken = refreshToken;
    }

    @PrePersist
    private void ensureUuidIsGenerated() {
        if (uuid == null || uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString(); // UUID 자동 생성
        }
    }


    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
