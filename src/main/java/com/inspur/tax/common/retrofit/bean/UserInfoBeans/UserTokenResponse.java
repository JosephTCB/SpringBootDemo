package com.inspur.tax.common.retrofit.bean.UserInfoBeans;

/**
 * Created by lichujun on 2018-05-08.
 */
public class UserTokenResponse {
    public final String access_token;
    public final int expires_in;
    public final String refresh_token;
    public final String openid;
    public final int errcode;
    public final String scope;

    public UserTokenResponse(String access_token, int expires_in, String refresh_token, String openid, int errcode, String scope) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.openid = openid;
        this.errcode = errcode;
        this.scope = scope;
    }
}
