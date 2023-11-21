package com.ggwp.memberservice.global.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
  GUEST("GUEST"),
  USER("USER"),
  ADMIN("ADMIN");

  private final String key;

}
