package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/30.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class PostMegInfo {

    private String type;
    private String title;
    private String id;
    private String meetTime;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMeetTime() {
        return meetTime;
    }
    public void setMeetTime(String meetTime) {
        this.meetTime = meetTime;
    }

    public PostMegInfo(String type, String title, String id, String meetTime) {
        super();
        this.type = type;
        this.title = title;
        this.id = id;
        this.meetTime = meetTime;
    }
    public PostMegInfo() {
        super();
    }

    @Override
    public String toString() {
        return "PostMegInfo [type=" + type + ", title=" + title + ", id=" + id +
                ", meetTime=" + meetTime + "]";
    }

}
