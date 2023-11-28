package com.ggwp.memberservice.domain;


import jakarta.persistence.*;

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

    @Column(columnDefinition = "varchar(16)", unique = true, nullable = false)
    private String email; // 이멜

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String password;

    @Column(columnDefinition = "varchar(16)", nullable = false)
    private String lolNickname;

    @Column(columnDefinition = "varchar(16)", nullable = false)
    private String tag;

    @Column
    private Boolean agreedPersonal;

    // mhlee :: 어디에 어떤 용도로 사용하는 값일까??
    @Column(unique = true)
    private String uuid; //이 필드가 유일한 값임을 보장

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(16)", nullable = false)
    private UserRole role;

    @Builder
    public Member(Long id, String email, String password, String lolNickname,
                  String uuid, UserRole role, String tag, Boolean agreedPersonal) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.lolNickname = lolNickname;
        this.tag = tag;
        this.uuid = uuid;
        this.role = role != null ? role : UserRole.USER;
        this.agreedPersonal = agreedPersonal;

    }

    @PrePersist
    private void ensureUuidIsGenerated() {
        if (uuid == null || uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString(); // UUID 자동 생성
        }
    }

}