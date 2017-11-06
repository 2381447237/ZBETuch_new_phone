package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/22.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/Get_Qa_UpLoad_Personnel.aspx?page=0&rows=15&master_id=5&type=1
 *
 * [{"ID":1363,"NO":"080081","PID":"200309244329870","NAME":"郑志银(测试)","SFZ":"530103196209022514","SEX":"男","EDU":"研究生",
 * "ZT":"无业","DZ":"延长路149号","QX":"闸北","JD":"大宁","JW":"上工新村居委","LXDZ":"上大A楼2号301","PHONE":"69980385","QA_MASTER":5,
 * "CREATE_DATE":"2015-02-09T11:21:44.6","CREATE_STAFF":5,"RECEIVED":-1,"MARK":"","RECEIVED_STAFF":2,"RECEIVED_TIME":"2017-03-02T13:28:20.667",
 * "RecordCount":4,"IsCreate":false,"IsDelete":false,"conut":1,"IsJYStatus":true}]
 */

public class NairePersonInfo {

    private int ID;
    private String NO;
    private String PID;
    private String NAME;//姓名
    private String SFZ;
    private String SEX;//性别
    private String EDU;
    private String ZT;
    private String DZ;//户籍地址
    private String QX;
    private String JD;//街道
    private String JW;//居委
    private String LXDZ;
    private String PHONE;
    private int QA_MASTER;
    private String CREATE_DATE;
    private int CREATE_STAFF;
    private int RECEIVED;
    private String MARK;
    private int RECEIVED_STAFF;
    private String RECEIVED_TIME;
    private int RecordCount;//人数
    private boolean IsCreate;
    private boolean IsDelete;
    private int conut;//家庭成员数量
    private boolean IsJYStatus;

    public int getConut() {
        return conut;
    }

    public void setConut(int conut) {
        this.conut = conut;
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

    public String getDZ() {
        return DZ;
    }

    public void setDZ(String DZ) {
        this.DZ = DZ;
    }

    public String getEDU() {
        return EDU;
    }

    public void setEDU(String EDU) {
        this.EDU = EDU;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isCreate() {
        return IsCreate;
    }

    public void setCreate(boolean create) {
        IsCreate = create;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public boolean isJYStatus() {
        return IsJYStatus;
    }

    public void setJYStatus(boolean JYStatus) {
        IsJYStatus = JYStatus;
    }

    public String getJD() {
        return JD;
    }

    public void setJD(String JD) {
        this.JD = JD;
    }

    public String getJW() {
        return JW;
    }

    public void setJW(String JW) {
        this.JW = JW;
    }

    public String getLXDZ() {
        return LXDZ;
    }

    public void setLXDZ(String LXDZ) {
        this.LXDZ = LXDZ;
    }

    public String getMARK() {
        return MARK;
    }

    public void setMARK(String MARK) {
        this.MARK = MARK;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public int getQA_MASTER() {
        return QA_MASTER;
    }

    public void setQA_MASTER(int QA_MASTER) {
        this.QA_MASTER = QA_MASTER;
    }

    public String getQX() {
        return QX;
    }

    public void setQX(String QX) {
        this.QX = QX;
    }

    public int getRECEIVED() {
        return RECEIVED;
    }

    public void setRECEIVED(int RECEIVED) {
        this.RECEIVED = RECEIVED;
    }

    public int getRECEIVED_STAFF() {
        return RECEIVED_STAFF;
    }

    public void setRECEIVED_STAFF(int RECEIVED_STAFF) {
        this.RECEIVED_STAFF = RECEIVED_STAFF;
    }

    public String getRECEIVED_TIME() {
        return RECEIVED_TIME;
    }

    public void setRECEIVED_TIME(String RECEIVED_TIME) {
        this.RECEIVED_TIME = RECEIVED_TIME;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getSFZ() {
        return SFZ;
    }

    public void setSFZ(String SFZ) {
        this.SFZ = SFZ;
    }

    public String getZT() {
        return ZT;
    }

    public void setZT(String ZT) {
        this.ZT = ZT;
    }
}
