package com.leslie.demo.wechat.service;

import com.leslie.demo.wechat.dao.MyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lichujun on 2018-05-22.
 */
@Service
public class MyService {
    private final MyDao myDao;
    @Autowired
    public MyService(MyDao myDao){
        this.myDao = myDao;
    }
    public void add(Map<String, Object> map){
        myDao.add(map);
    }
}
