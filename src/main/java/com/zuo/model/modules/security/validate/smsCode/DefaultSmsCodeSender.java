package com.zuo.model.modules.security.validate.smsCode;

public class DefaultSmsCodeSender implements SmsCodeSender {


    @Override
    public void send(String moblie, String code) {
        System.out.println("向手机号"+moblie+"发送验证码"+code);
    }
}
