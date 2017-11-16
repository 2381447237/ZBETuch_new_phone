package com.youli.zbetuch.jingan.utils;

import android.content.Context;
import android.location.LocationManager;
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

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

}
