package com.zuo.model.modules.security.validate.ValidateCode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 处理生成逻辑
 * 如果生成逻辑变了就改这里
 */
public interface ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);
}
