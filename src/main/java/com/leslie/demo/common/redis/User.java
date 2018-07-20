package com.leslie.demo.common.redis;

import java.io.Serializable;

/**
 * Created by lichujun on 2018-05-24.
 */
public class User implements Serializable{
    private int id;
    private String name;

    public User(){

    }
    public User(int id, String name){
        super();
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
