package com.leslie.demo.wechat.controller;

import com.leslie.demo.common.retrofit.bean.UserInfoBeans.UserInfoResponse;
import com.leslie.demo.common.retrofit.bean.UserInfoBeans.UserTokenResponse;
import com.leslie.demo.common.retrofit.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by lichujun on 2018-05-08.
 */
@RestController
public class UserInfoController {
    private UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoController(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @RequestMapping("/getUserToken")
    public UserTokenResponse getUserToken(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
        return userInfoRepository.getUserToken(code);
    }

    @RequestMapping("/getUserInfo")
    public UserInfoResponse getUserInfo(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
        return userInfoRepository.getUserInfo(code);
    }
}
