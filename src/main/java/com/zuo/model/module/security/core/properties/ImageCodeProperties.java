package com.zuo.model.module.security.core.properties;

public class ImageCodeProperties extends SmsCodeProperties {

    private int width = 67;
    private int height = 23;

    public ImageCodeProperties() {
        setLength(4);//默认值
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
