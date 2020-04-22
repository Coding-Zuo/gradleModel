package com.zuo.model.controller;


import com.zuo.model.config.security.core.SecurityProperties;
import com.zuo.model.utils.common.mvc.AbstractController;
import com.zuo.model.utils.common.rest.RestResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class IndexController extends AbstractController {

    //把当前的请求缓存到session中
    private RequestCache requestCache = new HttpSessionRequestCache();

    //跳转
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @RequestMapping(value = {"/", "index"})
    @ApiOperation(value = "首页测试")
    public String Index(Map<String,Object> params) {
        return "index";
    }

    @RequestMapping(value = {"login"})
    @ApiOperation(value = "登录页面")
    public String login() {
        return "backend/login";
    }

    @RequestMapping(value = {"loginProcess"})
    @ApiOperation(value = "登录处理")
    public String loginProcess() {
        return "index";
    }

//    @RequestMapping(value = "authentication/require")
//    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
//    @ApiOperation(value = "当需要身份验证时跳转到这里")
//    public RestResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        SavedRequest savedRequest = requestCache.getRequest(request, response);
//        if (savedRequest != null) {
//            String targetUrl = savedRequest.getRedirectUrl();
//            logger.info("引发跳转的请求是" + targetUrl);
//            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
//                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
//            }
//        }
//        RestResponse resp = RestResponse.FAIL("401","LOGIN401","访问的服务需要身份认证，清引导用户到登录页");
//        return resp;
//    }

}
