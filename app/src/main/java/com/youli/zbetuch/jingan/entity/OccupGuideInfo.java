package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/18.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 职业指导
 *http://web.youli.pw:89/Json/Get_JobGuide.aspx?master_id=100&page=0&rows=15
 *
 * [{"ID":53,"MASTER_ID":100,"CHECK_GUIDE":true,"GUIDE_DATE":"2017-10-16T00:00:00",
 * "GUIDE_NAME":"fff","GUIDE_DOC":"ffff","CHECK_RECOMMEND":true,"RECOMMEND_DATE":"2017-10-16T00:00:00",
 * "RECOMMEND_COM":"ddd","RECOMMEND_JOB":"ggg","CREATE_DATE":"2017-10-16T15:22:25.25","CREAT_STAFF":2,
 * "UPDATE_DATE":"2017-10-16T15:22:25.253","UPDATE_STAFF":2,"RecordCount":2},{"ID":36,"MASTER_ID":100,"CHECK_GUIDE":true,"GUIDE_DATE":"2014-03-17T00:00:00","GUIDE_NAME":"Ggg","GUIDE_DOC":"Gvhh","CHECK_RECOMMEND":false,"RECOMMEND_DATE":"2014-03-17T16:47:17","RECOMMEND_COM":"","RECOMMEND_JOB":"","CREATE_DATE":"2014-03-17T16:46:28.83","CREAT_STAFF":2,"UPDATE_DATE":"2014-03-17T16:46:28.83","UPDATE_STAFF":2,"RecordCount":2}]
 */

public class OccupGuideInfo {

    private int ID;
    private int MASTER_ID;

    private boolean CHECK_GUIDE;//是否职业指导
    private String GUIDE_DATE;//职业指导日期
    private String GUIDE_NAME;//职业指导员
    private String GUIDE_DOC;//职业指导内容

    private boolean CHECK_RECOMMEND;//是否推荐就业岗位
    private String RECOMMEND_DATE;//推荐面试时间
    private String RECOMMEND_COM;//推荐面试单位名称
    private String RECOMMEND_JOB;//推荐面试岗位名称

    private String CREATE_DATE;
    private int CREAT_STAFF;
    private String UPDATE_DATE;
    private int UPDATE_STAFF;
    private int RecordCount;


    public boolean isCHECK_GUIDE() {
        return CHECK_GUIDE;
    }

    public void setCHECK_GUIDE(boolean CHECK_GUIDE) {
        this.CHECK_GUIDE = CHECK_GUIDE;
    }

    public boolean isCHECK_RECOMMEND() {
        return CHECK_RECOMMEND;
    }

    public void setCHECK_RECOMMEND(boolean CHECK_RECOMMEND) {
        this.CHECK_RECOMMEND = CHECK_RECOMMEND;
    }

    public int getCREAT_STAFF() {
        return CREAT_STAFF;
    }

    public void setCREAT_STAFF(int CREAT_STAFF) {
        this.CREAT_STAFF = CREAT_STAFF;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getGUIDE_DATE() {
        return GUIDE_DATE;
    }

    public void setGUIDE_DATE(String GUIDE_DATE) {
        this.GUIDE_DATE = GUIDE_DATE;
    }

    public String getGUIDE_DOC() {
        return GUIDE_DOC;
    }

    public void setGUIDE_DOC(String GUIDE_DOC) {
        this.GUIDE_DOC = GUIDE_DOC;
    }

    public String getGUIDE_NAME() {
        return GUIDE_NAME;
    }

    public void setGUIDE_NAME(String GUIDE_NAME) {
        this.GUIDE_NAME = GUIDE_NAME;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMASTER_ID() {
        return MASTER_ID;
    }

    public void setMASTER_ID(int MASTER_ID) {
        this.MASTER_ID = MASTER_ID;
    }

    public String getRECOMMEND_COM() {
        return RECOMMEND_COM;
    }

    public void setRECOMMEND_COM(String RECOMMEND_COM) {
        this.RECOMMEND_COM = RECOMMEND_COM;
    }

    public String getRECOMMEND_DATE() {
        return RECOMMEND_DATE;
    }

    public void setRECOMMEND_DATE(String RECOMMEND_DATE) {
        this.RECOMMEND_DATE = RECOMMEND_DATE;
    }

    public String getRECOMMEND_JOB() {
        return RECOMMEND_JOB;
    }

    public void setRECOMMEND_JOB(String RECOMMEND_JOB) {
        this.RECOMMEND_JOB = RECOMMEND_JOB;
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
