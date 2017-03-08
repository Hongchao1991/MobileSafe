package com.zhang.mobiledemo.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Android_develop on 2017/3/7.
 */

public class UtilsNetwork {

    /**
     * 链接URL网络
     * @param localURL
     * @throws IOException
     */
    public static String connectNetWork(String localURL) throws IOException {
        URL url = new URL(localURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");//设置请求方式
        conn.setConnectTimeout(5000);//设置连接超时
        conn.setReadTimeout(5000);//设置响应超时，服务器不给响应
        conn.connect();//链接服务器
        int responseCode = conn.getResponseCode();//获取响应码
        if (responseCode==200){
            InputStream inputStream = conn.getInputStream();
            String result = readFromStream(inputStream);
            System.out.println("result:"+result);
            return result;
        }
        return null;

    }

    /**
     * 读取数据流
     * @param in
     * @return
     * @throws IOException
     */
    public static String readFromStream(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len=0;
        byte[] buffer = new byte[1024];
        while ((len=in.read(buffer))!=-1){
            out.write(buffer,0,len);
        }
        String result = out.toString();
        in.close();
        out.close();
        return result;
    }
}
