package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/14.
 */

//http://web.youli.pw:89/Json/Get_Sfz_Service.aspx?sfz=310108198004026642
//[{"ID":49,"SFZ":"310108198004026642","STAFF":2,"SERVICE_TIME":"2017-08-08T00:00:00","TYPE":2,"MARK":"哈哈",
// "STAFF_NAME":"admin","TYPE_NAME":"政策服务"}]
public class ServiceReInfo {

    private int ID;
    private String SFZ;
    private int STAFF;
    private String STAFF_NAME;//要显示的
    private String SERVICE_TIME;//要显示的
    private String TYPE_NAME;//要显示的
    private String MARK;//要显示的
    private int TYPE;

    public ServiceReInfo(String STAFF_NAME, String SERVICE_TIME, String TYPE_NAME, String MARK) {
        this.STAFF_NAME = STAFF_NAME;
        this.SERVICE_TIME = SERVICE_TIME;
        this.TYPE_NAME = TYPE_NAME;
        this.MARK = MARK;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMARK() {
        return MARK;
    }

    public void setMARK(String MARK) {
        this.MARK = MARK;
    }

    public String getSERVICE_TIME() {
        return SERVICE_TIME;
    }

    public void setSERVICE_TIME(String SERVICE_TIME) {
        this.SERVICE_TIME = SERVICE_TIME;
    }

    public String getSFZ() {
        return SFZ;
    }

    public void setSFZ(String SFZ) {
        this.SFZ = SFZ;
    }

    public int getSTAFF() {
        return STAFF;
    }

    public void setSTAFF(int STAFF) {
        this.STAFF = STAFF;
    }

    public String getSTAFF_NAME() {
        return STAFF_NAME;
    }

    public void setSTAFF_NAME(String STAFF_NAME) {
        this.STAFF_NAME = STAFF_NAME;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }
}
