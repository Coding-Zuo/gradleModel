package com.zuo.model.modules.security.validate.ValidateCode;

import com.zuo.model.modules.security.core.SecurityConstants;
import com.zuo.model.modules.security.core.SecurityProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "验证码接口,使用spring依赖搜索 实现图片、短信验证码，方便扩展")
public class ValidateCodeController {

    /**
     * 系统配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @ApiOperation(value = "验证码接口",notes = "短信、图片验证码根据请求调用")
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
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
