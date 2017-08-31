package com.youli.zbetuch.jingan.bean.addressBean;


import com.google.gson.annotations.SerializedName;

public class StreetInfo {

    @SerializedName("ID")
    private String id;
    @SerializedName("IID")
    private int iid;
    @SerializedName("NAME")
    private String streetName;
    @SerializedName("NAME2")
    private String streetName2;
    @SerializedName("REGIONID")
    private String regionId;
    @SerializedName("RecordCount")
    private String recordCount;

    public String getStreetName2() {
        return streetName2;
    }

    public void setStreetName2(String streetName2) {
        this.streetName2 = streetName2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }
}
