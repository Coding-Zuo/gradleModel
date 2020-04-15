package com.zuo.model.utils.common.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractController {
    protected Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    protected HttpServletRequest httpServletRequest;
    @Autowired
    protected HttpSession httpSession;

    @Value("${app.instance.errorPrefix:}")
    protected String prefix;

    protected String module;

    public AbstractController() {
    }

    public AbstractController(String module) {
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    protected String getRemoteAddr() {
        String ip  = httpServletRequest.getHeader("x-real-ip");
        if (StringUtils.isEmpty(ip)) {
            ip = httpServletRequest.getRemoteAddr();
        }
        return ip;
    }

    protected String getUserId() {
        return (String) httpSession.getAttribute("userId");
    }

    protected String getUserName() {
        return (String) httpSession.getAttribute("userName");
    }

    protected String getUserIdAndName() {
        String userId = getUserId();
        if (userId == null) {
            return null;
        }
        String userName = getUserName();
        if (userName == null) {
            return null;
        }
        return getUserId() + "_" + getUserName();
    }
}
