/**
 * 
 */
package com.zuo.model.config.security.validate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;


/**
 *
 */
@ApiModel(value = "验证码类",description = "描述验证码的属性，验证码、过时时间")
public class ValidateCode {

	@ApiModelProperty(value = "验证码")
	private String code;
	@ApiModelProperty(value = "超时时间",name = "expireTime",example = "60")
	private LocalDateTime expireTime;
	
	public ValidateCode(String code, int expireIn){
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public ValidateCode(String code, LocalDateTime expireTime){
		this.code = code;
		this.expireTime = expireTime;
	}
	
	public boolean isExpried() {
		return LocalDateTime.now().isAfter(expireTime);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}
	
}
