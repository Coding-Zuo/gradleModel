package com.zuo.model.modules.security.core.authentication.account;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
public class MyUserDetailService implements UserDetailsService , SocialUserDetailsService {

    private Logger logger= LoggerFactory.getLogger(getClass());

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        logger.info("表单登录用户名："+username);
//        //根据用户名查找用户信息
//        return new User(username,new BCryptPasswordEncoder().encode("admin"),
//                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
//    }
//
//    @Override
//    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
//        logger.info("社交用户登录id："+userId);
//        //根据用户名查找用户信息
//        //根据查找到的用户信息判断是否冻结
//        return new SocialUser(userId,new BCryptPasswordEncoder().encode("admin"),
//                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
//    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("表单登录用户名:" + username);
        return buildUser(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("设计登录用户Id:" + userId);
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode("admin");
        logger.info("数据库密码是:"+password);
        return new SocialUser(userId, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }

}
