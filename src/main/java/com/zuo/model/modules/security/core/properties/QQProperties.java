package com.zuo.model.modules.security.core.properties;

import com.zuo.model.modules.security.core.social.config.SocialProperties;

/**
 *
 */
public class QQProperties extends SocialProperties {
	
	private String providerId = "qq";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
}
