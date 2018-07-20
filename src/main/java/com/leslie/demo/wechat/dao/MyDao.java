package com.leslie.demo.wechat.dao;

import com.leslie.demo.common.db.MySqlSessionTemplate;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by lichujun on 2018-05-22.
 */
@Repository
public class MyDao {
    private final SqlSessionTemplate sqlSession;
    @Autowired
    public MyDao(MySqlSessionTemplate sqlSession){
        this.sqlSession = sqlSession.getSqlSessionTemplate();
    }
    public void add(Map<String,Object> map){
        sqlSession.insert("My.add",map);
    }
}
