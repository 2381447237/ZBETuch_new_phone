package com.youli.zbetuch.jingan.entity;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by liutao on 2017/8/22.
 */
//http://web.youli.pw:89/Json/Get_Tb_Mark.aspx?sfz=310108198004026642
//[{"ID":79,"SFZ":"310108198004026642","MARK":"应届毕业生","CREATE_DATE":"2017-08-18T10:49:22.363","SOURCE":"现场采集",
// "GPS":null}]
public class MarkImgInfo {

    private int ID;
    private String SFZ;
    private String MARK;
    private String CREATE_DATE;
    private String SOURCE;
    private String GPS;

    public MarkImgInfo(){}

    public MarkImgInfo(String MARK, int ID,String CREATE_DATE, String GPS,  String SFZ, String SOURCE) {
        this.CREATE_DATE = CREATE_DATE;
        this.GPS = GPS;
        this.ID = ID;
        this.MARK = MARK;
        this.SFZ = SFZ;
        this.SOURCE = SOURCE;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getGPS() {
        return GPS;
    }

    public void setGPS(String GPS) {
        this.GPS = GPS;
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

    public String getSFZ() {
        return SFZ;
    }

    public void setSFZ(String SFZ) {
        this.SFZ = SFZ;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    @Override
    public String toString() {
        return "MarkImgInfo{" +
                "CREATE_DATE='" + CREATE_DATE + '\'' +
                ", ID=" + ID +
                ", SFZ='" + SFZ + '\'' +
                ", MARK='" + MARK + '\'' +
                ", SOURCE='" + SOURCE + '\'' +
                ", GPS='" + GPS + '\'' +
                '}';
    }
}
