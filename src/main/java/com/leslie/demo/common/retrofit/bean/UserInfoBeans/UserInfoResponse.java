package com.leslie.demo.common.retrofit.bean.UserInfoBeans;

/**
 * Created by lichujun on 2018-05-08.
 */
public class UserInfoResponse {
    public final String openid;
    public final String nickname;
    public final String sex;
    public final String province;
    public final String city;
    public final String country;
    public final String headimgurl;
    public final String unionid;

    public UserInfoResponse(String openid, String nickname
            , String sex, String province, String city
            , String country, String headimgurl
            , String unionid) {
        this.openid = openid;
        this.nickname = nickname;
        this.sex = sex;
        this.province = province;
        this.city = city;
        this.country = country;
        this.headimgurl = headimgurl;
        this.unionid = unionid;
    }
}
