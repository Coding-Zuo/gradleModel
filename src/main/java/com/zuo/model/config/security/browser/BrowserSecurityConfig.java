package com.zuo.model.config.security.browser;

import com.zuo.model.config.security.core.SecurityProperties;
import com.zuo.model.config.security.validate.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter=new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage(securityProperties.getBrowser().getLoginPage())
//                .failureUrl("/login-error") //自定义登录界面
                .loginProcessingUrl("/loginProcess")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/static/**","/css/**", "/js/**", "/fonts/**",
                        securityProperties.getBrowser().getLoginPage(),"/code/image").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();

//        http.authorizeRequests()
//                .antMatchers("/css/**", "/js/**", "/fonts/**").permitAll() //都可以访问
//                .antMatchers("/admin/**").hasRole("ADMIN") //需要相应的角色才能访问
//                .and()
//                .formLogin(); //基于Form表单登录验证
//                .loginPage("/login").failureUrl("/login-error"); //自定义登录界面
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
