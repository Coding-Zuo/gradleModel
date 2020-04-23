package com.zuo.model.config.security.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 处理整个验证码生成流程 生成、存到session、发送出去
 * 如果验证码整个逻辑变了就改写这里
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session的前缀
     */
    String SESSION_KEY_PREFIX="SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验码
     * @param request ServletWebRequest request、response都可以放在这一个里面
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;
}
