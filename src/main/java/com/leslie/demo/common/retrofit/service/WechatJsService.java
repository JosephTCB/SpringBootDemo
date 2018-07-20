package com.leslie.demo.common.retrofit.service;

import com.leslie.demo.common.retrofit.bean.WechatJsBeans.TicketResponse;
import com.leslie.demo.common.retrofit.bean.WechatJsBeans.TokenResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lichujun on 2018-05-07.
 */
public interface WechatJsService {
    @GET("cgi-bin/token")
    Call<TokenResponse> getToken(@Query("grant_type") String grant_type,
                                 @Query("appid") String appid,
                                 @Query("secret") String secret);

    @GET("cgi-bin/ticket/getticket")
    Call<TicketResponse> getTicket(@Query("access_token") String access_token,
                                   @Query("type") String type);
}
