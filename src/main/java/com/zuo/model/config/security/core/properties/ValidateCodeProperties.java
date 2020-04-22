package com.zuo.model.config.security.core.properties;

import com.zuo.model.config.security.core.properties.ImageCodeProperties;

public class ValidateCodeProperties {
    private ImageCodeProperties image=new ImageCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
