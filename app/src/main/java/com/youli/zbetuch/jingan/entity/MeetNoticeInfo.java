package com.youli.zbetuch.jingan.entity;

/**
 * Created by ZHengBin on 2017/9/2.
 */

public class MeetNoticeInfo {

    private String theme;
    private String address;
    private String time;

    public MeetNoticeInfo(String theme, String address, String time) {
        this.theme = theme;
        this.address = address;
        this.time = time;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
