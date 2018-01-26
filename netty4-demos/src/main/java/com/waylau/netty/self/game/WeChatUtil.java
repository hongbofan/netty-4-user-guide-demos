package com.waylau.netty.self.game;

import java.util.Map;

import static com.waylau.netty.self.game.HttpUtil.post;

/**
 * Created by DELL on 2018/1/26.
 */
public class WeChatUtil {
    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String APPID = "wx71feb7b92a22f730";
    public static final String SECRET = "081585e843729b741ad992812a6f2c23";
    public static final String AUTHORIZATION_CODE = "authorization_code";

    public static Map<String,String> auth(String code) {
        String url = GET_ACCESS_TOKEN_URL + "?" + "appid=" + APPID + "&secret=" + SECRET + "&code=" + code + "&grant_type=" + AUTHORIZATION_CODE;
        Map<String,Object> map = HttpUtil.post(url,"");
        return null;
    }

    public static void main(String[] args) {
        WeChatUtil.auth("013laPnf031MNB1NI5lf0b3aof0laPnm");
    }
}
