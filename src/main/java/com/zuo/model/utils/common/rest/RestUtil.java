package com.zuo.model.utils.common.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * @author liuhongbin
 * @date 2017/8/14
 * @description: REST功能类，定义常用的方法
 **/
@Component
public class RestUtil {
    private static Log logger = LogFactory.getLog(RestUtil.class);

    private static String SUCCESS_CODE = "00000";
    private static String SUCCESS_MSG = "处理成功";

    public static String ERROR_INTERNAL_CODE = "99";
    public static String ERROR_INTERNAL_MSG = "网络通讯异常";

    public static String ERROR_UNKNOW_CODE = "98";
    public static String ERROR_UNKNOW_MSG = "未知错误";

    @Autowired
    private RestTemplate restTemplate;


    private static RestUtil restUtil;
    
    @PostConstruct
    public void init() {
        restUtil = this;
        restUtil.restTemplate = this.restTemplate;
    }

    public static HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        if (!StringUtils.isEmpty(token)) {
            headers.add("access_token", token);
            headers.add("Authorization", "Bearer " + token);
        }
        return headers;
    }


    public static HttpHeaders getHeaders(String token, Map<String, Object> map) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        if (!StringUtils.isEmpty(token)) {
            headers.add("access_token", token);
            headers.add("Authorization", "Bearer " + token);
        }
        if (!StringUtils.isEmpty(map) && map.size() > 0) {
            if (!StringUtils.isEmpty(map.get("channel"))) {
                headers.add("sysFlag", map.get("channel").toString());
            }
            if (!StringUtils.isEmpty(map.get("channelNo"))) {
                headers.add("channelNo", map.get("channelNo").toString());
            }
        }
        return headers;
    }


    public static String restPost(String url, String token, String data, int responseCode) {
        return restExchange(HttpMethod.POST, url, token, data, responseCode);
    }
    
    public static String restPut(String url, String token, String data, int responseCode) {
        return restExchange(HttpMethod.PUT, url, token, data, responseCode);
    }

    public static String restGet(String url, String token, Integer responseCode) {
        return restExchange(HttpMethod.GET, url, token, null, responseCode);
    }
    public static String restGet(String url, String token) {
        return restGet(url, token, HttpStatus.OK.value());
    }
    public static String restGet(String url) {
        return restGet(url, null);
    }
    
    public static String restDelete(String url, String token, Integer responseCode) {
        return restExchange(HttpMethod.DELETE, url, token, null, responseCode);
    }
    public static String restDelete(String url, String token, String data, Integer responseCode) {
        return restExchange(HttpMethod.DELETE, url, token, data, responseCode);
    }
    
    public static String restExchange(HttpMethod method, String url, String token, String data, Integer responseCode) {
        try {
//            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity;
            
            HttpHeaders headers = getHeaders(token);
            if (data != null) {
                HttpEntity<String> reqE = new HttpEntity<>(data, headers);
                responseEntity = restUtil.restTemplate.exchange(url, method, reqE, String.class);
            } else {
                HttpEntity reqE = new HttpEntity<>(headers);
                responseEntity = restUtil.restTemplate.exchange(url, method, reqE, String.class);
            }

            HttpStatus status = responseEntity.getStatusCode();
            if (responseCode == null || status.value() == responseCode.intValue()) {
                return responseEntity.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("restExchangeMap失败：" + e.getMessage());
            return null;
        }
    }
    
    public static Map<String, Object> restPostMap(String url, String token, Map<String, Object> data, Integer responseCode) {
        return restExchangeMap(HttpMethod.POST, url, token, data, responseCode);
    }
    public static Map<String, Object> restPostMapOrigin(String url, String token, Map<String, Object> data, Integer responseCode) {
        return restExchangeMapOrigin(HttpMethod.POST, url, token, data, responseCode);
    }
    public static Map<String, Object> restPostMap(String url, Map<String, Object> data, Integer responseCode) {
        return restExchangeMap(HttpMethod.POST, url, null, data, responseCode);
    }
    public static Map<String, Object> restPostMap(String url, String token, Map<String, Object> data) {
        return restUtil.restPostMap(url, token, data, HttpStatus.OK.value());
    }
    public static Map<String, Object> restPostMapOrigin(String url, String token, Map<String, Object> data) {
        return restUtil.restPostMapOrigin(url, token, data, HttpStatus.OK.value());
    }
    public static Map<String, Object> restPostMap(String url, Map<String, Object> data) {
        return restPostMap(url, null, data, HttpStatus.OK.value());
    }
    public static Map<String, Object> restDeleteMap(String url, Map<String, Object> data) {
        return restDeleteMap(url, null, data, HttpStatus.OK.value());
    }
    public static Map<String, Object> restDeleteMap(String url, String token, Map<String, Object> data, Integer responseCode) {
        return restExchangeMap(HttpMethod.DELETE, url, token, data, responseCode);
    }
    public static Map<String, Object> restPutMap(String url, String token, Map<String, Object> data, Integer responseCode) {
        return restExchangeMap(HttpMethod.PUT, url, token, data, responseCode);
    }
    public static Map<String, Object> restPutMap(String url, Map<String, Object> data, Integer responseCode) {
        return restExchangeMap(HttpMethod.PUT, url, null, data, responseCode);
    }
    public static Map<String, Object> restPutMap(String url, String token, Map<String, Object> data) {
        return restUtil.restPutMap(url, token, data, HttpStatus.OK.value());
    }
    public static Map<String, Object> restPutMap(String url, Map<String, Object> data) {
        return restPutMap(url, null, data, HttpStatus.OK.value());
    }
    public static Map<String, Object> restGetMap(String url, String token, int responseCode) {
        return restExchangeMap(HttpMethod.GET, url, token, null, responseCode);
    }
    public static Map<String, Object> restGetMap(String url, int responseCode) {
        return restGetMap(url, null, responseCode);
    }
    public static Map<String, Object> restGetMap(String url) {
        return restGetMap(url, HttpStatus.OK.value());
    }
    
    public static Map<String, Object> restExchangeMapOrigin(HttpMethod method, String url, String token, Map<String, Object> data, Integer responseCode) {
        return restExchangeMap(method, true, url, token, data, responseCode);
    }
    public static Map<String, Object> restExchangeMap(HttpMethod method, String url, String token, Map<String, Object> data, Integer responseCode) {
        return restExchangeMap(method, false, url, token, data, responseCode);
    }
    public static Map<String, Object> restExchangeMap(HttpMethod method, boolean isOrigin, String url, String token, Map<String, Object> data, Integer responseCode) {
        try {
            ResponseEntity<Map> responseEntity;
            
            HttpHeaders headers = getHeaders(token, data);
            logger.debug("request=="+new JSONObject(data));
            if (data != null) {
                HttpEntity<Map<String, Object>> reqE = new HttpEntity<>(data, headers);
                if (isOrigin) {
                    responseEntity = new RestTemplate().exchange(url, method, reqE, Map.class);
                } else {
                    responseEntity = restUtil.restTemplate.exchange(url, method, reqE, Map.class);
                }
               
            } else {
                HttpEntity reqE = new HttpEntity<>(headers);
                if (isOrigin) {
                    responseEntity = new RestTemplate().exchange(url, method, reqE, Map.class);
                } else {
                    responseEntity = restUtil.restTemplate.exchange(url, method, reqE, Map.class);
                }
            }

            HttpStatus status = responseEntity.getStatusCode();
            if (responseCode == null || status.value() == responseCode.intValue()) {
            	 logger.debug("response=="+responseEntity.getBody());
                return responseEntity.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("restExchangeMap失败：" + e.getMessage());
            return null;
        }
    }
    
    /**
     * 把json字符串（对象）转换成Map
     * @param json
     * @return
     */
    public static Map<String, Object> json2Map(String json) {
        if (json == null) {
            return null;
        }
        JSONObject jo = JSONObject.parseObject(json);
        Map<String, Object> map = new LinkedHashMap<>();
        for (Object k : jo.keySet()) {
            Object v = jo.get(k.toString());
            if (v == null) {
                v = "";
            } else if (v instanceof JSONArray) {
                v = json2List(v.toString());
            }
            map.put(k.toString(), v);
        }
        return map;
    }

    /**
     * 把json字符串（数组）转换为List
     * @param json
     * @return
     */
    public static List<Map<String, Object>> json2List(String json) {
        if (json == null) {
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        JSONArray ja = JSONArray.parseArray(json);
        for (int i = 0; i < ja.size(); i ++) {
            String subJson = ja.get(i).toString();
            Map<String, Object> map = json2Map(subJson);
            list.add(map);
        }
        return list;
    }

    /**
     * 递归方式深层转换json字符串为map
     * @param json
     * @return
     */
    public static HashMap<String, Object> json2DeepMap(String json) {
        if (json == null) {
            return null;
        }
        JSONObject jo = JSONObject.parseObject(json);
        HashMap<String, Object> map = new HashMap<>();
        for (Object k : jo.keySet()) {
            Object v = jo.get(k.toString());
            if (v == null) {
                v = "";
            } else if (v instanceof JSONArray) {
                v = json2DeepList(v.toString());
            } else if (v instanceof JSONObject) {
                v = json2DeepMap(JSONObject.toJSONString(v));
            }
            map.put(k.toString(), v);
        }
        return map;
    }

    /**
     * 把json字符串（数组）深层转换为List
     * @param json
     * @return
     */
    public static List<Object> json2DeepList(String json) {
        if (json == null) {
            return null;
        }
        List<Object> list = new ArrayList<>();
        JSONArray ja = JSONArray.parseArray(json);
        for (int i = 0; i < ja.size(); i ++) {
            String subJson = ja.get(i).toString();
            if (subJson.startsWith("{") && subJson.endsWith("}")) {
                Map<String, Object> map = json2DeepMap(subJson);
                list.add(map);
            } else {
                list.add(subJson);
            }
        }
        return list;
    }

    /**
     * 获取返回结果码，可用于判断是否成功
     * @param json
     * @return
     */
    public static String getReturnCode(String json) {
        Map<String, Object> map = json2Map(json);
        return getReturnCode(map);
    }

    /**
     * 获取返回结果码，可用于判断是否成功
     * @param map
     * @return
     */
    public static String getReturnCode(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        Map<String, Object> mapHead;
        if (map.get("head") instanceof Map) {
            mapHead = (Map<String, Object>) map.get("head");
        } else if (map.get("head") instanceof ResponseHead) {
            return ((ResponseHead)map.get("head")).getRetFlag();
        } else {
            mapHead = json2Map(map.get("head").toString());
        }
        return mapHead.get("retFlag").toString();
    }

    /**
     * 判断是否成功
     * @param json
     * @return
     */
    public static boolean isSuccess(String json) {
        return getReturnCode(json).equals("00000") || getReturnCode(json).equals("0000");
    }

    /**
     * 判断是否成功
     * @param map
     * @return
     */
    public static boolean isSuccess(Map<String, Object> map) {
        return getReturnCode(map).equals("00000") || getReturnCode(map).equals("0000");
    }

    /**
     * 获取头信息，兼容Map/ResponseHead
     * @param resultMap
     * @return map:{"retFlag":"00000","retMsg":"处理成功"}
     */
    public static Map<String, Object> getHeadMap(Map<String, Object> resultMap) {
        if (resultMap == null || resultMap.isEmpty()) {
            return null;
        }
        if(resultMap.get("response") != null) {
            resultMap = (Map<String, Object>)resultMap.get("response");
        }
        if (resultMap.get("head") instanceof Map) {
            return (Map<String, Object>) resultMap.get("head");
        } else if (resultMap.get("head") instanceof ResponseHead) {
            Map<String, Object> map = new HashMap<>();
            map.put("retFlag", ((ResponseHead) resultMap.get("head")).getRetFlag());
            map.put("retMsg", ((ResponseHead) resultMap.get("head")).getRetMsg());
            return map;
        } else {
            return null;
        }
    }

    /**
     * 兼容多个头格式获取retFlag
     * @param resultMap
     * @return
     */
    public static String getRetFlag(Map<String, Object> resultMap) {
        Map<String, Object> headMap = getHeadMap(resultMap);
        if (headMap == null) {
            return null;
        }
        return StringUtils.isEmpty(headMap.get("retFlag")) ? "" : headMap.get("retFlag").toString();
    }

    /**
     * 兼容多个头格式获取retMsg
     * @param resultMap
     * @return
     */
    public static String getRetMsg(Map<String, Object> resultMap) {
        Map<String, Object> headMap = getHeadMap(resultMap);
        if (headMap == null) {
            return null;
        }
        return StringUtils.isEmpty(headMap.get("retMsg")) ? "" : headMap.get("retMsg").toString();
    }

    public static Map<String, Object> success() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("head", new ResponseHead(SUCCESS_CODE, SUCCESS_MSG));
        return resultMap;
    }
    public static Map<String, Object> success(Object result) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("head", new ResponseHead(SUCCESS_CODE, SUCCESS_MSG));
        resultMap.put("body", result);
        return resultMap;
    }
    public static Map<String, Object> fail(String retFlag, String retMsg) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("head", new ResponseHead(retFlag, retMsg));
        return resultMap;
    }

}
