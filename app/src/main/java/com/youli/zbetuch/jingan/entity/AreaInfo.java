package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/25.
 */

//http://web.youli.pw:89/Json/Get_Area_Info.aspx
    //[{"areaid":1,"areaname":"全市","areacode":"000"},{"areaid":2,"areaname":"黄浦","areacode":"001"},{"areaid":3,"areaname":"卢湾","areacode":"003"},{"areaid":4,"areaname":"徐汇","areacode":"004"},{"areaid":5,"areaname":"长宁","areacode":"005"},{"areaid":6,"areaname":"静安","areacode":"006"},{"areaid":7,"areaname":"普陀","areacode":"007"},{"areaid":8,"areaname":"闸北","areacode":"008"},{"areaid":9,"areaname":"虹口","areacode":"009"},{"areaid":10,"areaname":"杨浦","areacode":"010"},{"areaid":11,"areaname":"闵行","areacode":"012"},{"areaid":12,"areaname":"宝山","areacode":"013"},{"areaid":13,"areaname":"嘉定","areacode":"014"},{"areaid":14,"areaname":"浦东","areacode":"015"},{"areaid":15,"areaname":"金山","areacode":"016"},{"areaid":16,"areaname":"松江","areacode":"017"},{"areaid":17,"areaname":"青浦","areacode":"018"},{"areaid":18,"areaname":"南汇","areacode":"025"},{"areaid":19,"areaname":"奉贤","areacode":"026"},{"areaid":20,"areaname":"崇明","areacode":"030"}]
public class AreaInfo {

    private int areaid;
    private String areaname;
    private String areacode;

    public AreaInfo(String areacode, int areaid, String areaname) {
        this.areacode = areacode;
        this.areaid = areaid;
        this.areaname = areaname;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public int getAreaid() {
        return areaid;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    @Override
    public String toString() {
        return  areaname;
    }
}



