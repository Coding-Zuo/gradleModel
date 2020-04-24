package com.zuo.model.module.security.core.properties;


import com.zuo.model.module.security.core.LoginType;

public class BrowserProperties {

    private String loginPage;

    private LoginType loginType=LoginType.JSON; //默认配置项返回JSON也可直接跳转

    private int rememberMeSeconds=604800;

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}
