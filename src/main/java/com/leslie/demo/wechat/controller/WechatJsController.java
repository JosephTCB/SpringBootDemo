package com.leslie.demo.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leslie.demo.common.retrofit.repository.WechatJsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by lichujun on 2018-05-07.
 */
@RestController
public class WechatJsController {
    private WechatJsRepository wechatJsRepository;

    @RequestMapping("getToken")
    public String getToken() throws IOException {
        String token = wechatJsRepository.getTokenInstance();
        return token;
    }

    @RequestMapping("getTicket")
    public String getTicket() throws IOException {
        String ticket = wechatJsRepository.getTicketInstance();
        return ticket;
    }

    @Autowired
    public WechatJsController(WechatJsRepository wechatJsRepository) {
        this.wechatJsRepository = wechatJsRepository;
    }

    @RequestMapping("getSignature")
    public JSONObject getSignature(HttpServletRequest request) throws IOException {
        String url = request.getParameter("url");
        String nonceStr = wechatJsRepository.createNonceStr();
        long timestamp = wechatJsRepository.createTimestamp();
        String ticket = wechatJsRepository.getTicketInstance();
        String signature = wechatJsRepository.createSignature(nonceStr, ticket, timestamp, url);
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"nonceStr\":\"").append(nonceStr).append("\",")
                .append("\"timestamp\":").append(timestamp).append(",")
                .append("\"signature\":\"").append(signature).append("\"}");
        return JSON.parseObject(sb.toString());
    }
}
