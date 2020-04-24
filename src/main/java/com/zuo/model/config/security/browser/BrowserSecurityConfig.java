package com.zuo.model.config.security.browser;

import com.zuo.model.config.security.core.AbstractChannelSecurityConfig;
import com.zuo.model.config.security.core.SecurityConstants;
import com.zuo.model.config.security.core.SecurityProperties;
import com.zuo.model.config.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.zuo.model.config.security.validate.SmsCodeFilter;
import com.zuo.model.config.security.validate.ValidateCodeFilter;
import com.zuo.model.config.security.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);//启动自动创建记住我的数据库表
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        ValidateCodeFilter validateCodeFilter=new ValidateCodeFilter();
//        validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
//        validateCodeFilter.setSecurityProperties(securityProperties);
//        validateCodeFilter.afterPropertiesSet();
//
//        SmsCodeFilter smsCodeFilter=new SmsCodeFilter();
//        smsCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
//        smsCodeFilter.setSecurityProperties(securityProperties);
//        smsCodeFilter.afterPropertiesSet();

        applyPasswordAuthenticationConfig(http);

        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                    .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                        "/user/regist","/static/**","/css/**", "/js/**", "/fonts/**",
                            securityProperties.getBrowser().getLoginPage(),"/code/*","/swagger-ui.html/*")
                .permitAll()
                .anyRequest()
                    .authenticated()
                    .and()
                .csrf().disable();
//        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin()
//                    .loginPage(securityProperties.getBrowser().getLoginPage())
//    //                .failureUrl("/login-error") //自定义登录界面
//                    .loginProcessingUrl("/loginProcess")
//                    .successHandler(myAuthenticationSuccessHandler)
//                    .failureHandler(myAuthenticationFailureHandler)
//                .and()
//                .rememberMe()
//                    .tokenRepository(persistentTokenRepository())
//                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
//                    .userDetailsService(userDetailsService)
//                    .and()
//                .authorizeRequests()
//                    .antMatchers("/static/**","/css/**", "/js/**", "/fonts/**",
//                            securityProperties.getBrowser().getLoginPage(),"/code/*","/swagger-ui.html/*").permitAll()
//                    .anyRequest()
//                    .authenticated()
//                    .and()
//                .csrf().disable()
//                .apply(smsCodeAuthenticationSecurityConfig);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
