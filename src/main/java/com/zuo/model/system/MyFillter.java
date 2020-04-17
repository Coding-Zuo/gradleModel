package com.zuo.model.system;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * filter记录不了controller 是j2ee提供的 interceptor能
 */
//@Component
public class MyFillter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
