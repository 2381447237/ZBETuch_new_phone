package com.youli.zbetuch.jingan.entity;

/**
 * Created by ZHengBin on 2017/8/3.
 */

public class GetStaffInfo {

   // {"ID":2,"NAME":"admin","INPUT_CODE":"admin","PWD":"WnllRjkwRXZyYW89","PHONE":"",
    // "EMAIL":"","PHOTO":null,"CREATE_DATE":"2013-04-26T13:26:00.527","CREATE_STAFF":1,
    // "UPDATE_DATE":"2013-04-26T13:26:00.527","UPDATE_STAFF":1,"STOP":false,
    // "DEVICE_NUMBER":"","SFZ":"","DEPT":"","RecordCount":0,"Enable":true,"Line_name":null}

    //http://web.youli.pw:89/Json/Get_Staff.aspx

    private int ID;
    private String NAME;
    private String INPUT_CODE;
    private String PWD;
    private String PHONE;
    private String EMAIL;
    private String PHOTO;
    private String CREATE_DATE;
    private int CREATE_STAFF;
    private String UPDATE_DATE;
    private int UPDATE_STAFF;
    private boolean STOP;
    private String DEVICE_NUMBER;
    private String SFZ;
    private String DEPT;
    private int RecordCount;
    private boolean Enable;
    private String Line_name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLine_name() {
        return Line_name;
    }

    public void setLine_name(String line_name) {
        Line_name = line_name;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getINPUT_CODE() {
        return INPUT_CODE;
    }

    public void setINPUT_CODE(String INPUT_CODE) {
        this.INPUT_CODE = INPUT_CODE;
    }

    public String getPWD() {
        return PWD;
    }

    public void setPWD(String PWD) {
        this.PWD = PWD;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(String PHOTO) {
        this.PHOTO = PHOTO;
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

    public boolean isSTOP() {
        return STOP;
    }

    public void setSTOP(boolean STOP) {
        this.STOP = STOP;
    }

    public String getDEVICE_NUMBER() {
        return DEVICE_NUMBER;
    }

    public void setDEVICE_NUMBER(String DEVICE_NUMBER) {
        this.DEVICE_NUMBER = DEVICE_NUMBER;
    }

    public String getSFZ() {
        return SFZ;
    }

    public void setSFZ(String SFZ) {
        this.SFZ = SFZ;
    }

    public String getDEPT() {
        return DEPT;
    }

    public void setDEPT(String DEPT) {
        this.DEPT = DEPT;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public boolean isEnable() {
        return Enable;
    }

    public void setEnable(boolean enable) {
        Enable = enable;
    }

    @Override
    public String toString() {
        return "GetStaffInfo{" +
                "CREATE_DATE='" + CREATE_DATE + '\'' +
                ", ID=" + ID +
                ", NAME='" + NAME + '\'' +
                ", INPUT_CODE='" + INPUT_CODE + '\'' +
                ", PWD='" + PWD + '\'' +
                ", PHONE='" + PHONE + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", PHOTO='" + PHOTO + '\'' +
                ", CREATE_STAFF=" + CREATE_STAFF +
                ", UPDATE_DATE='" + UPDATE_DATE + '\'' +
                ", UPDATE_STAFF=" + UPDATE_STAFF +
                ", STOP=" + STOP +
                ", DEVICE_NUMBER='" + DEVICE_NUMBER + '\'' +
                ", SFZ='" + SFZ + '\'' +
                ", DEPT='" + DEPT + '\'' +
                ", RecordCount=" + RecordCount +
                ", Enable=" + Enable +
                ", Line_name='" + Line_name + '\'' +
                '}';
    }
}
