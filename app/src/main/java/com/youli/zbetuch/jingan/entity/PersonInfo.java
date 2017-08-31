package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * Created by liutao on 2017/8/17.
 */

//http://web.youli.pw:89/Json/Get_BASIC_INFORMATION.aspx?sfz=310101198711030515
public class PersonInfo implements Serializable{
    /**
     * ID : 170380
     * SOURCE : 5
     * ISVERIFY : 0
     * SFZ : 310101198711030515
     * NAME : 王俊
     * SEX : 男
     * BIRTH_DATE : 1987-11-03T00:00:00
     * CULTURAL_CODE : 研究生毕业
     * POLITICAL :
     * RESIDE_TYPE : 2
     * CONTACT_NUMBER :
     * NATIONS : 汉
     * IMG : null
     * NATIVE :
     * CREATE_TIME : 2013-05-02T12:03:32.35
     * CREATE_USER_ID : 1
     * MODIFY_TIME : 0001-01-01T00:00:00
     * MODIFY_USER_ID : 0
     * DR : 0
     * STREET_ID : 8013
     * COMMITTEE_ID : 65
     * TYPE : 未登记失业
     * ROAD : 大宁路
     * LANE : 535弄
     * NO : 13号
     * ROOM : 1301室
     * NOW_ROAD : 大宁路
     * NOW_LANE : 535弄
     * NOW_NO : 13号
     * NOW_ROOM : 1301室
     * Current_situation : 入学
     * Current_intent : 无需意向分类
     * Remark :
     * RecordCount : 0
     * Center : {"Q序号":" 38118","Q姓名":"王俊","Q性别":"男","Q证件类型":"身份证","Q证件号码":"310101198711030515","Q民族":"汉族","Q出生日期":"1987年11月03日","Q文化程度":"大学本科(简称大学)","Q户口地址":"大宁路535弄13号1301室","Q目前摸底状况":"入学","Q当前意向":"无需意向分类","Q残疾人":"否","Q已核":"否","Q户口所属街道":"大宁路街道","Q居委会":"667弄居委会","TYPE":"登记失业","CREATE_DATE":"2017-04-06T10:51:18.493","CREATE_STAFF":2,"UPDATE_DATE":"2017-04-06T10:51:10.327","UPDATE_STAFF":0,"COMPARE_RESULT":"差异","RecordCount":0}
     * GetPhotoUrl : ../../../image/ooopic_1367546805.png
     * GetPhoto : 无
     * LevelMsg : null
     * GPS : null
     * Md : 入学
     */

    private int ID;
    private int SOURCE;
    private int ISVERIFY;
    private String SFZ;
    private String NAME;
    private String SEX;
    private String BIRTH_DATE;
    private String CULTURAL_CODE;
    private String POLITICAL;
    private String RESIDE_TYPE;
    private String CONTACT_NUMBER;
    private String NATIONS;
    private Object IMG;
    private String NATIVE;
    private String CREATE_TIME;
    private int CREATE_USER_ID;
    private String MODIFY_TIME;
    private int MODIFY_USER_ID;
    private int DR;
    private int STREET_ID;
    private int COMMITTEE_ID;
    private String TYPE;
    private String ROAD;
    private String LANE;
    private String NO;
    private String ROOM;
    private String NOW_ROAD;
    private String NOW_LANE;
    private String NOW_NO;
    private String NOW_ROOM;
    private String Current_situation;
    private String Current_intent;
    private String Remark;
    private int RecordCount;
    private CenterBean Center;
    private String GetPhotoUrl;
    private String GetPhoto;
    private Object LevelMsg;
    private Object GPS;
    private String Md;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(int SOURCE) {
        this.SOURCE = SOURCE;
    }

    public int getISVERIFY() {
        return ISVERIFY;
    }

    public void setISVERIFY(int ISVERIFY) {
        this.ISVERIFY = ISVERIFY;
    }

    public String getSFZ() {
        return SFZ;
    }

    public void setSFZ(String SFZ) {
        this.SFZ = SFZ;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getBIRTH_DATE() {
        return BIRTH_DATE;
    }

    public void setBIRTH_DATE(String BIRTH_DATE) {
        this.BIRTH_DATE = BIRTH_DATE;
    }

    public String getCULTURAL_CODE() {
        return CULTURAL_CODE;
    }

    public void setCULTURAL_CODE(String CULTURAL_CODE) {
        this.CULTURAL_CODE = CULTURAL_CODE;
    }

    public String getPOLITICAL() {
        return POLITICAL;
    }

    public void setPOLITICAL(String POLITICAL) {
        this.POLITICAL = POLITICAL;
    }

    public String getRESIDE_TYPE() {
        return RESIDE_TYPE;
    }

    public void setRESIDE_TYPE(String RESIDE_TYPE) {
        this.RESIDE_TYPE = RESIDE_TYPE;
    }

    public String getCONTACT_NUMBER() {
        return CONTACT_NUMBER;
    }

    public void setCONTACT_NUMBER(String CONTACT_NUMBER) {
        this.CONTACT_NUMBER = CONTACT_NUMBER;
    }

    public String getNATIONS() {
        return NATIONS;
    }

    public void setNATIONS(String NATIONS) {
        this.NATIONS = NATIONS;
    }

    public Object getIMG() {
        return IMG;
    }

    public void setIMG(Object IMG) {
        this.IMG = IMG;
    }

    public String getNATIVE() {
        return NATIVE;
    }

    public void setNATIVE(String NATIVE) {
        this.NATIVE = NATIVE;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public int getCREATE_USER_ID() {
        return CREATE_USER_ID;
    }

    public void setCREATE_USER_ID(int CREATE_USER_ID) {
        this.CREATE_USER_ID = CREATE_USER_ID;
    }

    public String getMODIFY_TIME() {
        return MODIFY_TIME;
    }

    public void setMODIFY_TIME(String MODIFY_TIME) {
        this.MODIFY_TIME = MODIFY_TIME;
    }

    public int getMODIFY_USER_ID() {
        return MODIFY_USER_ID;
    }

    public void setMODIFY_USER_ID(int MODIFY_USER_ID) {
        this.MODIFY_USER_ID = MODIFY_USER_ID;
    }

    public int getDR() {
        return DR;
    }

    public void setDR(int DR) {
        this.DR = DR;
    }

    public int getSTREET_ID() {
        return STREET_ID;
    }

    public void setSTREET_ID(int STREET_ID) {
        this.STREET_ID = STREET_ID;
    }

    public int getCOMMITTEE_ID() {
        return COMMITTEE_ID;
    }

    public void setCOMMITTEE_ID(int COMMITTEE_ID) {
        this.COMMITTEE_ID = COMMITTEE_ID;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getROAD() {
        return ROAD;
    }

    public void setROAD(String ROAD) {
        this.ROAD = ROAD;
    }

    public String getLANE() {
        return LANE;
    }

    public void setLANE(String LANE) {
        this.LANE = LANE;
    }

    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    public String getROOM() {
        return ROOM;
    }

    public void setROOM(String ROOM) {
        this.ROOM = ROOM;
    }

    public String getNOW_ROAD() {
        return NOW_ROAD;
    }

    public void setNOW_ROAD(String NOW_ROAD) {
        this.NOW_ROAD = NOW_ROAD;
    }

    public String getNOW_LANE() {
        return NOW_LANE;
    }

    public void setNOW_LANE(String NOW_LANE) {
        this.NOW_LANE = NOW_LANE;
    }

    public String getNOW_NO() {
        return NOW_NO;
    }

    public void setNOW_NO(String NOW_NO) {
        this.NOW_NO = NOW_NO;
    }

    public String getNOW_ROOM() {
        return NOW_ROOM;
    }

    public void setNOW_ROOM(String NOW_ROOM) {
        this.NOW_ROOM = NOW_ROOM;
    }

    public String getCurrent_situation() {
        return Current_situation;
    }

    public void setCurrent_situation(String Current_situation) {
        this.Current_situation = Current_situation;
    }

    public String getCurrent_intent() {
        return Current_intent;
    }

    public void setCurrent_intent(String Current_intent) {
        this.Current_intent = Current_intent;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int RecordCount) {
        this.RecordCount = RecordCount;
    }

    public CenterBean getCenter() {
        return Center;
    }

    public void setCenter(CenterBean Center) {
        this.Center = Center;
    }

    public String getGetPhotoUrl() {
        return GetPhotoUrl;
    }

    public void setGetPhotoUrl(String GetPhotoUrl) {
        this.GetPhotoUrl = GetPhotoUrl;
    }

    public String getGetPhoto() {
        return GetPhoto;
    }

    public void setGetPhoto(String GetPhoto) {
        this.GetPhoto = GetPhoto;
    }

    public Object getLevelMsg() {
        return LevelMsg;
    }

    public void setLevelMsg(Object LevelMsg) {
        this.LevelMsg = LevelMsg;
    }

    public Object getGPS() {
        return GPS;
    }

    public void setGPS(Object GPS) {
        this.GPS = GPS;
    }

    public String getMd() {
        return Md;
    }

    public void setMd(String Md) {
        this.Md = Md;
    }

    public static class CenterBean implements Serializable{
        /**
         * Q序号 :  38118
         * Q姓名 : 王俊
         * Q性别 : 男
         * Q证件类型 : 身份证
         * Q证件号码 : 310101198711030515
         * Q民族 : 汉族
         * Q出生日期 : 1987年11月03日
         * Q文化程度 : 大学本科(简称大学)
         * Q户口地址 : 大宁路535弄13号1301室
         * Q目前摸底状况 : 入学
         * Q当前意向 : 无需意向分类
         * Q残疾人 : 否
         * Q已核 : 否
         * Q户口所属街道 : 大宁路街道
         * Q居委会 : 667弄居委会
         * TYPE : 登记失业
         * CREATE_DATE : 2017-04-06T10:51:18.493
         * CREATE_STAFF : 2
         * UPDATE_DATE : 2017-04-06T10:51:10.327
         * UPDATE_STAFF : 0
         * COMPARE_RESULT : 差异
         * RecordCount : 0
         */

        private String Q序号;
        private String Q姓名;
        private String Q性别;
        private String Q证件类型;
        private String Q证件号码;
        private String Q民族;
        private String Q出生日期;
        private String Q文化程度;
        private String Q户口地址;
        private String Q目前摸底状况;
        private String Q当前意向;
        private String Q残疾人;
        private String Q已核;
        private String Q户口所属街道;
        private String Q居委会;
        private String TYPE;
        private String CREATE_DATE;
        private int CREATE_STAFF;
        private String UPDATE_DATE;
        private int UPDATE_STAFF;
        private String COMPARE_RESULT;
        private int RecordCount;

        public String getQ序号() {
            return Q序号;
        }

        public void setQ序号(String Q序号) {
            this.Q序号 = Q序号;
        }

        public String getQ姓名() {
            return Q姓名;
        }

        public void setQ姓名(String Q姓名) {
            this.Q姓名 = Q姓名;
        }

        public String getQ性别() {
            return Q性别;
        }

        public void setQ性别(String Q性别) {
            this.Q性别 = Q性别;
        }

        public String getQ证件类型() {
            return Q证件类型;
        }

        public void setQ证件类型(String Q证件类型) {
            this.Q证件类型 = Q证件类型;
        }

        public String getQ证件号码() {
            return Q证件号码;
        }

        public void setQ证件号码(String Q证件号码) {
            this.Q证件号码 = Q证件号码;
        }

        public String getQ民族() {
            return Q民族;
        }

        public void setQ民族(String Q民族) {
            this.Q民族 = Q民族;
        }

        public String getQ出生日期() {
            return Q出生日期;
        }

        public void setQ出生日期(String Q出生日期) {
            this.Q出生日期 = Q出生日期;
        }

        public String getQ文化程度() {
            return Q文化程度;
        }

        public void setQ文化程度(String Q文化程度) {
            this.Q文化程度 = Q文化程度;
        }

        public String getQ户口地址() {
            return Q户口地址;
        }

        public void setQ户口地址(String Q户口地址) {
            this.Q户口地址 = Q户口地址;
        }

        public String getQ目前摸底状况() {
            return Q目前摸底状况;
        }

        public void setQ目前摸底状况(String Q目前摸底状况) {
            this.Q目前摸底状况 = Q目前摸底状况;
        }

        public String getQ当前意向() {
            return Q当前意向;
        }

        public void setQ当前意向(String Q当前意向) {
            this.Q当前意向 = Q当前意向;
        }

        public String getQ残疾人() {
            return Q残疾人;
        }

        public void setQ残疾人(String Q残疾人) {
            this.Q残疾人 = Q残疾人;
        }

        public String getQ已核() {
            return Q已核;
        }

        public void setQ已核(String Q已核) {
            this.Q已核 = Q已核;
        }

        public String getQ户口所属街道() {
            return Q户口所属街道;
        }

        public void setQ户口所属街道(String Q户口所属街道) {
            this.Q户口所属街道 = Q户口所属街道;
        }

        public String getQ居委会() {
            return Q居委会;
        }

        public void setQ居委会(String Q居委会) {
            this.Q居委会 = Q居委会;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
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

        public String getCOMPARE_RESULT() {
            return COMPARE_RESULT;
        }

        public void setCOMPARE_RESULT(String COMPARE_RESULT) {
            this.COMPARE_RESULT = COMPARE_RESULT;
        }

        public int getRecordCount() {
            return RecordCount;
        }

        public void setRecordCount(int RecordCount) {
            this.RecordCount = RecordCount;
        }
    }

    // [{"ID":170380,"SOURCE":5,"ISVERIFY":0,"SFZ":"310101198711030515","NAME":"王俊","SEX":"男 ","BIRTH_DATE":"1987-11-03T00:00:00",
    // "CULTURAL_CODE":"研究生毕业","POLITICAL":" ","RESIDE_TYPE":"2 ","CONTACT_NUMBER":"","NATIONS":"汉 ",
    // "IMG":null,"NATIVE":"","CREATE_TIME":"2013-05-02T12:03:32.35","CREATE_USER_ID":1,"MODIFY_TIME":"0001-01-01T00:00:00",
    // "MODIFY_USER_ID":0,"DR":0,"STREET_ID":8013,"COMMITTEE_ID":65,"TYPE":"未登记失业",
    // "ROAD":"大宁路","LANE":"535弄","NO":"13号","ROOM":"1301室","NOW_ROAD":"大宁路","NOW_LANE":"535弄","NOW_NO":"13号","NOW_ROOM":"1301室",
    // "Current_situation":"入学","Current_intent":"无需意向分类","Remark":"","RecordCount":0,
    // "Center":{"Q序号":" 38118","Q姓名":"王俊","Q性别":"男","Q证件类型":"身份证","Q证件号码":"310101198711030515","Q民族":"汉族","Q出生日期":"1987年11月03日",
    // "Q文化程度":"大学本科(简称大学)","Q户口地址":"大宁路535弄13号1301室","Q目前摸底状况":"入学","Q当前意向":"无需意向分类","Q残疾人":"否","Q已核":"否",
    // "Q户口所属街道":"大宁路街道","Q居委会":"667弄居委会","TYPE":"登记失业","CREATE_DATE":"2017-04-06T10:51:18.493","CREATE_STAFF":2,
    // "UPDATE_DATE":"2017-04-06T10:51:10.327","UPDATE_STAFF":0,"COMPARE_RESULT":"差异","RecordCount":0},
    // "GetPhotoUrl":"../../../image/ooopic_1367546805.png","GetPhoto":"无","LevelMsg":null,"GPS":null,"Md":"入学"}]



}
