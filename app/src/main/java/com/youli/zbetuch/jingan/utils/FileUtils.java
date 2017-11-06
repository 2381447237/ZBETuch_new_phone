package com.youli.zbetuch.jingan.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.youli.zbetuch.jingan.naire.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by liutao on 2017/9/1.
 */

public class FileUtils {

    public static Intent openFile(String filePath) {

        File file = new File(filePath);

        if ((file == null) || !file.exists() || file.isDirectory())
            return null;

		/* 取得扩展名 */
        String end = file
                .getName()
                .substring(file.getName().lastIndexOf(".") + 1,
                        file.getName().length()).toLowerCase();
		/* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
                || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {

            return getAudioFileIntent(filePath);
        } else if (end.equals("3gp") || end.equals("mp4")) {

            return getAudioFileIntent(filePath);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp")) {

            return getImageFileIntent(filePath);
        } else if (end.equals("apk")) {

            return getApkFileIntent(filePath);
        } else if (end.equals("ppt")) {

            return getPptFileIntent(filePath);
        } else if (end.equals("xls")) {

            return getExcelFileIntent(filePath);
        } else if (end.equals("doc")) {

            return getWordFileIntent(filePath);

        } else if (end.equals("pdf")) {

            return getPdfFileIntent(filePath);
        } else if (end.equals("chm")) {

            return getChmFileIntent(filePath);
        } else if (end.equals("txt")) {

            return getTextFileIntent(filePath, false);
        } else {

            return getAllIntent(end,filePath);
        }
    }

    // Android获取一个用于打开APK文件的intent
    public static Intent getAllIntent(String end,String param) {

        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.N) {//小于安卓7.0
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "*/*");
        }else {//大于安卓7.0
            Uri uri = FileProvider.getUriForFile(MyApplication.getContext(), "com.youli.zbetuch.jingan.provider", new File(param));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);    //这一步很重要。给目标应用一个临时的授权。

            if(end.equals("xlsx")) {
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            }else{
                intent.setDataAndType(uri, "*/*");
            }
        }
        return intent;
    }

    // Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    // Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    // Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    // Android获取一个用于打开Html文件的intent
    public static Intent getHtmlFileIntent(String param) {

        Uri uri = Uri.parse(param).buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    // Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.N){//小于7.0查看图片
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "image/*");
        }else{//大于等于7.0查看图片
            Uri photoURI = FileProvider.getUriForFile(MyApplication.getContext(), "com.youli.zbetuch.jingan.provider", new File(param));
            intent.setDataAndType(photoURI, "image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);    //这一步很重要。给目标应用一个临时的授权。
        }

        return intent;
    }

    // Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    // Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    // Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    // Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    // Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    // Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }


    /**
     * 查询设备上所有非系统apk
     *
     * @param mContext
     * @return
     */
    public static String showAllApks(Context mContext) {
        String value = "";
        try {
            PackageManager packageManager = mContext.getPackageManager();
            List<PackageInfo> packageInfoList = packageManager
                    .getInstalledPackages(0);
            for (PackageInfo info : packageInfoList) {
                // 判断如果不是系统apk
                if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                    // System.out.println(info.applicationInfo.packageName
                    // + "===>"
                    // + packageManager
                    // .getApplicationLabel(info.applicationInfo));
                    value += packageManager
                            .getApplicationLabel(info.applicationInfo) + ",";
                }

                // 获得应用的图标
                // packageManager.getApplicationIcon(applicationInfo)
            }
            if (value.trim().length() > 1) {
                value = value.substring(0, value.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    public static String getMovies(Context mContext) {
        String value = "";
        try {
            ContentResolver contentResolver = mContext.getContentResolver();
            String[] projection = new String[] { MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.DATA };
            Cursor cursor = contentResolver.query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
                    null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
            cursor.moveToFirst();
            int fileNum = cursor.getCount();

            for (int counter = 0; counter < fileNum; counter++) {
                // Log.e("=============", "-------------file is: " +
                // cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                // );
                // Log.e("=============", "-------------path is: " +
                // cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
                // Log.e("=============", "-------------display is: " +
                // cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)));
                if (!cursor.getString(
                        cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                        .contains(Const.USB_PATH)) {
                    value += cursor
                            .getString(cursor
                                    .getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
                            + ",";
                }
                cursor.moveToNext();
            }
            cursor.close();
            if (value.trim().length() > 1) {
                value = value.substring(0, value.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String sendApps(Map<String, String> data) {
        String jsonString = parseToJson(data.get("apps"), data.get("films"));
        String url = "/Json/Set_TB_Staff_Pad_File.aspx";
        String valueString = HttpUtil.postJson(url, jsonString);
        return valueString;
    }

    public static String parseToJson(String apps, String films) {
        String[] appStrings = apps.split(",");
        System.out.println(appStrings.length);
        String[] filmsStrings = films.split(",");
        System.out.println(filmsStrings.length);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        try {
            for (int i = 0; i < appStrings.length; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("NAME", appStrings[i]);
                jsonObject.put("TYPE", "程序");
                jsonArray.put(jsonObject);
            }
            for (int i = 0; i < filmsStrings.length; i++) {
                jsonObject = new JSONObject();
                jsonObject.put("NAME", filmsStrings[i]);
                jsonObject.put("TYPE", "视频");
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonArray.toString();
    }

    public static File getSaveFile(Context context) {
        return new File(context.getFilesDir(), "pic.jpg");
    }

}
