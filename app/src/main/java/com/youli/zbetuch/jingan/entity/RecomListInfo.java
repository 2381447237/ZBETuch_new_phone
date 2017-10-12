package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/11.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 *
 * http://web.youli.pw:89/Json/Get_JobFairRecommend.aspx?rows=15&master_id=1&page=0&code=158286114
 *
 *
 * [{"ID":8,"SFZ":"310112197904256419","JOB_CODE":"158286114","MASTER_ID":1,
 * "CREATE_STAFF":2,"CREATE_DATE":"2017-03-24T09:44:01.357","RecordCount":0}]
 */

public class RecomListInfo {

    private String no;//编号
    private int ID;
    private String SFZ;//身份证
    private String JOB_CODE;//岗位编号
    private int MASTER_ID;//招聘会编号
    private int CREATE_STAFF;
    private String CREATE_DATE;//创建时间
    private int RecordCount;

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

    public String getJOB_CODE() {
        return JOB_CODE;
    }

    public void setJOB_CODE(String JOB_CODE) {
        this.JOB_CODE = JOB_CODE;
    }

    public int getMASTER_ID() {
        return MASTER_ID;
    }

    public void setMASTER_ID(int MASTER_ID) {
        this.MASTER_ID = MASTER_ID;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public String getSFZ() {
        return SFZ;
    }

    public void setSFZ(String SFZ) {
        this.SFZ = SFZ;
    }
}
