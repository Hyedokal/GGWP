package com.ggwp.noticeservice.exception;

import java.util.NoSuchElementException;

public class NoticeNotFoundException extends NoSuchElementException {

    public NoticeNotFoundException(String message){
        super(message);
    }
}
