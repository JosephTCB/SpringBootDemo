package com.inspur.tax.common.retrofit.service;

import com.inspur.tax.common.retrofit.bean.UserInfoBeans.UserInfoResponse;
import com.inspur.tax.common.retrofit.bean.UserInfoBeans.UserTokenResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lichujun on 2018-05-08.
 */
public interface UserInfoService {
    @GET("sns/oauth2/access_token")
    Call<UserTokenResponse> getUserToken(@Query("appid") String appid,
                                         @Query("secret") String secret,
                                         @Query("code") String code,
                                         @Query("grant_type") String grant_type);

    @GET("sns/userinfo")
    Call<UserInfoResponse> getUserInfo(@Query("access_token") String access_token,
                                       @Query("openid") String openid,
                                       @Query("lang") String lang);
}
