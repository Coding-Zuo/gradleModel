package com.zuo.model.module.security.validate;

import com.zuo.model.module.security.core.SecurityProperties;
import com.zuo.model.module.security.validate.ImageCode.ImageCodeGenerator;
import com.zuo.model.module.security.validate.smsCode.DefaultSmsCodeSender;
import com.zuo.model.module.security.validate.smsCode.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")//不存在这个bean的时候做这个配置
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator codeGenerator=new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsCodeSender")//不存在这个bean的时候做这个配置
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }

}
