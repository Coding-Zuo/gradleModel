package com.zuo.model.controller;


import com.zuo.model.utils.common.mvc.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends AbstractController {

    @RequestMapping(value={"/","index"})
    public String Index() {
        return "index";
    }

}
