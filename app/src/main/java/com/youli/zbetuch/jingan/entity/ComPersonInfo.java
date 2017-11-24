package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * 作者: zhengbin on 2017/11/23.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 企业人员列表
 *
 * http://web.youli.pw:89/Json/Get_Qa_Company_Personnel.aspx?Company_id=1
 */

public class ComPersonInfo implements Serializable{


    /**
     * ID : 1
     * COMPANY_ID : 1
     * NAME : 张三
     * SFZ : 310111199909093333
     * QA_MASTER : 1
     * CREATE_DATE : 2017-11-16T00:00:00
     * CREATE_STAFF : 1
     * RECEIVED : 0
     * MARK : 备注123
     * RECEIVED_STAFF : 1
     * RECEIVED_TIME : 2017-11-16T00:00:00
     * RecordCount : 0
     */

    private int ID;//个人ID
    private int COMPANY_ID;//企业ID
    private String NAME;//姓名
    private String SFZ;//身份证
    private int QA_MASTER;
    private String CREATE_DATE;
    private int CREATE_STAFF;
    private int RECEIVED;
    private String MARK;//备注
    private int RECEIVED_STAFF;
    private String RECEIVED_TIME;
    private int RecordCount;

    @Override
    public String toString() {
        return "ComPersonInfo{" +
                "ID=" + ID +
                ", MARK='" + MARK + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(int COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSFZ() {
        return SFZ;
    }

    public void setSFZ(String SFZ) {
        this.SFZ = SFZ;
    }

    public int getQA_MASTER() {
        return QA_MASTER;
    }

    public void setQA_MASTER(int QA_MASTER) {
        this.QA_MASTER = QA_MASTER;
    }

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

    public int getRECEIVED() {
        return RECEIVED;
    }

    public void setRECEIVED(int RECEIVED) {
        this.RECEIVED = RECEIVED;
    }

    public String getMARK() {
        return MARK;
    }

    public void setMARK(String MARK) {
        this.MARK = MARK;
    }

    public int getRECEIVED_STAFF() {
        return RECEIVED_STAFF;
    }

    public void setRECEIVED_STAFF(int RECEIVED_STAFF) {
        this.RECEIVED_STAFF = RECEIVED_STAFF;
    }

    public String getRECEIVED_TIME() {
        return RECEIVED_TIME;
    }

    public void setRECEIVED_TIME(String RECEIVED_TIME) {
        this.RECEIVED_TIME = RECEIVED_TIME;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int RecordCount) {
        this.RecordCount = RecordCount;
    }



}
