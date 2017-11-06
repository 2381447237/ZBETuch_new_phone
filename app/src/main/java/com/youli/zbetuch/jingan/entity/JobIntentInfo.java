package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/18.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 得到个人求职意愿的勾选情况
 http://web.youli.pw:89/Json/Get_JobIntent.aspx?master_id=100
 *
 * [{"ID":16,"MASTER_ID":100,"COMPROPERTYID1":2,"COMPROPERTYID2":5,"COMPROPERTYID3":6,
 * "INDUSTRY_CATEGORY1":"3","INDUSTRY_CATEGORY2":"11","INDUSTRY_CATEGORY3":"-1","JOB_CATEGORY1":"管理类",
 * "JOB_CATEGORY2":"","JOB_CATEGORY3":"","SALARY1":"5,000元至10,000元(含)","SALARY2":"","SALARY3":"",
 * "CREAT_DATE":"2014-03-17T16:45:53.603","CREAT_STAFF":2,"UPDATE_DATE":"2017-10-16T15:23:20.09","UPDATE_STAFF":2,
 * "RecordCount":0}]
 */

public class JobIntentInfo {

    private int ID;
    private int MASTER_ID;
    private int COMPROPERTYID1;
    private int COMPROPERTYID2;
    private int COMPROPERTYID3;
    private String INDUSTRY_CATEGORY1;
    private String INDUSTRY_CATEGORY2;
    private String INDUSTRY_CATEGORY3;
    private String JOB_CATEGORY1;
    private String JOB_CATEGORY2;
    private String JOB_CATEGORY3;
    private String SALARY1;
    private String SALARY2;
    private String SALARY3;
    private String CREAT_DATE;
    private int CREAT_STAFF;
    private String UPDATE_DATE;
    private int UPDATE_STAFF;
    private int RecordCount;

    public int getCOMPROPERTYID1() {
        return COMPROPERTYID1;
    }

    public void setCOMPROPERTYID1(int COMPROPERTYID1) {
        this.COMPROPERTYID1 = COMPROPERTYID1;
    }

    public int getCOMPROPERTYID2() {
        return COMPROPERTYID2;
    }

    public void setCOMPROPERTYID2(int COMPROPERTYID2) {
        this.COMPROPERTYID2 = COMPROPERTYID2;
    }

    public int getCOMPROPERTYID3() {
        return COMPROPERTYID3;
    }

    public void setCOMPROPERTYID3(int COMPROPERTYID3) {
        this.COMPROPERTYID3 = COMPROPERTYID3;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getINDUSTRY_CATEGORY1() {
        return INDUSTRY_CATEGORY1;
    }

    public void setINDUSTRY_CATEGORY1(String INDUSTRY_CATEGORY1) {
        this.INDUSTRY_CATEGORY1 = INDUSTRY_CATEGORY1;
    }

    public String getINDUSTRY_CATEGORY2() {
        return INDUSTRY_CATEGORY2;
    }

    public void setINDUSTRY_CATEGORY2(String INDUSTRY_CATEGORY2) {
        this.INDUSTRY_CATEGORY2 = INDUSTRY_CATEGORY2;
    }

    public String getINDUSTRY_CATEGORY3() {
        return INDUSTRY_CATEGORY3;
    }

    public void setINDUSTRY_CATEGORY3(String INDUSTRY_CATEGORY3) {
        this.INDUSTRY_CATEGORY3 = INDUSTRY_CATEGORY3;
    }

    public String getJOB_CATEGORY1() {
        return JOB_CATEGORY1;
    }

    public void setJOB_CATEGORY1(String JOB_CATEGORY1) {
        this.JOB_CATEGORY1 = JOB_CATEGORY1;
    }

    public String getJOB_CATEGORY2() {
        return JOB_CATEGORY2;
    }

    public void setJOB_CATEGORY2(String JOB_CATEGORY2) {
        this.JOB_CATEGORY2 = JOB_CATEGORY2;
    }

    public String getJOB_CATEGORY3() {
        return JOB_CATEGORY3;
    }

    public void setJOB_CATEGORY3(String JOB_CATEGORY3) {
        this.JOB_CATEGORY3 = JOB_CATEGORY3;
    }

    public int getMASTER_ID() {
        return MASTER_ID;
    }

    public void setMASTER_ID(int MASTER_ID) {
        this.MASTER_ID = MASTER_ID;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public String getSALARY1() {
        return SALARY1;
    }

    public void setSALARY1(String SALARY1) {
        this.SALARY1 = SALARY1;
    }

    public String getSALARY2() {
        return SALARY2;
    }

    public void setSALARY2(String SALARY2) {
        this.SALARY2 = SALARY2;
    }

    public String getSALARY3() {
        return SALARY3;
    }

    public void setSALARY3(String SALARY3) {
        this.SALARY3 = SALARY3;
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
