package com.zuo.model.utils.common.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractService {
    protected Log logger = LogFactory.getLog(this.getClass());

    /**
     * 根据页号和每页行数，计算起始行数，用于分页参数
     * @param page 页号
     * @param size 每页行数
     * @return 起始行数
     */
    protected Integer getPageStart(Integer page, Integer size) {
        return (page - 1) * size;
    }
}
