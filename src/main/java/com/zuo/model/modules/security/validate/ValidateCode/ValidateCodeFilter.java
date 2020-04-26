package com.zuo.model.modules.security.validate.ValidateCode;

import com.zuo.model.modules.security.core.SecurityConstants;
import com.zuo.model.modules.security.core.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 验证码过滤器
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private Set<String> urls=new HashSet<>();

    /**
     * 配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 校验码失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }
    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

//    @Override
//    public void afterPropertiesSet() throws ServletException {
//        super.afterPropertiesSet();
//        Object o=securityProperties.getCode().getImage().getUrl();
//        if(o!=null || securityProperties.getCode().getImage().getUrl()!=""){
//            String[] configUrls=StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(),",");
//            for(String url:configUrls){
//                urls.add(url);
//            }
//        }
//        urls.add("/loginProcess");
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String url=request.getRequestURI();
//        String method=request.getMethod();
//
//        boolean action=false;
//        for(String configUrl:urls){
//            if(pathMatcher.match(configUrl,url)){
//                action=true;
//            }
//        }
//        if(action /*&& StringUtils.equals(method,"POST")*/){
//            try {
//                validate(new ServletWebRequest(request),response);
//            }catch (ValidateCodeException e){
//                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
//                return;
//            }
//        }
//        filterChain.doFilter(request,response);
        ValidateCodeType type = getValidateCodeType(request);
        if (type != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }

//    private void validate(ServletWebRequest request,HttpServletResponse response) throws ServletRequestBindingException, IOException {
//        ImageCode codeInSession=(ImageCode)sessionStrategy.getAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
//        String codeInRequest= ServletRequestUtils.getStringParameter(request.getRequest(),"imageCode");
//        if(StringUtils.isBlank(codeInRequest)){
//            logger.info("验证码的值不能为空");
////            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
////            response.setContentType("application/json;charset=UTF-8");
////            response.getWriter().write("验证码的值不能为空");
//            throw new ValidateCodeException("验证码的值不能为空");
//        }
//        if(codeInSession==null){
//            logger.info("验证码不存在");
////            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
////            response.setContentType("application/json;charset=UTF-8");
////            response.getWriter().write("验证码不存在");
//            throw new ValidateCodeException("验证码不存在");
//        }
//        if(codeInSession.isExpried()){
//            sessionStrategy.removeAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
//            logger.info("验证码已过期");
////            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
////            response.setContentType("application/json;charset=UTF-8");
////            response.getWriter().write("验证码已过期");
//            throw new ValidateCodeException("验证码已过期");
//        }
//        if(!StringUtils.equals(codeInSession.getCode(),codeInRequest)){
//            logger.info("验证码不匹配");
////            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
////            response.setContentType("application/json;charset=UTF-8");
////            response.getWriter().write("验证码不匹配");
//            throw new ValidateCodeException("验证码不匹配");
//        }
//        sessionStrategy.removeAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
//    }

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

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
