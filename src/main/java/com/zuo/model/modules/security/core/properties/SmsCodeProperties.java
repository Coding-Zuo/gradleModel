package com.zuo.model.modules.security.core.properties;

public class SmsCodeProperties {

    private int length = 6;
    private int expireIn = 60;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public int getLength() {
        return length;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }


    public void setLength(int length) {
        this.length = length;
    }

}
