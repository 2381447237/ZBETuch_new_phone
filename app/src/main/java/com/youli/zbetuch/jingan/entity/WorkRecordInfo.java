package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/9.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class WorkRecordInfo {

    private String noStr;
    private String titleStr;
    private String dateStr;

    public WorkRecordInfo(String dateStr, String noStr, String titleStr) {
        this.dateStr = dateStr;
        this.noStr = noStr;
        this.titleStr = titleStr;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getNoStr() {
        return noStr;
    }

    public void setNoStr(String noStr) {
        this.noStr = noStr;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }
}
