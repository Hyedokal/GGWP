package com.ggwp.commentservice.enums;

//로마자를 정수로 변환하기 위한 클래스.
public enum RomanNum {
    I("I", 1),
    II("II", 2),
    III("III", 3),
    IV("IV", 4);

    private final String romanNum;
    private final int value;

    RomanNum(String romanNum, int value) {
        this.romanNum = romanNum;
        this.value = value;
    }

    public static int getValueByRomanNum(String romanNum) {
        for (RomanNum roman : values()) {
            if (roman.romanNum.equals(romanNum)) {
                return roman.value;
            }
        }
        return -1;
    }
}
