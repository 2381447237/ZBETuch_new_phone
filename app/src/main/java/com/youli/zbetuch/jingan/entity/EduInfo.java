package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/15.
 */
//http://web.youli.pw:89/Json/Get_Educational_Information.aspx?sfz=310108198004026642
public class EduInfo {
    //[{"ID":8,"SFZ":"310108198004026642","SCHOOL":"测试学校1","EDUCATION":"测试学历1","SPECIALTY":"测试专业1","START_DATE":"2017-08-15T00:00:00",
    // "END_DATE":"2017-08-17T00:00:00","CREATE_DATE":"2017-08-17T16:53:14.747","UPDATE_DATE":"2017-08-17T16:53:14.747","CREATE_STAFF":2,"UPDATE_STAFF":2,
    // "RecordCount":0,"Type":0}]
    private int ID;
    private String SFZ;
    private String SCHOOL;//要显示的 学校
    private String EDUCATION;//要显示的 学历
    private String SPECIALTY;//专业
    private String START_DATE;//要显示的
    private String END_DATE;//要显示的
    private String CREATE_DATE;
    private String UPDATE_DATE;
    private int CREATE_STAFF;
    private int UPDATE_STAFF;
    private int RecordCount;
    private int Type;

    public int getUPDATE_STAFF() {
        return UPDATE_STAFF;
    }

    public void setUPDATE_STAFF(int UPDATE_STAFF) {
        this.UPDATE_STAFF = UPDATE_STAFF;
    }

    public int getCREATE_STAFF() {
        return CREATE_STAFF;
    }

    public void setCREATE_STAFF(int CREATE_STAFF) {
        this.CREATE_STAFF = CREATE_STAFF;
    }

    public String getEDUCATION() {
        return EDUCATION;
    }

    public void setEDUCATION(String EDUCATION) {
        this.EDUCATION = EDUCATION;
    }

    public String getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(String END_DATE) {
        this.END_DATE = END_DATE;
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

    public String getSCHOOL() {
        return SCHOOL;
    }

    public void setSCHOOL(String SCHOOL) {
        this.SCHOOL = SCHOOL;
    }

    public String getSFZ() {
        return SFZ;
    }

    public void setSFZ(String SFZ) {
        this.SFZ = SFZ;
    }

    public String getSPECIALTY() {
        return SPECIALTY;
    }

    public void setSPECIALTY(String SPECIALTY) {
        this.SPECIALTY = SPECIALTY;
    }

    public String getSTART_DATE() {
        return START_DATE;
    }

    public void setSTART_DATE(String START_DATE) {
        this.START_DATE = START_DATE;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getUPDATE_DATE() {
        return UPDATE_DATE;
    }

    public void setUPDATE_DATE(String UPDATE_DATE) {
        this.UPDATE_DATE = UPDATE_DATE;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }
}
