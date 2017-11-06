package com.youli.zbetuch.jingan.service;

import com.youli.zbetuch.jingan.naire.HttpUtil;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.Map;

/**
 * 作者: zhengbin on 2017/10/30.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class MainDao {

    /**
     * 全局获取推送信息
     * @param
     */
    public String postMainNum(Map<String, String> data){

//		String url = "/comet_broadcast.asyn";
        String url = "/Msg.GetMsg.bspx";

        try {
            String value = HttpUtil.postLongRequest(url, data);
            return value;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

}
