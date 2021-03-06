package com.zuo.model.modules.backend;

import com.zuo.model.utils.common.mvc.AbstractController;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/user")
public class UserController extends AbstractController {

    @RequestMapping(value = "/me",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object getCurrentUser(Authentication authentication){
        return authentication;
    }

    @RequestMapping(value = "/me/detail",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object detail(@AuthenticationPrincipal UserDetails user){
        return user;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String query1() {
        return "index";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public String query() {
        return "index";
    }

}
