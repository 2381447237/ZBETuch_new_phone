package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/18.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 调查走访情况
 *
 * http://web.youli.pw:89/Json/Get_JobMarks.aspx?master_id=920&page=0&rows=15
 *
 * [{"ID":41,"MASTER_ID":920,"VISIT_DATE":"2014-03-17T00:00:00",
 * "BASE_SITUATION":"已就业","DETAIL_SITUATION1":"青年职业创业见习","DETAIL_SITUATION2":"",
 * "DETAIL_COMPANY":"Vv","CREAT_DATE":"2014-03-17T16:50:19.38","CREAT_STAFF":2,"UPDATE_DATE":"2014-03-17T16:50:19.38",
 * "UPDATE_STAFF":2,"INQUIRER":"admin","REMARK":"","RecordCount":1}]
 */

public class InvestInfo {

    private int ID;
    private int MASTER_ID;
    private String VISIT_DATE;//调查走访日期
    private String BASE_SITUATION;//人员基本情况
    private String DETAIL_SITUATION1;//人员具体情况
    private String DETAIL_SITUATION2;
    private String DETAIL_COMPANY;//单位
    private String CREAT_DATE;
    private int CREAT_STAFF;
    private String UPDATE_DATE;
    private int UPDATE_STAFF;
    private String INQUIRER;//调查人
    private String REMARK;//备注
    private int RecordCount;

    public String getBASE_SITUATION() {
        return BASE_SITUATION;
    }

    public void setBASE_SITUATION(String BASE_SITUATION) {
        this.BASE_SITUATION = BASE_SITUATION;
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

    public String getDETAIL_COMPANY() {
        return DETAIL_COMPANY;
    }

    public void setDETAIL_COMPANY(String DETAIL_COMPANY) {
        this.DETAIL_COMPANY = DETAIL_COMPANY;
    }

    public String getDETAIL_SITUATION1() {
        return DETAIL_SITUATION1;
    }

    public void setDETAIL_SITUATION1(String DETAIL_SITUATION1) {
        this.DETAIL_SITUATION1 = DETAIL_SITUATION1;
    }

    public String getDETAIL_SITUATION2() {
        return DETAIL_SITUATION2;
    }

    public void setDETAIL_SITUATION2(String DETAIL_SITUATION2) {
        this.DETAIL_SITUATION2 = DETAIL_SITUATION2;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getINQUIRER() {
        return INQUIRER;
    }

    public void setINQUIRER(String INQUIRER) {
        this.INQUIRER = INQUIRER;
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

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
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

    public String getVISIT_DATE() {
        return VISIT_DATE;
    }

    public void setVISIT_DATE(String VISIT_DATE) {
        this.VISIT_DATE = VISIT_DATE;
    }
}
