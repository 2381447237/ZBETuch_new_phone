package com.youli.zbetuch.jingan.entity;

import java.util.List;

/**
 * Created by liutao on 2017/8/14.
 */

public class FamilyAddressInfo {

    private String title;

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


   private List<FamilyAddressInfoList> list;

    public List<FamilyAddressInfoList> getList() {
        return list;
    }

    public void setList(List<FamilyAddressInfoList> list) {
        this.list = list;
    }

    public FamilyAddressInfo(boolean isChecked, String title, List<FamilyAddressInfoList> list) {
        this.isChecked = isChecked;
        this.title = title;
        this.list = list;
    }

    public static class FamilyAddressInfoList{
      //  http://web.youli.pw:89/Json/Get_family_Info_Now.aspx?sfz=310108198004026642

       // [{"ID":169917,"SOURCE":5,"ISVERIFY":0,"SFZ":"310108198004026642","NAME":"储明净静","SEX":"女 ","BIRTH_DATE":"1980-04-02T00:00:00",
        // "CULTURAL_CODE":"高中","POLITICAL":" ","RESIDE_TYPE":"2 ","CONTACT_NUMBER":"","NATIONS":"汉族 ","IMG":null,"NATIVE":"",
        // "CREATE_TIME":"2013-05-02T12:03:32.35","CREATE_USER_ID":1,"MODIFY_TIME":"2017-08-17T16:27:18.943","MODIFY_USER_ID":2,"DR":0,
        // "STREET_ID":8013,"COMMITTEE_ID":65,"TYPE":"未登记失业","ROAD":"大宁路","LANE":"535弄","NO":"16号","ROOM":"602室","NOW_ROAD":"测试",
        // "NOW_LANE":"测试","NOW_NO":"测试","NOW_ROOM":"测试","Current_situation":"其他","Current_intent":"无意向分类","Remark":"",
        // "RecordCount":0,"Center":null,"GetPhotoUrl":"../../../image/ooopic_1369286910.png","GetPhoto":"无","LevelMsg":null,"GPS":null,"Md":"其他"}]

        private int ID;
        private int SOURCE;
        private int ISVERIFY;
        private String NAME;//要显示的
        private String SEX;//要显示的
        private String BIRTH_DATE;//要显示的
        private String SFZ;//要显示的
        private String CULTURAL_CODE;
        private String POLITICAL;
        private String RESIDE_TYPE;
        private String CONTACT_NUMBER;
        private String NATIONS;
        private String IMG;
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
        private String Center;
        private String GetPhotoUrl;
        private String GetPhoto;
        private String LevelMsg;
        private String GPS;
        private String Md;

        public FamilyAddressInfoList(String NAME, String SEX, String BIRTH_DATE, String SFZ) {
            this.NAME = NAME;
            this.SEX = SEX;
            this.BIRTH_DATE = BIRTH_DATE;
            this.SFZ = SFZ;
        }

        public String getBIRTH_DATE() {
            return BIRTH_DATE;
        }

        public void setBIRTH_DATE(String BIRTH_DATE) {
            this.BIRTH_DATE = BIRTH_DATE;
        }

        public String getCenter() {
            return Center;
        }

        public void setCenter(String center) {
            Center = center;
        }

        public int getCOMMITTEE_ID() {
            return COMMITTEE_ID;
        }

        public void setCOMMITTEE_ID(int COMMITTEE_ID) {
            this.COMMITTEE_ID = COMMITTEE_ID;
        }

        public String getCONTACT_NUMBER() {
            return CONTACT_NUMBER;
        }

        public void setCONTACT_NUMBER(String CONTACT_NUMBER) {
            this.CONTACT_NUMBER = CONTACT_NUMBER;
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

        public String getCULTURAL_CODE() {
            return CULTURAL_CODE;
        }

        public void setCULTURAL_CODE(String CULTURAL_CODE) {
            this.CULTURAL_CODE = CULTURAL_CODE;
        }

        public String getCurrent_intent() {
            return Current_intent;
        }

        public void setCurrent_intent(String current_intent) {
            Current_intent = current_intent;
        }

        public String getCurrent_situation() {
            return Current_situation;
        }

        public void setCurrent_situation(String current_situation) {
            Current_situation = current_situation;
        }

        public int getDR() {
            return DR;
        }

        public void setDR(int DR) {
            this.DR = DR;
        }

        public String getGetPhoto() {
            return GetPhoto;
        }

        public void setGetPhoto(String getPhoto) {
            GetPhoto = getPhoto;
        }

        public String getGetPhotoUrl() {
            return GetPhotoUrl;
        }

        public void setGetPhotoUrl(String getPhotoUrl) {
            GetPhotoUrl = getPhotoUrl;
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

        public String getIMG() {
            return IMG;
        }

        public void setIMG(String IMG) {
            this.IMG = IMG;
        }

        public int getISVERIFY() {
            return ISVERIFY;
        }

        public void setISVERIFY(int ISVERIFY) {
            this.ISVERIFY = ISVERIFY;
        }

        public String getLANE() {
            return LANE;
        }

        public void setLANE(String LANE) {
            this.LANE = LANE;
        }

        public String getLevelMsg() {
            return LevelMsg;
        }

        public void setLevelMsg(String levelMsg) {
            LevelMsg = levelMsg;
        }

        public String getMd() {
            return Md;
        }

        public void setMd(String md) {
            Md = md;
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

        public String getNATIVE() {
            return NATIVE;
        }

        public void setNATIVE(String NATIVE) {
            this.NATIVE = NATIVE;
        }

        public String getNO() {
            return NO;
        }

        public void setNO(String NO) {
            this.NO = NO;
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

        public String getNOW_ROAD() {
            return NOW_ROAD;
        }

        public void setNOW_ROAD(String NOW_ROAD) {
            this.NOW_ROAD = NOW_ROAD;
        }

        public String getNOW_ROOM() {
            return NOW_ROOM;
        }

        public void setNOW_ROOM(String NOW_ROOM) {
            this.NOW_ROOM = NOW_ROOM;
        }

        public String getPOLITICAL() {
            return POLITICAL;
        }

        public void setPOLITICAL(String POLITICAL) {
            this.POLITICAL = POLITICAL;
        }

        public int getRecordCount() {
            return RecordCount;
        }

        public void setRecordCount(int recordCount) {
            RecordCount = recordCount;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getRESIDE_TYPE() {
            return RESIDE_TYPE;
        }

        public void setRESIDE_TYPE(String RESIDE_TYPE) {
            this.RESIDE_TYPE = RESIDE_TYPE;
        }

        public String getROAD() {
            return ROAD;
        }

        public void setROAD(String ROAD) {
            this.ROAD = ROAD;
        }

        public String getROOM() {
            return ROOM;
        }

        public void setROOM(String ROOM) {
            this.ROOM = ROOM;
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

        public int getSOURCE() {
            return SOURCE;
        }

        public void setSOURCE(int SOURCE) {
            this.SOURCE = SOURCE;
        }

        public int getSTREET_ID() {
            return STREET_ID;
        }

        public void setSTREET_ID(int STREET_ID) {
            this.STREET_ID = STREET_ID;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }
    }
}
