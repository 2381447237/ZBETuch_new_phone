package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/29.
 */


//http://web.youli.pw:89/Json/Get_Career_Counselor.aspx?JobNo=158302239
//[{"id":1,"name":"上海市职业介绍闸北分中心","address":"沪太路707号闸北区职业介绍所",
// "phone":"56089900","zip":"200072","staff":2}]
public class CounselorInfo {

    private int id;
    private String name;
    private String address;
    private String phone;
    private String zip;
    private int staff;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStaff() {
        return staff;
    }

    public void setStaff(int staff) {
        this.staff = staff;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
