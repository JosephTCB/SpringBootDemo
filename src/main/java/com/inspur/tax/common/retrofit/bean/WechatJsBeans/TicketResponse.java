package com.inspur.tax.common.retrofit.bean.WechatJsBeans;

/**
 * Created by lichujun on 2018-05-07.
 */
public class TicketResponse {
    public final int errcode;
    public final String errmsg;
    public final String ticket;
    public final String expires_in;

    public TicketResponse(int errcode, String errmsg, String ticket, String expires_in) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.ticket = ticket;
        this.expires_in = expires_in;
    }
}
