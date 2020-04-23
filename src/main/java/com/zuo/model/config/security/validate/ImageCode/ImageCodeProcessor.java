/**
 * 
 */
package com.zuo.model.config.security.validate.ImageCode;

import javax.imageio.ImageIO;

import com.zuo.model.config.security.validate.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 图片验证码处理器
 * 
 *
 */
@Component("imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	/**
	 * 发送图形验证码，将其写到响应中
	 */
	@Override
	protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
		ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
	}

}
