package com.zuo.model.utils.common.rest;

import java.io.Serializable;
import java.util.Random;

/**
 * 请求报文头定义
 */
public class RequestHead extends AbstractMessageHead implements Serializable {
    private static final long serialVersionUID = 1L;

    public RequestHead() {
        super();
        this.setRequestNo(newRequestNo());
    }

    /**
     * 生成新的报文流水号
     * @return
     */
    private String newRequestNo() {
        long time = this.getCreateTime().getTime();
        Random random = new Random();
        random.setSeed(time);
        return String.valueOf(time) + String.valueOf(random.nextLong());
    }
}
