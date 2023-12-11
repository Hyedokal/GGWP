package com.ggwp.noticeservice.common.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {


    USER("ROLE_USER"),ADMIN("ROLE_ADMIN");

    private final String key;
}
