package com.zuo.model.config.security.validate.smsCode;

//短信提供商
public interface SmsCodeSender {

    void send(String moblie, String code);

}
