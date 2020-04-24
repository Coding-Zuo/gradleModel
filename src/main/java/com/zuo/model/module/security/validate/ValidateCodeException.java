package com.zuo.model.module.security.validate;


import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg){
        super(msg);
    }

    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }
}
