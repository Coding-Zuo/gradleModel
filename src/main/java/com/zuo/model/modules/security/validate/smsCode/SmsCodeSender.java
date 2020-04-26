package com.zuo.model.modules.security.validate.smsCode;

//短信提供商
public interface SmsCodeSender {

    void send(String moblie, String code);

}
