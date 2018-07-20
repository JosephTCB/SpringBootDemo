package com.leslie.demo.wechat.controller;

import com.leslie.demo.wechat.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lichujun on 2018-05-18.
 */
@RestController
public class ServiceController {
    private final MyService myService;

    @Autowired
    public ServiceController(MyService myService){
        this.myService = myService;
    }

    @RequestMapping("/add")
    public String add(){
        Map<String, Object> map = new HashMap();
        String uuid = UUID.randomUUID().toString();
        map.put("username","Joseph");
        map.put("age",22);
        map.put("id",uuid.substring(0,16));
        myService.add(map);
        return "hello";
    }
}
