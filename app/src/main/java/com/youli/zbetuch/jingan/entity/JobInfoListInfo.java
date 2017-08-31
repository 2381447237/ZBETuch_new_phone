package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * Created by liutao on 2017/8/23.
 */

//http://web.youli.pw:89/Json/GetJobs_Search.aspx?PageRecCnts=15&ZyflId=-1&Age=-1&GZXZId=-1&ZyflChildId=-1&JobName=&IsDisabledPerson=false&IsDirectInterview=false&ComPropertyId=-1&JobNo=&ModifyStartDate=2010-01-01&IsAssurance=false&EndSalary=0&IndustryClassChildId=-1&ComName=&GZBSId=-1&EduID=-1&AreaId3=-1&AreaId2=-1&AreaId1=-1&ModifyEndDate=2030-01-01&StartSalary=0&IndustryClassId=-1&IsNewGraduates=false&PageIndex=0

//[{"max_row":273,"comname":"上海联家超市有限公司大场店","compropertyname":"外商投资","jobid":2,
// "jobno":"158296291","jobname":"生鲜部、蔬果部员工","eduname":"高中/中专/技校","startage":18,"endage":40,
// "recruitnums":2,"startsalary":2000.00,"endsalary":2200.00,"modifydate":"2017-02-14T00:00:00"}]

public class JobInfoListInfo implements Serializable{

    private int max_row;
    private String comname;//单位名称
    private String compropertyname;
    private int jobid;
    private String jobno;//岗位编码
    private String jobname;//岗位名称
    private String eduname;//学历
    private int startage;//年龄下限
    private int endage;//年龄上限
    private int recruitnums;//招聘人数
    private float startsalary;//月薪下限
    private float endsalary;//月薪上限
    private String modifydate;//更新时间


    public JobInfoListInfo(){}

    public JobInfoListInfo(String comname, String jobname, String jobno, String eduname, int startage, int endage, int recruitnums, float startsalary, float endsalary, String modifydate) {
        this.comname = comname;
        this.jobname = jobname;
        this.jobno = jobno;
        this.eduname = eduname;
        this.startage = startage;
        this.endage = endage;
        this.recruitnums = recruitnums;
        this.startsalary = startsalary;
        this.endsalary = endsalary;
        this.modifydate = modifydate;
    }

    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname;
    }

    public String getCompropertyname() {
        return compropertyname;
    }

    public void setCompropertyname(String compropertyname) {
        this.compropertyname = compropertyname;
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

    @Override
    public String toString() {
        return
              comname ;
    }
}
