package com.zuo.model.modules.security.core.social;

import javax.sql.DataSource;

import com.zuo.model.modules.security.core.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;


/**
 *
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
		repository.setTablePrefix("t_");
		if(connectionSignUp != null) {
			repository.setConnectionSignUp(connectionSignUp);
		}
		return repository;
	}
	/*
	*
	create table UserConnection (userId varchar(255) not null,
		providerId varchar(255) not null,
		providerUserId varchar(255),
		rank int not null,
		displayName varchar(255),
		profileUrl varchar(512),
		imageUrl varchar(512),
		accessToken varchar(512) not null,
		secret varchar(512),
		refreshToken varchar(512),
		expireTime bigint,
		primary key (userId, providerId, providerUserId));
	create unique index UserConnectionRank on UserConnection(userId, providerId, rank);
	* */

	@Bean
	public SpringSocialConfigurer imoocSocialSecurityConfig() {
		String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
		MySpringSocialConfigurer configurer = new MySpringSocialConfigurer(filterProcessesUrl);
		configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
		return configurer;
	}

//	@Bean
//	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
//		return new ProviderSignInUtils(connectionFactoryLocator,
//				getUsersConnectionRepository(connectionFactoryLocator)) {
//		};
//	}
}
