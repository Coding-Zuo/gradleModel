package com.zuo.model.config.security.validate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码过滤器
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {


    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url=request.getRequestURI();
        String method=request.getMethod();
        if(StringUtils.equals("/loginProcess",url) && StringUtils.equals(method,"POST")){
            try {
                validate(new ServletWebRequest(request),response);
            }catch (ValidateCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    private void validate(ServletWebRequest request,HttpServletResponse response) throws ServletRequestBindingException, IOException {
        ImageCode codeInSession=(ImageCode)sessionStrategy.getAttribute(request,ValidateCodeController.SESSION_KEY);
        String codeInRequest= ServletRequestUtils.getStringParameter(request.getRequest(),"imageCode");
        if(StringUtils.isBlank(codeInRequest)){
            logger.info("验证码的值不能为空");
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setContentType("application/json;charset=UTF-8");
//            response.getWriter().write("验证码的值不能为空");
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if(codeInSession==null){
            logger.info("验证码不存在");
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setContentType("application/json;charset=UTF-8");
//            response.getWriter().write("验证码不存在");
            throw new ValidateCodeException("验证码不存在");
        }
        if(codeInSession.isExpried()){
            sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY);
            logger.info("验证码已过期");
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setContentType("application/json;charset=UTF-8");
//            response.getWriter().write("验证码已过期");
            throw new ValidateCodeException("验证码已过期");
        }
        if(!StringUtils.equals(codeInSession.getCode(),codeInRequest)){
            logger.info("验证码不匹配");
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setContentType("application/json;charset=UTF-8");
//            response.getWriter().write("验证码不匹配");
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY);
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }
}
