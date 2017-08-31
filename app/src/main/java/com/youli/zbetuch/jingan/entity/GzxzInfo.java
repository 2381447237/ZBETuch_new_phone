package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/25.
 */
//http://web.youli.pw:89/Json/Get_Gzxz.aspx
//[{"id":1,"name":"全日制劳动合同","code":"1"},{"id":2,"name":"事业单位聘用合同","code":"2"},{"id":3,"name":"保险代理人","code":"3"},{"id":4,"name":"见习","code":"4"}]
public class GzxzInfo {

    private int id;
    private String name;
    private String code;

    public GzxzInfo(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return  name ;
    }
}
