package com.inspur.tax.common.retrofit.repository;

import com.inspur.tax.common.retrofit.bean.UserInfoBeans.UserInfoResponse;
import com.inspur.tax.common.retrofit.bean.UserInfoBeans.UserTokenResponse;
import com.inspur.tax.common.retrofit.service.UserInfoService;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by lichujun on 2018-05-08.
 */
@Component
public class UserInfoRepository {
    private final String grant_type = "authorization_code";
    private final String appid = "******";//替换成微信公众号的appid
    private final String secret = "******";//替换成微信公众号的secret
    private final String lang = "zh_CN";
    private Retrofit retrofit = new Retrofit.Builder()
            //设置baseUrl,注意baseUrl 应该以/ 结尾。
            .baseUrl("https://api.weixin.qq.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UserInfoService userInfoService = retrofit.create(UserInfoService.class);

    public UserTokenResponse getUserToken(String code) throws IOException {
        Call<UserTokenResponse> call = userInfoService.getUserToken(appid, secret, code, grant_type);
        return call.execute().body();
    }

    public UserInfoResponse getUserInfo(String code) throws IOException {
        Call<UserTokenResponse> call = userInfoService.getUserToken(appid, secret, code, grant_type);
        UserTokenResponse userTokenResponse = call.execute().body();
        Call<UserInfoResponse> infocall = userInfoService.getUserInfo(userTokenResponse.access_token
                , userTokenResponse.openid, lang);
        return infocall.execute().body();
    }
}
