package com.youli.zbetuch.jingan.utils;

import android.os.Environment;

import java.io.File;

/**
 * 作者: zhengbin on 2017/10/27.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public interface Const {

    String VIDEO_PATH = Environment.getExternalStorageDirectory()
            + File.separator + "WORKVIDEOS";
    String USB_PATH = "/mnt/usb";

}
