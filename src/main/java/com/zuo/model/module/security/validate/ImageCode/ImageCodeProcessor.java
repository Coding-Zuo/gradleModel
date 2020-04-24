/**
 * 
 */
package com.zuo.model.module.security.validate.ImageCode;

import javax.imageio.ImageIO;

import com.zuo.model.module.security.validate.ValidateCodeException;
import com.zuo.model.module.security.validate.ValidateCodeProcessor;
import com.zuo.model.module.security.validate.impl.AbstractValidateCodeProcessor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 图片验证码处理器
 * 
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
	private Logger logger= LoggerFactory.getLogger(getClass());
	/**
	 * 发送图形验证码，将其写到响应中
	 */
	@Override
	protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
		ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
	}

	@Override
	public void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
		ImageCode codeInSession=(ImageCode)sessionStrategy.getAttribute(servletWebRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
		String codeInRequest= ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(),"imageCode");
		if(StringUtils.isBlank(codeInRequest)){
			logger.info("验证码的值不能为空");
			throw new ValidateCodeException("验证码的值不能为空");
		}
		if(codeInSession==null){
			logger.info("验证码不存在");
			throw new ValidateCodeException("验证码不存在");
		}
		if(codeInSession.isExpried()){
			sessionStrategy.removeAttribute(servletWebRequest,ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
			logger.info("验证码已过期");
			throw new ValidateCodeException("验证码已过期");
		}
		if(!StringUtils.equals(codeInSession.getCode(),codeInRequest)){
			logger.info("验证码不匹配");
			throw new ValidateCodeException("验证码不匹配");
		}
		sessionStrategy.removeAttribute(servletWebRequest,ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
	}

	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}

	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}
}
