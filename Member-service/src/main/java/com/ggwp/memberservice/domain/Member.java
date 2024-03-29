package com.ggwp.memberservice.domain;


import com.ggwp.memberservice.dto.request.user.PatchLolNickNameTagRequestDto;
import com.ggwp.memberservice.dto.request.user.PatchProfileImageRequestDto;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(35)", unique = true, nullable = false)
    private String email; // 이멜

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String password;

    @Column(columnDefinition = "varchar(16)", nullable = false)
    private String lolNickname;

    @Column(columnDefinition = "varchar(16)", nullable = false)
    private String tag;

    @Column(nullable = false)
    private Boolean agreedPersonal;

    @Column(unique = true)
    private String uuid; //이 필드가 유일한 값임을 보장

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(16)", nullable = false)
    private UserRole role;


    @Column(columnDefinition = "varchar(255)", nullable = true)
    private String profileImageUrl;

    @ElementCollection
    @CollectionTable(name = "member_personalities", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "personality")
    private List<String> personalities;

    @Builder
    public Member(Long id, String email, String password, String lolNickname,
                  String uuid, UserRole role, String tag, Boolean agreedPersonal, String profileImageUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.lolNickname = lolNickname;
        this.tag = tag;
        this.uuid = uuid;
        this.role = role != null ? role : UserRole.USER;
        this.agreedPersonal = agreedPersonal;
        this.profileImageUrl = profileImageUrl;

    }

    @PrePersist
    private void ensureUuidIsGenerated() {
        if (uuid == null || uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString(); // UUID 자동 생성
        }
    }



    public void patchLolNickNameTag(PatchLolNickNameTagRequestDto dto) {
        this.lolNickname = dto.getLolNickName();
        this.tag = dto.getTag();
    }




    public void setPersonalities(List<String> personalities) {
        this.personalities = personalities;
    }



    public void patchProfileImage(PatchProfileImageRequestDto dto) {
        this.profileImageUrl = dto.getProfileImage();
    }
}