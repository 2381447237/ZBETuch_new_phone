package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/17.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/Get_Area.aspx?COMMITTEE=6013
 *
 * [{"ID":258,"NAME":"海园居委","STREETID":"6013","NAME2":"海园居委,海园居委"}]
 */

public class JwInfo {

    private int ID;
    private String NAME;
    private String STREETID;
    private String NAME2;

    public JwInfo(int ID, String NAME) {
        this.ID = ID;
        this.NAME = NAME;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNAME2() {
        return NAME2;
    }

    public void setNAME2(String NAME2) {
        this.NAME2 = NAME2;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSTREETID() {
        return STREETID;
    }

    public void setSTREETID(String STREETID) {
        this.STREETID = STREETID;
    }

    @Override
    public String toString() {
        return  NAME;
    }
}
