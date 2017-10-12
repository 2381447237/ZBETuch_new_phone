package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/9.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class GraduateInfo {

    private String noStr;
    private String nameStr;
    private String sexStr;
    private String idCardStr;
    private String phoneStr;

    public GraduateInfo(String idCardStr, String nameStr, String noStr, String phoneStr, String sexStr) {
        this.idCardStr = idCardStr;
        this.nameStr = nameStr;
        this.noStr = noStr;
        this.phoneStr = phoneStr;
        this.sexStr = sexStr;
    }

    public String getIdCardStr() {
        return idCardStr;
    }

    public void setIdCardStr(String idCardStr) {
        this.idCardStr = idCardStr;
    }

    public String getNameStr() {
        return nameStr;
    }

    public void setNameStr(String nameStr) {
        this.nameStr = nameStr;
    }

    public String getNoStr() {
        return noStr;
    }

    public void setNoStr(String noStr) {
        this.noStr = noStr;
    }

    public String getPhoneStr() {
        return phoneStr;
    }

    public void setPhoneStr(String phoneStr) {
        this.phoneStr = phoneStr;
    }

    public String getSexStr() {
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }
}
