package com.zuo.model.module.security.core;

import com.zuo.model.module.security.core.properties.BrowserProperties;
import com.zuo.model.module.security.core.properties.ValidateCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取配置类
 */
@Component
@ConfigurationProperties(prefix = "zuo.security")
public class SecurityProperties {
    private BrowserProperties browser=new BrowserProperties();
    private ValidateCodeProperties code =new ValidateCodeProperties();

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
