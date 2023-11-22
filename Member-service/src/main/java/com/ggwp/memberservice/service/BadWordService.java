package com.ggwp.memberservice.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BadWordService implements IBadWord {

    // 우선 금칙어 리스트가 static 변수로 관리된다고 가정하자!!

    private static final List<String> BAD_WORD_LIST = List.of("개새끼", "fuck", "18", "십팔");

    private Pattern pattern = null;

    @Override
    public List<String> extractBadWords(String str) {
        // for 문으로 하나, 하나 체크하는것 보다 정규식으로 한방에 체크하자!!
        if (pattern == null) compilePattern();

        List<String> foundBadWords = new ArrayList<>();

        // 발견된 금칙어를 모두 리턴한다.
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            foundBadWords.add(matcher.group());
        }

        return foundBadWords;
    }

    private void compilePattern() {
        if (pattern != null) return;

        String regex = "\\b(" + String.join("|", BAD_WORD_LIST) + ")\\b";
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }
}
