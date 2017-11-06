package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/15.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/Get_Staff_Login.aspx?rows=15&page=0
 *
 * [{"ID":10915,"STAFF":2,"LOGIN_TIME":"2017-10-15T18:50:22.74","NAME":"admin","RecordCount":10027}]
 */

public class LoginInfo {

    private int ID;
    private int STAFF;
    private String NAME;
    private String LOGIN_TIME;
    private int RecordCount;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLOGIN_TIME() {
        return LOGIN_TIME;
    }

    public void setLOGIN_TIME(String LOGIN_TIME) {
        this.LOGIN_TIME = LOGIN_TIME;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public int getSTAFF() {
        return STAFF;
    }

    public void setSTAFF(int STAFF) {
        this.STAFF = STAFF;
    }
}
