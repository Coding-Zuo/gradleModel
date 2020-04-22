package com.zuo.model.config.security.validate;

import com.zuo.model.config.security.validate.ImageCode.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 可扩展更高级的验证码效果
 */
//@Component("imageCodeGenerator")
public class KuozhanCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        return null;
    }
}
