package com.youli.zbetuch.jingan.utils;


import android.util.Base64;
import android.util.Log;

import org.json.JSONTokener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyOkHttpUtils {

    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public static final String BaseUrl="http://web.youli.pw:89";
    private static final String TAG = "asdasdasd";
    static OkHttpClient okHttpClient = null;

    //懒汉
    private static synchronized OkHttpClient getInstance() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }

    /**
     * OKHttp 同步 Get
     *
     * @param url 请求网址
     * @return 获取到数据返回Response，若未获取到数据返回null
     */
    public static Response okHttpLogin(String url) {
        getInstance();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    /**
     * @return
     * 根据 cookie 请求数据或提交数据
     *
     * @param url
     */
    public static Response okHttpGet(String url) {
        getInstance();
        String cookies = SharedPreferencesUtils.getString("cookies");
        Log.d(TAG, "okHttpGet: "+ cookies);
        Request request;
        if (!cookies.isEmpty()) {
            request = new Request.Builder().addHeader("cookie", cookies).url(url).build();
        } else {
            request = new Request.Builder().url(url).build();
        }
        try {
            return okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 阻塞 Post
     *
     * @param url      url
     * @param userName 用户名
     * @param psd      密码
     * @return Response
     */
    public Response okHttpPost(String url, String userName, String psd) {
        getInstance();
        RequestBody requestBody = new FormBody.Builder()
                .add("username", userName)
                .add("password", psd)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    //失业无业的调查提交
    public static Response okHttpPost(String url,String ID,String DQYX,String MQZK,String DATE,String MARK){
        getInstance();
        String cookies = SharedPreferencesUtils.getString("cookies");
        RequestBody requestBody=new FormBody.Builder()
                .add("ID",ID).add("NEW_DQYX",DQYX).add("NEW_MQZK",MQZK)
                .add("SURVEY_DATE",DATE).add("MARK",MARK)
                .build();
        Request request=new Request.Builder().url(url)
                .post(requestBody).addHeader("cookie",cookies).build();
        Response response;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    return  response;
    }

    //服务记录的新增（ServiceReFramgent）
//    http://web.youli.pw:89/Json/Set_Sfz_Service.aspx?ID=0&SFZ=310108198004026642
//            &STAFF=2&SERVICE_TIME=2017-8-18&TYPE=1&MARK=测试
    public static Response okHttpPost(String url,String ID,String SFZ,String STAFF,String SERVICE_TIME,String TYPE,String MARK){

        getInstance();
        String cookies= SharedPreferencesUtils.getString("cookies");
        RequestBody requestBody=new FormBody.Builder().add("ID",ID)
                .add("SFZ",SFZ).add("STAFF",STAFF).add("SERVICE_TIME",SERVICE_TIME)
                .add("TYPE",TYPE).add("MARK",MARK).build();

        Request request=new Request.Builder().url(url)
        .post(requestBody).addHeader("cookie",cookies).build();
        Response response;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return response;
    }

//服务记录的删除
    //http://web.youli.pw:89/Json/Set_Sfz_Service.aspx?ID=52&del=true

    public static Response okHttpPostDelServiceRe(String url,String ID,String del){

        getInstance();
        String cookies=SharedPreferencesUtils.getString("cookies");
        RequestBody requestBody=new FormBody.Builder().add("ID",ID)
                .add("del",del).build();

        Request request=new Request.Builder().url(url).post(requestBody)
                .addHeader("cookie",cookies).build();

        Response response;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        return response;
    }

    //教育信息的新建或修改
//http://web.youli.pw:89/Json/Set_Educational_Information.aspx?ID=0&SFZ=310108198004026642&SCHOOL=我的学校&EDUCATION=我的学历&SPECIALTY=我的专业&START_DATE=2017-09-04&END_DATE=2017-09-04
    public  static Response okHttpPostNewEduInfo(String url,String id,String sfz,String school,String education,String specialty,String startTime,String endTime){

        getInstance();
        String cookies=SharedPreferencesUtils.getString("cookies");
        RequestBody requestBody=new FormBody.Builder().add("ID",id)
                .add("SFZ",sfz).add("SCHOOL",school).add("EDUCATION",education)
                .add("SPECIALTY",specialty).add("START_DATE",startTime).add("END_DATE",endTime).build();

        Log.e("2017/9/5","requestBody=="+requestBody);

        Request request=new Request.Builder().url(url).post(requestBody)
                .addHeader("cookie",cookies).build();

        Response response;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
return response;
    }

    //教育信息的删除,新建，修改
    public  static Response okHttpPostEduInfo(String url,String json){

        getInstance();
        String cookies=SharedPreferencesUtils.getString("cookies");
        RequestBody requestBody=new FormBody.Builder().add("json",json).build();

        Log.e("2017/9/5","requestBody=="+requestBody);

        Request request=new Request.Builder().url(url).post(requestBody)
                .addHeader("cookie",cookies).build();

        Response response;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }


    //异步Get
    public static void okHttpAsynGet(String url, Callback callback) {
        getInstance();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;

        okHttpClient.newCall(request).enqueue(callback);

    }

    //异步 Post
    public static void okHttpAsyncPost(String url, String userName, String psd, Callback callback) {
        getInstance();
        RequestBody requestBody = new FormBody.Builder()
                .add("username", userName)
                .add("password", psd)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //提交个人简历信息上传
    public static Response okHttpPersonRePost(String url,String data){

        getInstance();
        String cookies=SharedPreferencesUtils.getString("cookies");
        RequestBody requestBody = RequestBody.create(JSON, data);

                Request request=new Request.Builder().url(url).post(requestBody)
                .addHeader("cookie",cookies).build();

        Response response;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;

    }

    //提交专项标识的上传
    public static Response okHttpZxMarkPost(String url,byte [] data){

        getInstance();
        String cookies=SharedPreferencesUtils.getString("cookies");
        String str = Base64.encodeToString(data, Base64.DEFAULT);//这句话不能少
        RequestBody requestBody=RequestBody.create(null,str.getBytes());
        Request request=new Request.Builder().url(url).post(requestBody)
                .addHeader("cookie",cookies).build();

        Response response;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        return response;

    }

    //维护标识的添加
    public static Response okHttpWhMarkAddPost(String url,String name){

        getInstance();
        String cookies=SharedPreferencesUtils.getString("cookies");


        RequestBody requestBody=new FormBody.Builder().add("name",name).build();

        Request request=new Request.Builder().url(url).post(requestBody)
                .addHeader("cookie",cookies).build();

        Response response;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;


    }

    //维护标识的删除
    public static Response okHttpWhMarkDelPost(String url,String id){

        getInstance();
        String cookies=SharedPreferencesUtils.getString("cookies");

        RequestBody requestBody=new FormBody.Builder().add("id",id).add("del","true").build();

        Request request=new Request.Builder().url(url).post(requestBody).addHeader("cookie",cookies).build();

        Response response;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return response;
    }

    //维护标识的修改
    public static Response okHttpWhMarkModifyPost(String url,String id,String name){

        getInstance();
        String cookies=SharedPreferencesUtils.getString("cookies");

        RequestBody requestBody=new FormBody.Builder().add("id",id).add("name",name).build();

        Request request=new Request.Builder().url(url).post(requestBody).addHeader("cookie",cookies).build();

        Response response;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return response;
    }


}
