package com.zuo.model.config.security.validate;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 验证码
 */
public class ImageCode extends ValidateCode{
    private BufferedImage image;
    private String code;
    private LocalDateTime expireTime;



    public ImageCode(BufferedImage image,String code,int expireIn){
        super(code, expireIn);
        this.image=image;
        this.code=code;
        this.expireTime=LocalDateTime.now().plusSeconds(expireIn);
    }

    public BufferedImage getImage() {
        return image;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
