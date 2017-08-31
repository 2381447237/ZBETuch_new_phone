package com.youli.zbetuch.jingan.bean.addressBean;

import com.google.gson.annotations.SerializedName;

public class CommitteeInfo {
    @SerializedName("ID")
    public int id;
    @SerializedName("NAME")
    public String committeeName;
    @SerializedName("NAME2")
    public String committeeName2;
    @SerializedName("STREETID")
    public String streetId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getCommitteeName2() {
        return committeeName2;
    }

    public void setCommitteeName2(String committeeName2) {
        this.committeeName2 = committeeName2;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }
}
