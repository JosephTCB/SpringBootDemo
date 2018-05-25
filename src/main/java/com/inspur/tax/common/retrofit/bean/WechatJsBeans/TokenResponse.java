package com.inspur.tax.common.retrofit.bean.WechatJsBeans;

/**
 * Created by lichujun on 2018-05-07.
 */
public class TokenResponse {
    public final String access_token;
    public final int expires_in;
    public final int errcode;
    public final String errmsg;

    public TokenResponse(String access_token, int expires_in, int errcode, String errmsg) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.errcode = errcode;
        this.errmsg = errmsg;
    }
}
