package com.zuo.model.utils.common.rest;

import com.alibaba.fastjson.JSONArray;
import com.zuo.model.utils.common.db.QueryParam;
import org.apache.commons.collections.MapUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求报文统一封装格式
 */
public class RestRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private RequestHead head;
    private Map<String, Object> body;

    public RestRequest() {
        this.head = new RequestHead();
        this.body = new HashMap<>();
    }

    public RestRequest(RequestHead head, Map<String, Object> body) {
        this.head = head;
        this.body = body;
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
     * 获取body值，同map类的get操作
     * @param key item key
     * @return
     */
    public Object pickBodyValue(String key) {
        return body.get(key);
    }
    public String pickBodyString(String key) {
        if (this.body.containsKey(key)) {
            return MapUtils.getString(body, key);
        } else {
            return null;
        }
    }
    public Double pickBodyDouble(String key) {
        try {
            return Double.parseDouble(this.pickBodyString(key));
        } catch (Exception e) {
            return null;
        }
    }
    public Integer pickBodyInt(String key) {
        try {
            return MapUtils.getInteger(body, key);
        } catch (Exception e) {
            return null;
        }
    }

    public Long pickBodyLong(String key) {
        try {
            return MapUtils.getLong(body, key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取查询参数列表
     * @return
     */
    public List<QueryParam> pickQueryParams() {
        JSONArray array = (JSONArray) pickBodyValue(RestConsts.KEY_QUERY_PARAMS);
        return array.toJavaList(QueryParam.class);
    }

    /**
     * 获取页号
     * @return
     */
    public Integer pickPageNum() {
        return Integer.valueOf(pickBodyValue(RestConsts.KEY_PAGE_NUM).toString());
    }

    /**
     * 获取每页行数
     * @return
     */
    public Integer pickPageSize() {
        return Integer.valueOf(pickBodyValue(RestConsts.KEY_PAGE_SIZE).toString());
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

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}
