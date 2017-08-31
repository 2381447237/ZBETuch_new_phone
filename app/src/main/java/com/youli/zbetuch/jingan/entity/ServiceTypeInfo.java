package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/16.
 */
//http://web.youli.pw:89/Json/Get_Service_Type.aspx
//[{"ID":1,"NAME":"职介指导","RecordCount":0},{"ID":2,"NAME":"政策服务","RecordCount":0}]

public class ServiceTypeInfo {

  private int ID;
    private String NAME;
    private int RecordCount;

    public ServiceTypeInfo(int ID, String NAME, int recordCount) {
        this.ID = ID;
        this.NAME = NAME;
        RecordCount = recordCount;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public String toString() {
        return   NAME;
    }
}
