package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/9/13.
 */

//http://web.youli.pw:89/Json/Get_TB_Staff_Mark_Type.aspx
//[{"ID":1,"NAME":"丧劳调查","CREATE_STAFF":2,"CREATE_DATE":"2014-09-11T10:23:07.477","RecordCount":0}]
public class WhMarkInfo {

    private int ID;
    private String NAME;
    private int CREATE_STAFF;
    private String CREATE_DATE;
    private int RecordCount;

    public WhMarkInfo(String NAME) {
        this.NAME = NAME;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public int getCREATE_STAFF() {
        return CREATE_STAFF;
    }

    public void setCREATE_STAFF(int CREATE_STAFF) {
        this.CREATE_STAFF = CREATE_STAFF;
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
}
