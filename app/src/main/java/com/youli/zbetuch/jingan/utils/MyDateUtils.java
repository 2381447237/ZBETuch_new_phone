package com.youli.zbetuch.jingan.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liutao on 2017/8/30.
 */

public class MyDateUtils {

    static SimpleDateFormat sdf;

    static Date date;

    //将字符串日期格式化 年月日时分秒
    public static String stringToYMDHMS(String myDate){

        sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(myDate==null){
            return null;
        }

        if(myDate.indexOf("T")==-1){
            return null;
        }
        myDate=myDate.replace("T"," ");

        try {
            date=sdf.parse(myDate);

            return sdf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    //将字符串日期格式化 年月日
    public static String stringToYMD(String myDate){

        if(myDate==null){
            return null;
        }

        sdf=new SimpleDateFormat("yyyy-MM-dd");

        if(myDate.indexOf("T")!=-1) {
            myDate = myDate.replace("T", " ");
        }
        try {
            date=sdf.parse(myDate);

            return sdf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    //将字符串日期格式化 时分秒
    public static String stringToHMS(String myDate){
        sdf=new SimpleDateFormat("HH:mm:ss");
        myDate=myDate.replace("T"," ");

        try {
            date=sdf.parse(myDate);

            return sdf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
