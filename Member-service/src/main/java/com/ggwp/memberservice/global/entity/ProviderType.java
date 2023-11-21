package com.ggwp.memberservice.global.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProviderType {
  LOCAL("LOCAL"),
  NAVER("NAVER"),
  KAKAO("KAKAO"),
  GOOGLE("GOOGLE");

  private final String value;

}