/**
 * 
 */
package com.zuo.model.modules.security.core.social.qq.config;

import com.zuo.model.modules.security.core.SecurityProperties;
import com.zuo.model.modules.security.core.properties.QQProperties;
import com.zuo.model.modules.security.core.social.config.SocialAutoConfigurerAdapter;
import com.zuo.model.modules.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "zuo.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	/*
	 */
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		QQProperties qqConfig = securityProperties.getSocial().getQq();
		return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
	}

}
