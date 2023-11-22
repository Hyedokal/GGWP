package com.ggwp.memberservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(16)", unique = true, nullable = false)
    private String userId; // 이멜

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String password;

    @Column(columnDefinition = "varchar(16)", nullable = false)
    private  String nickname;

    // mhlee :: 어디에 어떤 용도로 사용하는 값일까??
    @Column(unique = true)
    private String uuid; //이 필드가 유일한 값임을 보장

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(16)", nullable = false)
    private UserRole role;

    // mhlee :: 이건 클라이언트에서 관리해야 하는 값임
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

    @Data
    @Accessors(chain = true)
    public static class Vo {
        private Long id;
        private String userId;
        private String nickname;
        private String uuid;
        private UserRole role;
    }

    public Vo toVo() {
        return new Vo()
                .setId(id)
                .setUserId(userId)
                .setNickname(nickname)
                .setUuid(uuid)
                .setRole(role);
    }
}
