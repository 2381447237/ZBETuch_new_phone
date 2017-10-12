package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/9/28.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/Get_Attention.aspx?page=0&rows=15
 *
 * [{"ID":51,"STAFF":2,"SFZ":"310108198607242635","CREATE_TIME":"2017-05-03T14:35:29.737",
 * "NAME":"陶林峰","RecordCount":10,"Delete1":false}]
 */

public class FollowListInfo {

    private int ID;
    private int STAFF;
    private String SFZ;
    private String CREATE_TIME;
    private String NAME;
    private int RecordCount;
    private boolean Delete1;

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public boolean isDelete1() {
        return Delete1;
    }

    public void setDelete1(boolean delete1) {
        Delete1 = delete1;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public String getSFZ() {
        return SFZ;
    }

    public void setSFZ(String SFZ) {
        this.SFZ = SFZ;
    }

    public int getSTAFF() {
        return STAFF;
    }

    public void setSTAFF(int STAFF) {
        this.STAFF = STAFF;
    }
}
