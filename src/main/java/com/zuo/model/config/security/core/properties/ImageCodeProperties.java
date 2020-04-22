package com.zuo.model.config.security.core.properties;

public class ImageCodeProperties {

    private int width=67;
    private int height=23;
    private int length=5;
    private int expireIn=60;

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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
