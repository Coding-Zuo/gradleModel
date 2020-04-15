package com.zuo.model.utils.common.db;


import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询条件定义
 */
public class QueryParam {
    private String fieldName;
    private String operator1;
    private Object value1;
    private String operator2;
    private Object value2;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOperator1() {
        return operator1;
    }

    public void setOperator1(String operator1) {
        this.operator1 = operator1;
    }

    public Object getValue1() {
        return value1;
    }

    public void setValue1(Object value1) {
        this.value1 = value1;
    }

    public String getOperator2() {
        return operator2;
    }

    public void setOperator2(String operator2) {
        this.operator2 = operator2;
    }

    public Object getValue2() {
        return value2;
    }

    public void setValue2(Object value2) {
        this.value2 = value2;
    }

    /***************** 以下为查询条件相关功能实现方法 *********************/

    /**
     * 根据参数Map生成查询参数列表，全部为“[字段]=[值]”的条件格式
     * @param paramMap
     * @return
     */
    public static List<QueryParam> fromParamMap(Map<String, Object> paramMap) {
        List<QueryParam> paramList = new ArrayList<>();
        for(String key : paramMap.keySet()) {
            QueryParam param = new QueryParam();
            param.setFieldName(key);
            param.setOperator1("=");
            param.setValue1(paramMap.get(key));
            param.setOperator2("");
            param.setValue2("");
            paramList.add(param);
        }
        return paramList;
    }

    /**
     * 根据Map生成where子句，全部为“[字段]=[值]”的条件格式
     * @param paramMap
     * @return
     */
    public static String getWhereFromMap(Map<String, Object> paramMap) {
        return QueryParam.getWhere(QueryParam.fromParamMap(paramMap));
    }

    /**
     * 生成where子句
     * @param paramList
     * @return
     */
    public static String getWhere(List<QueryParam> paramList) {
        StringBuffer sb = new StringBuffer();
        for (QueryParam param : paramList) {
            sb.append(" AND ").append(param.getWhere());
        }
        String where = sb.toString();
        return StringUtils.isEmpty(where) ? "" : where.substring(5);
    }

    /**
     * 生成where子句
     * @return
     */
    public String getWhere() {
        StringBuffer sb = new StringBuffer();
        sb.append(toDbName(fieldName)).append(" ");
        sb.append(operator1).append(" ");
        sb.append(getQuotedValue(value1)).append(" ");
        if (!StringUtils.isEmpty(operator2) && value2 != null) {
            sb.append(operator2).append(" ");
            sb.append(getQuotedValue(value2)).append(" ");
        }
        return sb.toString();
    }

    /**
     * 属性名转换成数据库字段名：驼峰式转成下划线格式
     * @param fieldName 属性名(驼峰式，首字母小写)
     * @return 字段名（下划线格式）
     */
    private String toDbName(String fieldName) {
        char[] arr = fieldName.toCharArray();
        StringBuffer sb = new StringBuffer();
        for(char c : arr) {
            if (Character.isUpperCase(c)) {
                sb.append("_" + Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 根据类型返回带引号或不带引号的参数值字符串
     * 支持的类型："Integer","Long","Boolean","Double","Timestamp","Date","Time","String"
     * @param value
     * @return
     */
    private String getQuotedValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        } else if (value instanceof java.util.Date) {
            return "'" + new SimpleDateFormat("yyyy-MM-dd").format(value) + "'";
        } else if (value instanceof Date || value instanceof Time || value instanceof Timestamp) {
            return "'" + value.toString() + "'";
        } else {
            return value.toString();
        }
    }
}
