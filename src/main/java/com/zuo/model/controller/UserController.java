package com.zuo.model.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/user1", method = RequestMethod.GET)
    @ResponseBody
    public String query() {
        return "123";
    }

}
