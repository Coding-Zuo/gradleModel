package com.zuo.model.modules.security.core.social.config;

public abstract class SocialProperties {
    private String appId;
    private String appSecret;
    public SocialProperties() {
    }
    public String getAppId() {
        return this.appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getAppSecret() {
        return this.appSecret;
    }
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}

