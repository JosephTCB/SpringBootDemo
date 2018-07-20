package com.leslie.demo.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lichujun on 2018-05-22.
 */
@Controller
public class WebController {
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
