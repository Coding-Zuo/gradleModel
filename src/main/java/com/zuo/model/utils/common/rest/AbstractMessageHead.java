package com.zuo.model.utils.common.rest;

import java.util.Date;

/**
 * 报文头基类，定义通用参数
 */
public abstract class AbstractMessageHead {

    /**
     * 请求流水号，响应报文返回与请求相同的流水号
     */
    private String requestNo;

    /**
     * 报文创建时间，默认为对象创建时间
     */
    private Date createTime;

    private String token;

    public AbstractMessageHead() {
        this.requestNo = "";
        this.token = "";
        this.createTime = new Date();
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
