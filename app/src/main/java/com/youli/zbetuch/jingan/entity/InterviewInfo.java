package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * 作者: zhengbin on 2017/9/28.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/GetJobFairMaster.aspx?page=0&rows=15
 *
 *
 * [{"ID":1,"NAME":"test招聘会","JOBFAIRDATA":"2018-02-01T00:00:00","ADDRESS":"沪太所现场招聘会",
 * "CREATE_DATE":"2014-03-31T00:00:00","CREATE_STAFF":2,"UPDATE_DATE":"2014-04-02T14:21:15.83","UPDATE_STAFF":2,
 * "RecordCount":36,"Company_num":4,"Job_num":5}]
 */

public class InterviewInfo implements Serializable {

    private int ID;
    private String NAME;//标题
    private String JOBFAIRDATA;//时间
    private String ADDRESS;//地址
    private String CREATE_DATE;
    private int CREATE_STAFF;
    private String UPDATE_DATE;
    private int UPDATE_STAFF;
    private int RecordCount;
    private String Company_num;//单位数量
    private String Job_num;//岗位数量

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getCompany_num() {
        return Company_num;
    }

    public void setCompany_num(String company_num) {
        Company_num = company_num;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getJob_num() {
        return Job_num;
    }

    public void setJob_num(String job_num) {
        Job_num = job_num;
    }

    public String getJOBFAIRDATA() {
        return JOBFAIRDATA;
    }

    public void setJOBFAIRDATA(String JOBFAIRDATA) {
        this.JOBFAIRDATA = JOBFAIRDATA;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
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
