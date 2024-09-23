package com.study.SpringSecurityMybatis.exception;

import org.apache.logging.log4j.message.Message;

public class AccessTokenValidException extends RuntimeException{

    public AccessTokenValidException(String Message) {
        super(Message);
    }
}
