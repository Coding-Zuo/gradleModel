package com.zuo.model.utils.common.rest;

import com.alibaba.fastjson.JSON;
import com.zuo.model.utils.common.db.PageResult;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回报文统一封装格式
 */
public class RestResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_CODE = "00000";
    public static final String SUCCESS_MSG = "成功";
    public static final String UNKOWN_CODE = "99999";
    public static final String UNKOWN_MSG = "网络通讯异常";

    private ResponseHead head;
    private Map<String, Object> body;

    public RestResponse() {
        this.head = new ResponseHead();
        this.body = new HashMap<>();
    }

    public RestResponse(ResponseHead head, Map<String, Object> body) {
        this.head = head;
        this.body = body;
    }

    /**
     * 创建成功响应报文
     * @param requestNo 请求流水号
     * @return
     */
    public static RestResponse SUCCESS(String requestNo) {
        RestResponse response = new RestResponse();
        response.putHead(requestNo, SUCCESS_CODE, SUCCESS_MSG);
        return response;
    }

    /**
     * 创建失败响应报文
     * @param requestNo 请求流水号
     * @param retFlag 返回码
     * @param retMsg 返回消息
     * @return
     */
    public static RestResponse FAIL(String requestNo, String retFlag, String retMsg) {
        RestResponse response = new RestResponse();
        response.putHead(requestNo, retFlag, retMsg);
        return response;
    }

    /**
     * 创建发生未知异常时的失败响应报文
     * @param requestNo 请求流水号
     * @return
     */
    public static RestResponse UNKOWN(String requestNo) {
        RestResponse response = new RestResponse();
        response.putHead(requestNo, UNKOWN_CODE, UNKOWN_MSG);
        return response;
    }

    /**
     * 给报文head赋值，传入参数为Null或空字符串时，不进行赋值
     * @param requestNo 请求报文流水号
     * @param retFlag 返回码
     * @param retMsg 返回消息
     */
    public void putHead(String requestNo, String retFlag, String retMsg) {
        if (!StringUtils.isEmpty(requestNo)) {
            head.setRequestNo(requestNo);
        }
        if (!StringUtils.isEmpty(retFlag)) {
            head.setRetFlag(retFlag);
        }
        if (!StringUtils.isEmpty(retMsg)) {
            head.setRetMsg(retMsg);
        }
    }

    /**
     * 获取报文流水号
     * @return
     */
    public String pickRequestNo() {
        return head.getRequestNo();
    }

    /**
     * 获取报文创建时间
     * @return
     */
    public Date pickCreateTime() {
        return head.getCreateTime();
    }

    /**
     * 获取返回码
     * @return
     */
    public String pickRetFlag() {
        return head.getRetFlag();
    }

    /**
     * 获取返回消息
     * @return
     */
    public String pickRetMsg() {
        return head.getRetMsg();
    }

    /**
     * 给报文body赋值，同map类的put操作
     * @param key item key
     * @param value item value
     */
    public void putBodyValue(String key, Object value) {
        body.put(key, value);
    }

    /**
     * 设置报文Body为分页查询结果
     * @param pageResult
     */
    public void putPageResultAsBody(PageResult pageResult) {
        body = (Map<String, Object>) JSON.toJSON(pageResult);
    }

    public String pickBodyString(String key) {
        if (this.body.containsKey(key)) {
            return MapUtils.getString(body, key);
        } else {
            return null;
        }
    }

    /**
     * 设置报文Body为查询结果列表
     * @param list
     */
    public void putListAsBody(List list) {
        putBodyValue(RestConsts.KEY_RESULT_LIST, list);
    }

    /**
     * 获取body值，同map类的get操作
     * @param key item key
     * @return
     */
    public Object pickBodyValue(String key) {
        return body.get(key);
    }

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}
