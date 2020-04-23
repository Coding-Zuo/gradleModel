package com.zuo.model.config.security.validate;

import com.zuo.model.config.security.core.SecurityProperties;
import com.zuo.model.config.security.validate.ImageCode.ImageCode;
import com.zuo.model.config.security.validate.smsCode.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;

@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * 系统配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private Map<String,ValidateCodeProcessor> validateCodeProcessors;
    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping("/code"+ "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
//        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
        validateCodeProcessors.get(type+"CodeProcessor").create(new ServletWebRequest(request,response));
    }

//    @GetMapping("/code/image")
//    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));//生成
//        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);//放到session
//        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());//写到流里
//    }
//
//    @GetMapping("/code/sms")
//    public void phoneCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        ValidateCode validateCode = smsCodeGenerator.generate(new ServletWebRequest(request));
//        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, validateCode);
//        String moble=ServletRequestUtils.getRequiredStringParameter(request,"moble");
//        smsCodeSender.send(moble,validateCode.getCode());
//    }


    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
