package com.zuo.model.config.security.validate;

import com.zuo.model.config.security.validate.ImageCode.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {
    ImageCode generate(ServletWebRequest request);
}
