package com.youli.zbetuch.jingan.bean.addressBean;


import com.google.gson.annotations.SerializedName;

public class RegionInfo {
    @SerializedName("CITYID")
    private String cityId;
    @SerializedName("ID")
    private int regionId;
    @SerializedName("NAME")
    private String name;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
