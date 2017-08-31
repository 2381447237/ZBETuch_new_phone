package com.youli.zbetuch.jingan.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liutao on 2017/8/9.
 */

public class StreetInfo {

    private String streetId;
    private String streetName;

    public StreetInfo() {

    }

    public StreetInfo(String streetId, String streetName) {
        this.streetId = streetId;
        this.streetName = streetName;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public List<StreetInfo> getSteetList(){

        List<StreetInfo> streetList=new ArrayList<>();

        streetList.add(new StreetInfo("0", "请选择"));
        streetList.add(new StreetInfo("8001", "天目西路街"));
        streetList.add(new StreetInfo("8006", "北站街道"));
        streetList.add(new StreetInfo("8007", "宝山路街道"));
        streetList.add(new StreetInfo("8012", "共和新路街道"));
        streetList.add(new StreetInfo("8013", "大宁路街道"));
        streetList.add(new StreetInfo("8014", "彭浦新村街道"));
        streetList.add(new StreetInfo("8015", "临汾路街道"));
        streetList.add(new StreetInfo("8016", "芷江西路街道"));
        streetList.add(new StreetInfo("8101", "彭浦镇街道"));

        return streetList;
    }
   @Override
    public String toString() {
        return streetName;
    }


}
