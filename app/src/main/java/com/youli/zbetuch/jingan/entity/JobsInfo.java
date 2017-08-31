package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/4.
 */
//岗位信息
//http://web.youli.pw:89/Json/GetJobs.aspx?page=0&rows=3
//[{"jobid":2,"comname":"上海联家超市有限公司大场店","jobname":"生鲜部、蔬果部员工","jobno":"158296291",
// "eduname":"高中/中专/技校","startage":18,"endage":40,"recruitnums":2,"modifydate":"2017-02-14T00:00:00",
// "startsalary":2000.00,"endsalary":2200.00,"max_row":273}]
public class JobsInfo {

    private int jobid;
    private String comname;
    private String jobname;//显示这个
    private String jobno;
    private String eduname;
    private int startage;
    private int endage;
    private int recruitnums;
    private String modifydate;//显示这个
    private float startsalary;
    private float endsalary;
    private int max_row;

    public JobsInfo(String jobname, String modifydate) {
        this.jobname = jobname;
        this.modifydate = modifydate;
    }

    public float getStartsalary() {
        return startsalary;
    }

    public void setStartsalary(float startsalary) {
        this.startsalary = startsalary;
    }

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

    @Override
    public String toString() {
        return "JobsInfo{" +
                "comname='" + comname + '\'' +
                ", jobid=" + jobid +
                ", jobname='" + jobname + '\'' +
                ", jobno='" + jobno + '\'' +
                ", eduname='" + eduname + '\'' +
                ", startage=" + startage +
                ", endage=" + endage +
                ", recruitnums=" + recruitnums +
                ", modifydate='" + modifydate + '\'' +
                ", startsalary=" + startsalary +
                ", endsalary=" + endsalary +
                ", max_row=" + max_row +
                '}';
    }
}
