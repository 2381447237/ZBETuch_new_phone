package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/10.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/GetJobFairDetail.aspx?master_id=1&page=0&rows=15
 *
 *
 * [{"jobid":34,"comname":"上海逸摩咖啡有限公司",
 * "jobname":"逸摩咖啡门店店员","jobno":"158286114","eduname":"高中/中专/技校","startage":18,"endage":30,"recruitnums":1,
 * "modifydate":"2013-07-09T00:00:00","startsalary":2100.00,
 * "endsalary":2200.00,"max_row":5}]
 */

public class InterviewDetailsInfo {

    private int jobid;
    private String comname;//单位名称
    private String jobname;// 岗位名称
    private String jobno;
    private String eduname;//学历
    private int startage;//年龄
    private int endage;//年龄
    private int recruitnums;//招聘人数
    private String modifydate;
    private float startsalary;//月薪
    private float endsalary;//月薪
    private int max_row;


    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname;
    }

    public String getEduname() {
        return eduname;
    }

    public void setEduname(String eduname) {
        this.eduname = eduname;
    }

    public int getEndage() {
        return endage;
    }

    public void setEndage(int endage) {
        this.endage = endage;
    }

    public float getEndsalary() {
        return endsalary;
    }

    public void setEndsalary(float endsalary) {
        this.endsalary = endsalary;
    }

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getJobno() {
        return jobno;
    }

    public void setJobno(String jobno) {
        this.jobno = jobno;
    }

    public int getMax_row() {
        return max_row;
    }

    public void setMax_row(int max_row) {
        this.max_row = max_row;
    }

    public String getModifydate() {
        return modifydate;
    }

    public void setModifydate(String modifydate) {
        this.modifydate = modifydate;
    }

    public int getRecruitnums() {
        return recruitnums;
    }

    public void setRecruitnums(int recruitnums) {
        this.recruitnums = recruitnums;
    }

    public int getStartage() {
        return startage;
    }

    public void setStartage(int startage) {
        this.startage = startage;
    }

    public float getStartsalary() {
        return startsalary;
    }

    public void setStartsalary(float startsalary) {
        this.startsalary = startsalary;
    }
}
