package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/10/17.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * //http://web.youli.pw:89/Json/Get_Area.aspx?STREET=310108
 //[{"ID":"6013","NAME":"静安寺街道","REGIONID":"310108","IID":214,"NAME2":"静安寺街道","RecordCount":0}]
 */

public class JinAnStreetInfo {

    private String ID;
    private String NAME;
    private String REGIONID;
    private int IID;
    private String NAME2;
    private int RecordCount;

    public JinAnStreetInfo(String ID, String NAME, String REGIONID) {
        this.ID = ID;
        this.NAME = NAME;
        this.REGIONID = REGIONID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getIID() {
        return IID;
    }

    public void setIID(int IID) {
        this.IID = IID;
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

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int recordCount) {
        RecordCount = recordCount;
    }

    public String getREGIONID() {
        return REGIONID;
    }

    public void setREGIONID(String REGIONID) {
        this.REGIONID = REGIONID;
    }

    @Override
    public String toString() {
        return NAME ;
    }
}
