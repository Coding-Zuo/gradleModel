package com.zuo.model.module.security.validate.smsCode;

import com.zuo.model.module.security.core.SecurityProperties;
import com.zuo.model.module.security.validate.ValidateCode;
import com.zuo.model.module.security.validate.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class SmsCodeGenerator implements ValidateCodeGenerator {

    /**
     * 系统配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code= RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code,securityProperties.getCode().getSms().getExpireIn());
    }


    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }
}
