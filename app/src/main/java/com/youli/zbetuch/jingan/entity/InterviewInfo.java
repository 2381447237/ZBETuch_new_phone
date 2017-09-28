package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/9/28.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class InterviewInfo {

    private String title;//标题
    private String date;//时间
    private String companyNum;//单位数量
    private String postNum;//岗位数量
    private String address;//地址

    public InterviewInfo(String address, String companyNum, String date, String postNum, String title) {
        this.address = address;
        this.companyNum = companyNum;
        this.date = date;
        this.postNum = postNum;
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public void setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostNum() {
        return postNum;
    }

    public void setPostNum(String postNum) {
        this.postNum = postNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
