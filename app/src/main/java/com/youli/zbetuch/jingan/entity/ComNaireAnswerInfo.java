package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * 作者: zhengbin on 2017/11/24.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/Get_Qa_Company_Receiv.aspx?Personnel_id=14
 */

public class ComNaireAnswerInfo implements Serializable{


    /**
     * ID : 1
     * PERSONNEL_ID : 14
     * DETIL_ID : 2
     * INPUT_VALUE :
     * MASTER_ID : 0
     * RecordCount : 0
     */

    private int ID;
    private int PERSONNEL_ID;
    private int DETIL_ID;//选项
    private String INPUT_VALUE;//填空
    private int MASTER_ID;
    private int RecordCount;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPERSONNEL_ID() {
        return PERSONNEL_ID;
    }

    public void setPERSONNEL_ID(int PERSONNEL_ID) {
        this.PERSONNEL_ID = PERSONNEL_ID;
    }

    public int getDETIL_ID() {
        return DETIL_ID;
    }

    public void setDETIL_ID(int DETIL_ID) {
        this.DETIL_ID = DETIL_ID;
    }

    public String getINPUT_VALUE() {
        return INPUT_VALUE;
    }

    public void setINPUT_VALUE(String INPUT_VALUE) {
        this.INPUT_VALUE = INPUT_VALUE;
    }

    public int getMASTER_ID() {
        return MASTER_ID;
    }

    public void setMASTER_ID(int MASTER_ID) {
        this.MASTER_ID = MASTER_ID;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int RecordCount) {
        this.RecordCount = RecordCount;
    }

    @Override
    public String toString() {
        return "ComNaireAnswerInfo{" +
                "DETIL_ID=" + DETIL_ID +
                '}';
    }
}
