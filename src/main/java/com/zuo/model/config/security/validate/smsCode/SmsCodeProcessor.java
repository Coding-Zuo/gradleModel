/**
 * 
 */
package com.zuo.model.config.security.validate.smsCode;

import com.zuo.model.config.security.validate.ValidateCode;
import com.zuo.model.config.security.validate.ValidateCodeException;
import com.zuo.model.config.security.validate.ValidateCodeProcessor;
import com.zuo.model.config.security.validate.impl.AbstractValidateCodeProcessor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 短信验证码处理器
 * 
 *
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
	private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
	private Logger logger= LoggerFactory.getLogger(getClass());
	/**
	 * 短信验证码发送器
	 */
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
//		String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
		smsCodeSender.send(mobile, validateCode.getCode());
	}

	@Override
	public void validate(ServletWebRequest request) throws ServletRequestBindingException {
		ValidateCode codeInSession=(ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");
		String codeInRequest= ServletRequestUtils.getStringParameter(request.getRequest(),"smsCode");
		if(StringUtils.isBlank(codeInRequest)){
			logger.info("验证码的值不能为空");
			throw new ValidateCodeException("验证码的值不能为空");
		}
		if(codeInSession==null){
			logger.info("验证码不存在");
			throw new ValidateCodeException("验证码不存在");
		}
		if(codeInSession.isExpried()){
			sessionStrategy.removeAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");
			logger.info("验证码已过期");
			throw new ValidateCodeException("验证码已过期");
		}
		if(!StringUtils.equals(codeInSession.getCode(),codeInRequest)){
			logger.info("验证码不匹配");
			throw new ValidateCodeException("验证码不匹配");
		}
		sessionStrategy.removeAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");
	}

	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}

	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}
}
