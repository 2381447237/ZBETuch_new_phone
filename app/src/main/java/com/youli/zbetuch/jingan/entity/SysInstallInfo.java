package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/16.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/Get_TB_Staff_Pad_File.aspx?staff=2&page=0&rows=15
 *
 * [{"ID":20,"STAFF":2,"CREATE_DATE":"2017-02-15T11:58:19.393","NAME":"闸北公安","TYPE":"程序","RecordCount":44}]
 */

public class SysInstallInfo {

    private int ID;
    private int STAFF;
    private String CREATE_DATE;
    private String NAME;
    private String TYPE;
    private int RecordCount;

    public SysInstallInfo(String NAME, String CREATE_DATE, String TYPE) {
        this.NAME = NAME;
        this.CREATE_DATE = CREATE_DATE;
        this.TYPE = TYPE;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
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

    public int getSTAFF() {
        return STAFF;
    }

    public void setSTAFF(int STAFF) {
        this.STAFF = STAFF;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
