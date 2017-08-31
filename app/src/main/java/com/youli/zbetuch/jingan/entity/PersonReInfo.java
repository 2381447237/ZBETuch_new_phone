package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/21.
 */


//个人简历
//http://web.youli.pw:89/Json/Get_Resumes_Info.aspx?sfz=310108198004026642

//[{"IDNO":"310108198004026642","NAME":"储明净静","SEX":"女 ","BIRTH_DATE":"1980-04-02T00:00:00","CULTURAL_CODE":"高中",
// "NATIONS":"汉族 ","CONTACT_NUMBER":"","ADDRESS":"测试测试测试测试","COMPUTERLEVELID":"精通","COMPUTERCERT":"2级",
// "LANGUAGEID_1":"英语","LANGUAGEPROFICIENCYID_1":"精通","LANGUAGEID_2":"德语","LANGUAGEPROFICIENCYID_2":"良好",
// "LANGUAGECERT":"英语4级","OTHERCERTS":"教师资格证","WORKYEARS":-1.0,"SELFEVALUATION":"本人性格开朗","ZYFLID_1":"工程技术人员"
// ,"ZYFLCHILDID_1":"地质勘探工程技术人员","ZYFLID_2":"农业技术人员","ZYFLCHILDID_2":"兽医兽药技术人员","OTHERZYFL":"测试",
// "STARTSALARY":5001.00,"ENDSALARY":8000.00,"GZXZID":1,"GZXZNAME":"全日制劳动合同","GZBSID":1,"GZBSNAME":"常日班",
// "AREAID_1":"黄浦","AREAID_2":"普陀","AREAID_3":"长宁","ISRECOMMENDED":true}]
public class PersonReInfo {

    private String IDNO;
    private String NAME;
    private String SEX;
    private String BIRTH_DATE;
    private String CULTURAL_CODE;
    private String NATIONS;
    private String CONTACT_NUMBER;
    private String ADDRESS;
    private String COMPUTERLEVELID;//计算机应用能力
    private String COMPUTERCERT;//计算机证书
    private String LANGUAGEID_1;//外语语种1
    private String LANGUAGEPROFICIENCYID_1;//外语语种1的熟练程度
    private String LANGUAGEID_2;//外语语种2
    private String LANGUAGEPROFICIENCYID_2;//外语语种2的熟练程度
    private String LANGUAGECERT;//外语类证书
    private String OTHERCERTS;//其他职业技能证书
    private float WORKYEARS;
    private String SELFEVALUATION;//自我评价
    private String ZYFLID_1; //欲从事岗位1
    private String ZYFLCHILDID_1;//欲从事岗位1右边的
    private String ZYFLID_2;//欲从事岗位2
    private String ZYFLCHILDID_2;//欲从事岗位2右边的
    private String OTHERZYFL;//欲从事岗位3
    private int STARTSALARY;//期望薪资下限
    private int ENDSALARY;//期望薪资上限
    private int GZXZID;
    private String GZXZNAME; //用工性质
    private int GZBSID;
    private String GZBSNAME; //工作班时
    private String AREAID_1; //工作地区1
    private String AREAID_2; //工作地区2
    private String AREAID_3; //工作地区3
    private boolean ISRECOMMENDED;

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getAREAID_1() {
        return AREAID_1;
    }

    public void setAREAID_1(String AREAID_1) {
        this.AREAID_1 = AREAID_1;
    }

    public String getAREAID_2() {
        return AREAID_2;
    }

    public void setAREAID_2(String AREAID_2) {
        this.AREAID_2 = AREAID_2;
    }

    public String getAREAID_3() {
        return AREAID_3;
    }

    public void setAREAID_3(String AREAID_3) {
        this.AREAID_3 = AREAID_3;
    }

    public String getBIRTH_DATE() {
        return BIRTH_DATE;
    }

    public void setBIRTH_DATE(String BIRTH_DATE) {
        this.BIRTH_DATE = BIRTH_DATE;
    }

    public String getCOMPUTERCERT() {
        return COMPUTERCERT;
    }

    public void setCOMPUTERCERT(String COMPUTERCERT) {
        this.COMPUTERCERT = COMPUTERCERT;
    }

    public String getCOMPUTERLEVELID() {
        return COMPUTERLEVELID;
    }

    public void setCOMPUTERLEVELID(String COMPUTERLEVELID) {
        this.COMPUTERLEVELID = COMPUTERLEVELID;
    }

    public String getCONTACT_NUMBER() {
        return CONTACT_NUMBER;
    }

    public void setCONTACT_NUMBER(String CONTACT_NUMBER) {
        this.CONTACT_NUMBER = CONTACT_NUMBER;
    }

    public String getCULTURAL_CODE() {
        return CULTURAL_CODE;
    }

    public void setCULTURAL_CODE(String CULTURAL_CODE) {
        this.CULTURAL_CODE = CULTURAL_CODE;
    }


    public int getGZBSID() {
        return GZBSID;
    }

    public void setGZBSID(int GZBSID) {
        this.GZBSID = GZBSID;
    }

    public String getGZBSNAME() {
        return GZBSNAME;
    }

    public void setGZBSNAME(String GZBSNAME) {
        this.GZBSNAME = GZBSNAME;
    }

    public int getGZXZID() {
        return GZXZID;
    }

    public void setGZXZID(int GZXZID) {
        this.GZXZID = GZXZID;
    }

    public String getGZXZNAME() {
        return GZXZNAME;
    }

    public void setGZXZNAME(String GZXZNAME) {
        this.GZXZNAME = GZXZNAME;
    }

    public String getIDNO() {
        return IDNO;
    }

    public void setIDNO(String IDNO) {
        this.IDNO = IDNO;
    }

    public boolean ISRECOMMENDED() {
        return ISRECOMMENDED;
    }

    public void setISRECOMMENDED(boolean ISRECOMMENDED) {
        this.ISRECOMMENDED = ISRECOMMENDED;
    }

    public String getLANGUAGECERT() {
        return LANGUAGECERT;
    }

    public void setLANGUAGECERT(String LANGUAGECERT) {
        this.LANGUAGECERT = LANGUAGECERT;
    }

    public String getLANGUAGEID_1() {
        return LANGUAGEID_1;
    }

    public void setLANGUAGEID_1(String LANGUAGEID_1) {
        this.LANGUAGEID_1 = LANGUAGEID_1;
    }

    public String getLANGUAGEID_2() {
        return LANGUAGEID_2;
    }

    public void setLANGUAGEID_2(String LANGUAGEID_2) {
        this.LANGUAGEID_2 = LANGUAGEID_2;
    }

    public String getLANGUAGEPROFICIENCYID_1() {
        return LANGUAGEPROFICIENCYID_1;
    }

    public void setLANGUAGEPROFICIENCYID_1(String LANGUAGEPROFICIENCYID_1) {
        this.LANGUAGEPROFICIENCYID_1 = LANGUAGEPROFICIENCYID_1;
    }

    public String getLANGUAGEPROFICIENCYID_2() {
        return LANGUAGEPROFICIENCYID_2;
    }

    public void setLANGUAGEPROFICIENCYID_2(String LANGUAGEPROFICIENCYID_2) {
        this.LANGUAGEPROFICIENCYID_2 = LANGUAGEPROFICIENCYID_2;
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

    public String getOTHERCERTS() {
        return OTHERCERTS;
    }

    public void setOTHERCERTS(String OTHERCERTS) {
        this.OTHERCERTS = OTHERCERTS;
    }

    public String getOTHERZYFL() {
        return OTHERZYFL;
    }

    public void setOTHERZYFL(String OTHERZYFL) {
        this.OTHERZYFL = OTHERZYFL;
    }

    public String getSELFEVALUATION() {
        return SELFEVALUATION;
    }

    public void setSELFEVALUATION(String SELFEVALUATION) {
        this.SELFEVALUATION = SELFEVALUATION;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }


    public float getWORKYEARS() {
        return WORKYEARS;
    }

    public void setWORKYEARS(float WORKYEARS) {
        this.WORKYEARS = WORKYEARS;
    }

    public String getZYFLCHILDID_1() {
        return ZYFLCHILDID_1;
    }

    public void setZYFLCHILDID_1(String ZYFLCHILDID_1) {
        this.ZYFLCHILDID_1 = ZYFLCHILDID_1;
    }

    public String getZYFLCHILDID_2() {
        return ZYFLCHILDID_2;
    }

    public void setZYFLCHILDID_2(String ZYFLCHILDID_2) {
        this.ZYFLCHILDID_2 = ZYFLCHILDID_2;
    }

    public String getZYFLID_1() {
        return ZYFLID_1;
    }

    public void setZYFLID_1(String ZYFLID_1) {
        this.ZYFLID_1 = ZYFLID_1;
    }

    public String getZYFLID_2() {
        return ZYFLID_2;
    }

    public void setZYFLID_2(String ZYFLID_2) {
        this.ZYFLID_2 = ZYFLID_2;
    }

    public int getENDSALARY() {
        return ENDSALARY;
    }

    public void setENDSALARY(int ENDSALARY) {
        this.ENDSALARY = ENDSALARY;
    }

    public int getSTARTSALARY() {
        return STARTSALARY;
    }

    public void setSTARTSALARY(int STARTSALARY) {
        this.STARTSALARY = STARTSALARY;
    }
}

