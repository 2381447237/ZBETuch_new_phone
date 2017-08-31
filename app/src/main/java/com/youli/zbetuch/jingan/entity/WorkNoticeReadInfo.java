package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/30.
 */

//http://web.youli.pw:89/Json/Get_Work_Notice_Read.aspx
//[{"VALUE1":null,"VALUE2":null,"RATE":null}]
public class WorkNoticeReadInfo {

    private String VALUE1;
    private String VALUE2;
    private String RATE;

    public String getRATE() {
        return RATE;
    }

    public void setRATE(String RATE) {
        this.RATE = RATE;
    }

    public String getVALUE1() {
        return VALUE1;
    }

    public void setVALUE1(String VALUE1) {
        this.VALUE1 = VALUE1;
    }

    public String getVALUE2() {
        return VALUE2;
    }

    public void setVALUE2(String VALUE2) {
        this.VALUE2 = VALUE2;
    }
}
