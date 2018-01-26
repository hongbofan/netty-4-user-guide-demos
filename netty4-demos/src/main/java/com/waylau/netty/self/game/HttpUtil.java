package com.waylau.netty.self.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2018/1/26.
 */
public class HttpUtil {
    /**
     * @param url:请求url
     * @param content: 请求体(参数)
     * @return errorStr:错误信息;status:状态码，response：返回数据
     */
    public static Map<String, Object> post(String url, String content) {
        Map<String, Object> result = new HashMap<>();
        String errorStr = "";
        String status = "";
        String response = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
            // 设置请求属性
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");
            httpUrlConnection.setRequestProperty("x-adviewrtb-version", "2.1");
            // 发送POST请求必须设置如下两行
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(httpUrlConnection.getOutputStream());
            // 发送请求参数
            out.write(content);
            // flush输出流的缓冲
            out.flush();
            httpUrlConnection.connect();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                response += line;
            }
            status = Integer.toString(httpUrlConnection.getResponseCode());
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            errorStr = e.getMessage();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) { out.close();}
                if (in != null) {in.close();}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        result.put("errorStr", errorStr);
        result.put("response", response);
        result.put("status", status);
        return result;

    }
}
