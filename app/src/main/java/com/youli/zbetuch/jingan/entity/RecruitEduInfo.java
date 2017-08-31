package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/25.
 */


//http://web.youli.pw:89/Json/Get_Edu.aspx

//[{"id":1,"edu_name":"硕士研究生及以上学历"},{"id":2,"edu_name":"大学本科"},{"id":3,"edu_name":"大学专科/高职"},{"id":4,"edu_name":"高中/中专/技校"},{"id":5,"edu_name":"初中或以下"}]
public class RecruitEduInfo {

    private int id;
    private String edu_name;

    public RecruitEduInfo(String edu_name, int id) {
        this.edu_name = edu_name;
        this.id = id;
    }

    public String getEdu_name() {
        return edu_name;
    }

    public void setEdu_name(String edu_name) {
        this.edu_name = edu_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  edu_name ;
    }
}
