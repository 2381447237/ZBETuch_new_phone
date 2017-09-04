package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/9/1.
 */
//http://web.youli.pw:89/Json/Get_Work_Notice_File.aspx?master_id=2
    //http://web.youli.pw:89/Json/Get_Meeting_File.aspx?master_id=4
    //[{"ID":2,"FILE_PATH":"upload/Manage/9e0d2ccc-ad8a-496d-ba61-78e0c181c489_NimoScanDemo.jpg",
// "FILE_NAME":"NimoScan Demo.jpg","MASTER_ID":2,"RecordCount":0,"type":0}]
public class AppendixInfo {

    private int ID;
    private String FILE_PATH;
    private String FILE_NAME;
    private int MASTER_ID;
    private int RecordCount;
    private int type;

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getFILE_PATH() {
        return FILE_PATH;
    }

    public void setFILE_PATH(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
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

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
