package com.ggwp.domain;

import com.ggwp.global.entity.BaseEntity;
import com.ggwp.global.entity.ProviderType;
import com.ggwp.global.entity.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String email; // 이멜

    @NotBlank
    private String password;

    @Column(unique = true)
    private  String lolNickname;


    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    private String refreshToken; // 리프레시 토큰

    @Enumerated(EnumType.STRING)
    private RoleType role;


    private String uuid; //이 필드가 유일한 값임을 보장

    @Builder
    public Member(Long id, String email, String password, String lolNickname , String socialId, String refreshToken, RoleType role, String uuid) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.lolNickname = lolNickname;
        this.socialId = " ";
        this.refreshToken = refreshToken;
        this.role = role;
        this.uuid = uuid;
    }



    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    @PrePersist
    private void ensureUuidIsGenerated() {
        if (uuid == null || uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString(); // UUID 자동 생성
        }
    }
}
