package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/15.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/Get_Staff_Log.aspx?rows=15&page=0
 *
 *
 * [{"ID":195,"SFZ":"310108198004026642","DETAIL":"删除备注应届毕业生,",
 * "CREATE_TIME":"2017-09-15T13:06:22.103","STAFF":2,"GPS":"","RecordCount":161,"Staff_Name":null}]
 */

public class OpRecordInfo {

    private int ID;
    private String SFZ;
    private String DETAIL;
    private String CREATE_TIME;
    private int STAFF;
    private  String GPS;
    private int RecordCount;
    private String Staff_Name;

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getDETAIL() {
        return DETAIL;
    }

    public void setDETAIL(String DETAIL) {
        this.DETAIL = DETAIL;
    }

    public String getGPS() {
        return GPS;
    }

    public void setGPS(String GPS) {
        this.GPS = GPS;
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

    public String getStaff_Name() {
        return Staff_Name;
    }

    public void setStaff_Name(String staff_Name) {
        Staff_Name = staff_Name;
    }
}
