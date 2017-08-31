package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/25.
 */


//http://web.youli.pw:89/Json/Get_Gzbs.aspx
//[{"id":1,"name":"常日班","code":"1"},{"id":2,"name":"常夜班","code":"2"},{"id":3,"name":"常早班","code":"3"},{"id":4,"name":"常中班","code":"4"},{"id":5,"name":"三班制","code":"5"},{"id":6,"name":"两头班","code":"6"},{"id":7,"name":"两两制","code":"7"},{"id":8,"name":"早中班","code":"8"},{"id":9,"name":"中晚班","code":"9"},{"id":10,"name":"隔天班","code":"10"}]
public class GzbsInfo {

    private int id;
    private String name;
    private String code;

    public GzbsInfo(String code, int id, String name) {
        this.code = code;
        this.id = id;
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
        return name;
    }
}
