package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * Created by liutao on 2017/8/8.
 */
//http://web.youli.pw:89/Json/GetResourceSurvey.aspx?page=0&rows=15
//[{"ID":2,"TYPE":"无业","SET_TIME":"2014-08-22T00:00:00","CREATE_STAFF":2,
// "CREATE_DATE":"2014-08-21T14:31:22.91","MASTER_ID":2,"XUCHA":2550,"YICHA":24,"NAME":"admin"}

// ,{"ID":1,"TYPE":"失业","SET_TIME":"2014-08-21T00:00:00","CREATE_STAFF":2,
// "CREATE_DATE":"2014-08-21T12:03:35.973","MASTER_ID":1,"XUCHA":2427,"YICHA":10,"NAME":"admin"}]
public class ResourcesInfo implements Serializable{

   private int ID;//要显示的
    private String TYPE;//要显示的
    private String NAME;//要显示的
    private String SET_TIME;//要显示的
    private int XUCHA;//要显示的
    private int YICHA;//要显示的

    private int CREATE_STAFF;
    private String CREATE_DATE;
    private int MASTER_ID;

    public ResourcesInfo(int ID, String NAME, String SET_TIME, String TYPE, int XUCHA, int YICHA) {
        this.ID = ID;
        this.NAME = NAME;
        this.SET_TIME = SET_TIME;
        this.TYPE = TYPE;
        this.XUCHA = XUCHA;
        this.YICHA = YICHA;
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

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSET_TIME() {
        return SET_TIME;
    }

    public void setSET_TIME(String SET_TIME) {
        this.SET_TIME = SET_TIME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public int getXUCHA() {
        return XUCHA;
    }

    public void setXUCHA(int XUCHA) {
        this.XUCHA = XUCHA;
    }

    public int getYICHA() {
        return YICHA;
    }

    public void setYICHA(int YICHA) {
        this.YICHA = YICHA;
    }

    @Override
    public String toString() {
        return "ResourcesInfo{" +
                "CREATE_DATE='" + CREATE_DATE + '\'' +
                ", ID=" + ID +
                ", TYPE='" + TYPE + '\'' +
                ", SET_TIME='" + SET_TIME + '\'' +
                ", CREATE_STAFF=" + CREATE_STAFF +
                ", MASTER_ID=" + MASTER_ID +
                ", XUCHA=" + XUCHA +
                ", YICHA=" + YICHA +
                ", NAME='" + NAME + '\'' +
                '}';
    }
}
