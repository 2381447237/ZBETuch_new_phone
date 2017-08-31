package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * Created by liutao on 2017/8/9.
 */
//http://web.youli.pw:89/Json/Get_Resource_Survey_Detil_SY.aspx?page=0&rows=1&Master_id=1&typeName=%E5%A4%B1%E4%B8%9A&type=0
//[{"ID":2423,"MASTER_ID":1,"JD":"北站街道","JW":"北高居委","NAME":"姚磊陵",
// "SEX":"男","SFZ":"310107197511175035","MZ":"汉族","CSRQ":"1975-11-17T00:00:00","EDU":"高中",
// "HKDZ":"安庆路482号","MQZK":"积极求职","MDRQ":"20140808","DQYX":"就业条件一般","ZJSYDJRQ":"2014-05-23T00:00:00","SYDJYXQ":"2014-11-23T00:00:00",
// "CJR":"否 ","NEW_MQZK":"服兵役","NEW_DQYX":"就业条件较好","CREATE_DATE":"2014-08-21T12:03:31.103","UPDATE_DATE":"2015-05-21T09:43:17.923"
// ,"UPDATE_STAFF":2,"MARK":"Ggg","PHONE":"12345678","RecordCount":10,"card_type":"身份证","type":"失业",
// "Is_Update":false,"SURVEY_DATE":"2015-05-21T00:00:00","_SURVEY_DATE":"2015-05-21","STAFF_Name":null}]


//[{"ID":537,"MASTER_ID":2,"JD":"大宁路街道","JW":"667弄居委会","NAME":"陈丰娟","SEX":"女","SFZ":"340703197807260020","MZ":"汉族",
// "CSRQ":"1978-07-26T00:00:00","EDU":"大学毕业","HKDZ":"大宁路535弄10号602室","MQZK":"有劳动收入","MDRQ":"20130729","DQYX":"无需意向分类",
// "CJR":"否 ","NEW_MQZK":"患严重疾病","NEW_DQYX":"-1","CREATE_DATE":"2014-08-21T14:31:19.033","UPDATE_DATE":"2017-03-28T13:48:01.847","
// UPDATE_STAFF":2,"MARK":"","RecordCount":24,"card_type":"身份证","type":"无业","Is_Update":false,"SURVEY_DATE":"2017-03-28T00:00:00",
// "_SURVEY_DATE":"2017-03-28","STAFF_Name":null}]
public class ResourcesDetailInfo implements Serializable{

    //private int SHUNXUID;//顺序，要显示的

   private int ID;
    private int MASTER_ID;
    private String JD;
    private String JW;//要显示的
    private String NAME;//要显示的
    private String SEX;
    private String SFZ;//要显示的
    private String MZ;
    private String CSRQ;
    private String EDU;
    private String HKDZ;
    private String MQZK;
    private String MDRQ;
    private String DQYX;
    private String ZJSYDJRQ;
    private String SYDJYXQ;
    private String CJR;
    private String NEW_MQZK;
    private String NEW_DQYX;
    private String CREATE_DATE;
    private String UPDATE_DATE;
    private String UPDATE_STAFF;
    private String MARK;
    private String PHONE;
    private int RecordCount;
    private String card_type;
    private String type;
    private boolean Is_Update;
    private String SURVEY_DATE;
    private String _SURVEY_DATE;
    private String STAFF_Name;

    public String get_SURVEY_DATE() {
        return _SURVEY_DATE;
    }

    public void set_SURVEY_DATE(String _SURVEY_DATE) {
        this._SURVEY_DATE = _SURVEY_DATE;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCJR() {
        return CJR;
    }

    public void setCJR(String CJR) {
        this.CJR = CJR;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getCSRQ() {
        return CSRQ;
    }

    public void setCSRQ(String CSRQ) {
        this.CSRQ = CSRQ;
    }

    public String getDQYX() {
        return DQYX;
    }

    public void setDQYX(String DQYX) {
        this.DQYX = DQYX;
    }

    public String getEDU() {
        return EDU;
    }

    public void setEDU(String EDU) {
        this.EDU = EDU;
    }

    public String getHKDZ() {
        return HKDZ;
    }

    public void setHKDZ(String HKDZ) {
        this.HKDZ = HKDZ;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean is_Update() {
        return Is_Update;
    }

    public void setIs_Update(boolean is_Update) {
        Is_Update = is_Update;
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

    public String getMARK() {
        return MARK;
    }

    public void setMARK(String MARK) {
        this.MARK = MARK;
    }

    public int getMASTER_ID() {
        return MASTER_ID;
    }

    public void setMASTER_ID(int MASTER_ID) {
        this.MASTER_ID = MASTER_ID;
    }

    public String getMDRQ() {
        return MDRQ;
    }

    public void setMDRQ(String MDRQ) {
        this.MDRQ = MDRQ;
    }

    public String getMQZK() {
        return MQZK;
    }

    public void setMQZK(String MQZK) {
        this.MQZK = MQZK;
    }

    public String getMZ() {
        return MZ;
    }

    public void setMZ(String MZ) {
        this.MZ = MZ;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getNEW_DQYX() {
        return NEW_DQYX;
    }

    public void setNEW_DQYX(String NEW_DQYX) {
        this.NEW_DQYX = NEW_DQYX;
    }

    public String getNEW_MQZK() {
        return NEW_MQZK;
    }

    public void setNEW_MQZK(String NEW_MQZK) {
        this.NEW_MQZK = NEW_MQZK;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
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

//    public int getSHUNXUID() {
//        return SHUNXUID;
//    }
//
//    public void setSHUNXUID(int SHUNXUID) {
//        this.SHUNXUID = SHUNXUID;
//    }

    public String getSTAFF_Name() {
        return STAFF_Name;
    }

    public void setSTAFF_Name(String STAFF_Name) {
        this.STAFF_Name = STAFF_Name;
    }

    public String getSURVEY_DATE() {
        return SURVEY_DATE;
    }

    public void setSURVEY_DATE(String SURVEY_DATE) {
        this.SURVEY_DATE = SURVEY_DATE;
    }

    public String getSYDJYXQ() {
        return SYDJYXQ;
    }

    public void setSYDJYXQ(String SYDJYXQ) {
        this.SYDJYXQ = SYDJYXQ;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUPDATE_DATE() {
        return UPDATE_DATE;
    }

    public void setUPDATE_DATE(String UPDATE_DATE) {
        this.UPDATE_DATE = UPDATE_DATE;
    }

    public String getUPDATE_STAFF() {
        return UPDATE_STAFF;
    }

    public void setUPDATE_STAFF(String UPDATE_STAFF) {
        this.UPDATE_STAFF = UPDATE_STAFF;
    }

    public String getZJSYDJRQ() {
        return ZJSYDJRQ;
    }

    public void setZJSYDJRQ(String ZJSYDJRQ) {
        this.ZJSYDJRQ = ZJSYDJRQ;
    }
}
