package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/4.
 */
//近期热点
//http://web.youli.pw:89/Json/Get_News.aspx?page=0&rows=1
//[{"ID":5,"TITLE":"test11","DOC":"test11","CREATE_TIME":"2014-05-27T11:04:54.343","CREATE_STAFF":2,"RecordCount":5}]
public class NewsInfo {

    private int ID;
    private String TITLE;//显示这个
    private String DOC;
    private String CREATE_TIME;//显示这个
    private int CREATE_STAFF;
    private int RecordCount;

    public NewsInfo(String TITLE, String CREATE_TIME) {
        this.TITLE = TITLE;
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public int getCREATE_STAFF() {
        return CREATE_STAFF;
    }

    public void setCREATE_STAFF(int CREATE_STAFF) {
        this.CREATE_STAFF = CREATE_STAFF;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getDOC() {
        return DOC;
    }

    public void setDOC(String DOC) {
        this.DOC = DOC;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "CREATE_STAFF=" + CREATE_STAFF +
                ", ID=" + ID +
                ", TITLE='" + TITLE + '\'' +
                ", DOC='" + DOC + '\'' +
                ", CREATE_TIME='" + CREATE_TIME + '\'' +
                ", RecordCount=" + RecordCount +
                '}';
    }
}
