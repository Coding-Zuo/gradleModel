package com.zuo.model.utils.common.rest;

import java.io.Serializable;

/**
 * 响应报文头定义
 */
public class ResponseHead extends AbstractMessageHead implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    private String retFlag;
    /**
     * 返回消息
     */
    private String retMsg;
    
    public ResponseHead() {
        super();
        this.retFlag = "";
        this.retMsg = "";
    }
    
    public ResponseHead(String retFlag, String retMsg) {
        super();
        this.retFlag = retFlag;
        this.retMsg = retMsg;
    }

    public ResponseHead(String requestNo, String retFlag, String retMsg) {
        super();
        this.setRequestNo(requestNo);
        this.retFlag = retFlag;
        this.retMsg = retMsg;
    }

    public String getRetFlag() {
        return retFlag;
    }
    public void setRetFlag(String retFlag) {
        this.retFlag = retFlag;
    }
    public String getRetMsg() {
        return retMsg;
    }
    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
