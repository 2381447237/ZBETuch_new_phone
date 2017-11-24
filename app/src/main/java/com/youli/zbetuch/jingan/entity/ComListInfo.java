package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * 作者: zhengbin on 2017/11/22.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 企业调查里面的企业列表
 *
 * http://web.youli.pw:89/Json/Get_Qa_Company.aspx?master_id=1&page=0&rows=15
 */

public class ComListInfo implements Serializable{


    /**
     * ID : 1
     * MASTER_ID : 1
     * COMPANY_NAME : 测试单位11111
     * SSHY : 文创
     * YGZS : 11
     * GGRS : 2
     * ZCRS : 3
     * CREATE_STAFF : 2
     * CREATE_TIME : 2017-11-15T22:58:49.323
     * UPDATE_STAFF : 2
     * UPDATE_TIME : 2017-11-15T22:58:49.323
     * COMPANY_NO : null
     * RecordCount : 0
     * WJS : 1
     */

    private int ID;
    private int MASTER_ID;
    private String COMPANY_NAME;//企业名称
    private String SSHY;//所属行业
    private int YGZS;//员工总数
    private int GGRS;//高管人数
    private int ZCRS;//中层人数
    private int CREATE_STAFF;
    private String CREATE_TIME;
    private int UPDATE_STAFF;
    private String UPDATE_TIME;
    private String COMPANY_NO;//组织机构代码
    private int RecordCount;
    private int WJS;//问卷数

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

    public String getCOMPANY_NAME() {
        return COMPANY_NAME;
    }

    public void setCOMPANY_NAME(String COMPANY_NAME) {
        this.COMPANY_NAME = COMPANY_NAME;
    }

    public String getSSHY() {
        return SSHY;
    }

    public void setSSHY(String SSHY) {
        this.SSHY = SSHY;
    }

    public int getYGZS() {
        return YGZS;
    }

    public void setYGZS(int YGZS) {
        this.YGZS = YGZS;
    }

    public int getGGRS() {
        return GGRS;
    }

    public void setGGRS(int GGRS) {
        this.GGRS = GGRS;
    }

    public int getZCRS() {
        return ZCRS;
    }

    public void setZCRS(int ZCRS) {
        this.ZCRS = ZCRS;
    }

    public int getCREATE_STAFF() {
        return CREATE_STAFF;
    }

    public void setCREATE_STAFF(int CREATE_STAFF) {
        this.CREATE_STAFF = CREATE_STAFF;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public int getUPDATE_STAFF() {
        return UPDATE_STAFF;
    }

    public void setUPDATE_STAFF(int UPDATE_STAFF) {
        this.UPDATE_STAFF = UPDATE_STAFF;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public String getCOMPANY_NO() {
        return COMPANY_NO;
    }

    public void setCOMPANY_NO(String COMPANY_NO) {
        this.COMPANY_NO = COMPANY_NO;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int RecordCount) {
        this.RecordCount = RecordCount;
    }

    public int getWJS() {
        return WJS;
    }

    public void setWJS(int WJS) {
        this.WJS = WJS;
    }
}
