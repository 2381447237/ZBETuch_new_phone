package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * 作者: zhengbin on 2017/10/20.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class GraQueryInfo implements Serializable{

    private String STREET_ID;//街道
    private String COMMITTEE_ID;//居委
    private String SEX;//性别
    private String START_AGE;//年龄（小）
    private String END_AGE;//年龄（大）
    private String EDU;//文化程度
    private String MARK;//特殊情况
    private String COMPROPERTY_ID;//企业类型
    private String INDUSTARY_CATEGORY_ID;//行业类别
    private String JOB_CATEGORY;//岗位类别
    private String SALARY;//税后薪资
    private String BASE_SITUATION;//人员基本情况
    private String DETAIL_SITUATION;//人员具体情况
    private String CHECK_GUIDE;//是否职业指导
    private String CHECK_RECOMMEND;//是否推荐就业岗位


    public String getBASE_SITUATION() {
        return BASE_SITUATION;
    }

    public void setBASE_SITUATION(String BASE_SITUATION) {
        this.BASE_SITUATION = BASE_SITUATION;
    }

    public String getCHECK_GUIDE() {
        return CHECK_GUIDE;
    }

    public void setCHECK_GUIDE(String CHECK_GUIDE) {
        this.CHECK_GUIDE = CHECK_GUIDE;
    }

    public String getCHECK_RECOMMEND() {
        return CHECK_RECOMMEND;
    }

    public void setCHECK_RECOMMEND(String CHECK_RECOMMEND) {
        this.CHECK_RECOMMEND = CHECK_RECOMMEND;
    }

    public String getCOMMITTEE_ID() {
        return COMMITTEE_ID;
    }

    public void setCOMMITTEE_ID(String COMMITTEE_ID) {
        this.COMMITTEE_ID = COMMITTEE_ID;
    }

    public String getCOMPROPERTY_ID() {
        return COMPROPERTY_ID;
    }

    public void setCOMPROPERTY_ID(String COMPROPERTY_ID) {
        this.COMPROPERTY_ID = COMPROPERTY_ID;
    }

    public String getDETAIL_SITUATION() {
        return DETAIL_SITUATION;
    }

    public void setDETAIL_SITUATION(String DETAIL_SITUATION) {
        this.DETAIL_SITUATION = DETAIL_SITUATION;
    }

    public String getEDU() {
        return EDU;
    }

    public void setEDU(String EDU) {
        this.EDU = EDU;
    }

    public String getEND_AGE() {
        return END_AGE;
    }

    public void setEND_AGE(String END_AGE) {
        this.END_AGE = END_AGE;
    }

    public String getINDUSTARY_CATEGORY_ID() {
        return INDUSTARY_CATEGORY_ID;
    }

    public void setINDUSTARY_CATEGORY_ID(String INDUSTARY_CATEGORY_ID) {
        this.INDUSTARY_CATEGORY_ID = INDUSTARY_CATEGORY_ID;
    }

    public String getJOB_CATEGORY() {
        return JOB_CATEGORY;
    }

    public void setJOB_CATEGORY(String JOB_CATEGORY) {
        this.JOB_CATEGORY = JOB_CATEGORY;
    }

    public String getMARK() {
        return MARK;
    }

    public void setMARK(String MARK) {
        this.MARK = MARK;
    }

    public String getSALARY() {
        return SALARY;
    }

    public void setSALARY(String SALARY) {
        this.SALARY = SALARY;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getSTART_AGE() {
        return START_AGE;
    }

    public void setSTART_AGE(String START_AGE) {
        this.START_AGE = START_AGE;
    }

    public String getSTREET_ID() {
        return STREET_ID;
    }

    public void setSTREET_ID(String STREET_ID) {
        this.STREET_ID = STREET_ID;
    }

    @Override
    public String toString() {
        return "GraQueryInfo{" +
                "END_AGE='" + END_AGE + '\'' +
                ", START_AGE='" + START_AGE + '\'' +
                '}';
    }
}
