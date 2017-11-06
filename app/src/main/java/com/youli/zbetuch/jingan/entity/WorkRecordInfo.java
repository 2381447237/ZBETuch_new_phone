package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * 作者: zhengbin on 2017/10/9.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/Get_WorkToDate.aspx?rows=15&page=0
 * [{"ID":27,"TITLE":"234","DOC":"234","CREATE_STAFF":2,"CREATE_DATE":"2017-04-01T14:14:00.807","PIC":null,"RecordCount":13}]
 */

public class WorkRecordInfo implements Serializable{

    private int ID;
   private String TITLE;//标题
    private String DOC;
    private int CREATE_STAFF;
    private String CREATE_DATE;//日期
    private String PIC;
    private int RecordCount;

    public WorkRecordInfo(String TITLE, String CREATE_DATE) {
        this.TITLE = TITLE;
        this.CREATE_DATE = CREATE_DATE;
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

    public String getDOC() {
        return DOC;
    }

    public void setDOC(String DOC) {
        this.DOC = DOC;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPIC() {
        return PIC;
    }

    public void setPIC(String PIC) {
        this.PIC = PIC;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }
}
