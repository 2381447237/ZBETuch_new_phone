package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/25.
 */


//http://web.youli.pw:89/Json/Get_CompanyProperty.aspx

//[{"compropertyid":1,"compropertyname":"国有"}
// ,{"compropertyid":2,"compropertyname":"机关事业"},{"compropertyid":3,"compropertyname":"城镇集体"},{"compropertyid":4,"compropertyname":"股份制"},{"compropertyid":5,"compropertyname":"外商投资"},{"compropertyid":6,"compropertyname":"港澳台"},{"compropertyid":7,"compropertyname":"私营"},{"compropertyid":8,"compropertyname":"个体"},{"compropertyid":9,"compropertyname":"非正规"},{"compropertyid":10,"compropertyname":"其他"},{"compropertyid":11,"compropertyname":"有限责任"}]
public class CompanyPropertyInfo {

   private int compropertyid;
    private String compropertyname;

    public CompanyPropertyInfo(int compropertyid, String compropertyname) {
        this.compropertyid = compropertyid;
        this.compropertyname = compropertyname;
    }

    public int getCompropertyid() {
        return compropertyid;
    }

    public void setCompropertyid(int compropertyid) {
        this.compropertyid = compropertyid;
    }

    public String getCompropertyname() {
        return compropertyname;
    }

    public void setCompropertyname(String compropertyname) {
        this.compropertyname = compropertyname;
    }

    @Override
    public String toString() {
        return compropertyname ;
    }
}
