package com.youli.zbetuch.jingan.entity;

/**
 * Created by ZHengBin on 2017/8/21.
 */

//http://web.youli.pw:89/Json/Get_TB_Staff_Marks.aspx?sfz=310108198004026642
//[{"ID":20,"SFZ":"310108198004026642","TYPE":1,"CREATE_DATE":"2017-08-21T13:52:49.373","CREATE_STAFF":2,
// "UPDATE_DATE":"2017-08-21T13:52:49.373","UPDATE_STAFF":2,"RecordCount":0,"Type_Name":"丧劳调查"}]
public class StaffMarkInfo {

    private int ID;
    private String SFZ;
    private int TYPE;
    private String CREATE_DATE;
    private int CREATE_STAFF;
    private String UPDATE_DATE;
    private int UPDATE_STAFF;
    private int RecordCount;
    private String Type_Name;//显示这个

    public StaffMarkInfo(String type_Name, String CREATE_DATE) {
        Type_Name = type_Name;
        this.CREATE_DATE = CREATE_DATE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSFZ() {
        return SFZ;
    }

    public void setSFZ(String SFZ) {
        this.SFZ = SFZ;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
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

    public String getUPDATE_DATE() {
        return UPDATE_DATE;
    }

    public void setUPDATE_DATE(String UPDATE_DATE) {
        this.UPDATE_DATE = UPDATE_DATE;
    }

    public int getUPDATE_STAFF() {
        return UPDATE_STAFF;
    }

    public void setUPDATE_STAFF(int UPDATE_STAFF) {
        this.UPDATE_STAFF = UPDATE_STAFF;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public String getType_Name() {
        return Type_Name;
    }

    public void setType_Name(String type_Name) {
        Type_Name = type_Name;
    }
}
