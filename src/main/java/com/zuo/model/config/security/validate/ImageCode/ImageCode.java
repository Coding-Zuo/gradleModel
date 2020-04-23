package com.zuo.model.config.security.validate.ImageCode;

import com.zuo.model.config.security.validate.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 验证码
 */
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    public ImageCode(BufferedImage image,String code,int expireIn){
        super(code, expireIn);
        this.image=image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
