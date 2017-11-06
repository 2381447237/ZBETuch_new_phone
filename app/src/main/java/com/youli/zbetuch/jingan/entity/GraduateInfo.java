package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * 作者: zhengbin on 2017/10/9.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/Get_Graduate_Master.aspx?page=0&rows=15&name=&sfz=&year=2014
 *
 *
 * [{"ID":100,"NAME":"李祯元","SFZ":"310108199302100016","SEX":"男 ","NATIONS":"汉族 ",
 * "POLITICAL_STATUS":"共青团员","EDU":"大学专科/高职","SCHOOL":"D","SPECIALTY":"H","STREET_ID":8001,"COMMITTEE_ID":201,
 * "ADDRESS":"长安西路123弄13号","NOW_ADDRESS":"长安西路123弄13号","CONTACT_NUMBER":"58785258","MARK":"零就业家庭成员","SURVEY":"",
 * "CREAT_DATE":"2014-02-12T11:00:00.91","CREAT_STAFF":2,"UPDATE_DATE":"2017-10-16T15:23:20.107","UPDATE_STAFF":2,
 * "RecordCount":2990,"COMMITTEE_NAME":null,"STREET_NAME":null}]
 */

public class GraduateInfo implements Serializable{

    private int ID;
    private String NAME;//姓名
    private String SFZ;//身份证
    private String SEX;//性别
    private String NATIONS;
    private String POLITICAL_STATUS;
    private String EDU;
    private String SCHOOL;
    private String SPECIALTY;
    private int STREET_ID;
    private int COMMITTEE_ID;
    private String ADDRESS;
    private String NOW_ADDRESS;
    private String CONTACT_NUMBER;//联系电话
    private String MARK;
    private String SURVEY;
    private String CREAT_DATE;
    private int CREAT_STAFF;
    private String UPDATE_DATE;
    private int UPDATE_STAFF;
    private int RecordCount;
    private String COMMITTEE_NAME;
    private String STREET_NAME;

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public int getCOMMITTEE_ID() {
        return COMMITTEE_ID;
    }

    public void setCOMMITTEE_ID(int COMMITTEE_ID) {
        this.COMMITTEE_ID = COMMITTEE_ID;
    }

    public String getCOMMITTEE_NAME() {
        return COMMITTEE_NAME;
    }

    public void setCOMMITTEE_NAME(String COMMITTEE_NAME) {
        this.COMMITTEE_NAME = COMMITTEE_NAME;
    }

    public String getCONTACT_NUMBER() {
        return CONTACT_NUMBER;
    }

    public void setCONTACT_NUMBER(String CONTACT_NUMBER) {
        this.CONTACT_NUMBER = CONTACT_NUMBER;
    }

    public String getCREAT_DATE() {
        return CREAT_DATE;
    }

    public void setCREAT_DATE(String CREAT_DATE) {
        this.CREAT_DATE = CREAT_DATE;
    }

    public int getCREAT_STAFF() {
        return CREAT_STAFF;
    }

    public void setCREAT_STAFF(int CREAT_STAFF) {
        this.CREAT_STAFF = CREAT_STAFF;
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

    public String getNATIONS() {
        return NATIONS;
    }

    public void setNATIONS(String NATIONS) {
        this.NATIONS = NATIONS;
    }

    public String getNOW_ADDRESS() {
        return NOW_ADDRESS;
    }

    public void setNOW_ADDRESS(String NOW_ADDRESS) {
        this.NOW_ADDRESS = NOW_ADDRESS;
    }

    public String getPOLITICAL_STATUS() {
        return POLITICAL_STATUS;
    }

    public void setPOLITICAL_STATUS(String POLITICAL_STATUS) {
        this.POLITICAL_STATUS = POLITICAL_STATUS;
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

    public String getSPECIALTY() {
        return SPECIALTY;
    }

    public void setSPECIALTY(String SPECIALTY) {
        this.SPECIALTY = SPECIALTY;
    }

    public int getSTREET_ID() {
        return STREET_ID;
    }

    public void setSTREET_ID(int STREET_ID) {
        this.STREET_ID = STREET_ID;
    }

    public String getSTREET_NAME() {
        return STREET_NAME;
    }

    public void setSTREET_NAME(String STREET_NAME) {
        this.STREET_NAME = STREET_NAME;
    }

    public String getSURVEY() {
        return SURVEY;
    }

    public void setSURVEY(String SURVEY) {
        this.SURVEY = SURVEY;
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
}
