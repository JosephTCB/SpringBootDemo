package com.inspur.tax.common.retrofit.repository;

import com.inspur.tax.common.retrofit.bean.WechatJsBeans.TicketResponse;
import com.inspur.tax.common.retrofit.bean.WechatJsBeans.TokenResponse;
import com.inspur.tax.common.retrofit.service.WechatJsService;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.IOException;

/**
 * Created by lichujun on 2018-05-07.
 */
@Component
public class WechatJsRepository {
    private static String token = null;
    private static long tokentimestamp = 0;
    private static String ticket = null;
    private static long tickettimestamp = 0;
    private final String grant_type = "client_credential";
    private final String appid = "******";//替换成微信公众号的appid
    private final String secret = "******";//替换成微信公众号的secret
    Retrofit retrofit = new Retrofit.Builder()
            //设置baseUrl,注意baseUrl 应该以/ 结尾。
            .baseUrl("https://api.weixin.qq.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    WechatJsService wechatJsService = retrofit.create(WechatJsService.class);

    public String getToken() throws IOException {
        Call<TokenResponse> call = wechatJsService.getToken(grant_type, appid, secret);
        TokenResponse tokenResponse = call.execute().body();
        if (tokenResponse.errcode == 0) {
            token = tokenResponse.access_token;
            tokentimestamp = createTimestamp();
        }
        return token;
    }

    public String getTicket() throws IOException {
        Call<TicketResponse> call = wechatJsService.getTicket(token, "jsapi");
        TicketResponse ticketResponse = call.execute().body();
        if (ticketResponse.errcode == 0) {
            ticket = ticketResponse.ticket;
            tickettimestamp = createTimestamp();
        }
        return ticket;
    }

    public String getTokenInstance() throws IOException {
        if (token == null || tokentimestamp == 0 || (createTimestamp() - tokentimestamp > 3600)) {
            synchronized (WechatJsRepository.class) {
                if (token == null || tokentimestamp == 0 || (createTimestamp() - tokentimestamp > 3600)) {
                    token = getToken();
                }
            }
        }
        return token;
    }

    public String getTicketInstance() throws IOException {
        token = getTokenInstance();
        if (ticket == null || tickettimestamp == 0 || (createTimestamp() - tickettimestamp > 3600)) {
            synchronized (WechatJsRepository.class) {
                if (ticket == null || tickettimestamp == 0 || (createTimestamp() - tickettimestamp > 3600)) {
                    ticket = getTicket();
                }
            }
        }
        return ticket;
    }

    public String createNonceStr() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String nonceStr = "";
        for (int i = 0; i < 16; i++) {
            int beginIndex = (int) Math.round(Math.random() * 10);
            nonceStr += str.substring(beginIndex, beginIndex + 1);
        }
        return nonceStr;
    }

    public String createSignature(String nocestr, String ticket, long timestamp, String url) {
        // 这里参数的顺序要按照 key 值 ASCII 码升序排序
        StringBuffer sb = new StringBuffer();
        sb.append("jsapi_ticket=").append(ticket).append("&noncestr=").append(nocestr)
                .append("&timestamp=").append(timestamp).append("&url=").append(url);
        return DigestUtils.shaHex(sb.toString());
    }

    public long createTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
