package com.zuo.model.modules.security.validate.smsCode;

import com.zuo.model.modules.security.core.SecurityProperties;
import com.zuo.model.modules.security.validate.ValidateCode.ValidateCode;
import com.zuo.model.modules.security.validate.ValidateCode.ValidateCodeGenerator;
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
