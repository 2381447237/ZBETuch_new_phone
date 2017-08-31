package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/4.
 */
//会议通知
//http://web.youli.pw:89/Json/Get_Meeting_Master.aspx?State=true&page=0&rows=1
//[{"ID":10,"TITLE":"ssadas","DOC":"SsSs","MEETING_TIME":"2017-03-29T00:00:00","MEETING_ADD":"aAS",
// "CREATE_STAFF":2,"CREATE_DATE":"2017-03-29T11:25:40.163","RecordCount":9,"CREATE_STAFF_NAME":"admin",
// "METTING_STAFFS":null,"Checks":null}]
public class MeetInfo {

    private int ID;
    private String TITLE;//显示这个
    private String DOC;
    private String MEETING_TIME;//显示这个
    private String MEETING_ADD;
    private int CREATE_STAFF;
    private String CREATE_DATE;
    private int RecordCount;
    private String CREATE_STAFF_NAME;
    private String METTING_STAFFS;
    private String Checks;

    public MeetInfo(String TITLE, String MEETING_TIME) {
        this.TITLE = TITLE;
        this.MEETING_TIME = MEETING_TIME;
    }

    public String getChecks() {
        return Checks;
    }

    public void setChecks(String checks) {
        Checks = checks;
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

    public String getCREATE_STAFF_NAME() {
        return CREATE_STAFF_NAME;
    }

    public void setCREATE_STAFF_NAME(String CREATE_STAFF_NAME) {
        this.CREATE_STAFF_NAME = CREATE_STAFF_NAME;
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

    public String getMEETING_ADD() {
        return MEETING_ADD;
    }

    public void setMEETING_ADD(String MEETING_ADD) {
        this.MEETING_ADD = MEETING_ADD;
    }

    public String getMEETING_TIME() {
        return MEETING_TIME;
    }

    public void setMEETING_TIME(String MEETING_TIME) {
        this.MEETING_TIME = MEETING_TIME;
    }

    public String getMETTING_STAFFS() {
        return METTING_STAFFS;
    }

    public void setMETTING_STAFFS(String METTING_STAFFS) {
        this.METTING_STAFFS = METTING_STAFFS;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }
}
