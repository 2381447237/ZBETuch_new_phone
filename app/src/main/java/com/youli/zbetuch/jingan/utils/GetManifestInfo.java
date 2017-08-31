package com.youli.zbetuch.jingan.utils;

import android.content.Context;
import android.content.pm.PackageManager;

public class GetManifestInfo {
    /**
     * 获取Manifest下的VersionName
     * @param context
     * @return String versionName
     */
    public static String getVersionName(Context context){
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return versionName;
    }

    /**
     * 获取VersionCode
     * @param context
     * @return int versionCode
     */
    public static int getVersionCode(Context context){
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
        return versionCode;
    }
}
