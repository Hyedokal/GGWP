package com.ggwp.memberservice.service;

import java.util.List;

public interface IBadWord {
    List<String> extractBadWords(String str);
}
