package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/25.
 */
//http://web.youli.pw:89/Json/Get_Industry_Class.aspx
    //http://web.youli.pw:89/Json/Get_Industry_Class_Child.aspx?parent_code=100(code)
    //[{"id":1,"name":"农、林、牧、渔业","code":"100"},{"id":2,"name":"采矿业","code":"200"},{"id":3,"name":"制造业","code":"300"},{"id":4,"name":"电力、燃气及水的生产和供应业","code":"400"},{"id":5,"name":"建筑业","code":"500"},{"id":6,"name":"交通运输、仓储和邮政业","code":"600"},{"id":7,"name":"信息传输、计算机服务和软件业","code":"700"},{"id":8,"name":"批发和零售业","code":"800"},{"id":9,"name":"住宿和餐饮业","code":"900"},{"id":10,"name":"金融业","code":"1000"},{"id":11,"name":"房地产业","code":"1100"},{"id":12,"name":"租赁和商务服务业","code":"1200"},{"id":13,"name":"科学研究、技术服务和地质勘查业","code":"1300"},{"id":14,"name":"水利、环境和公共设施管理业","code":"1400"},{"id":15,"name":"居民服务和其他服务业","code":"1500"},{"id":16,"name":"教育","code":"1600"},{"id":17,"name":"卫生、社会保障和社会福利业","code":"1700"},{"id":18,"name":"文化、体育和娱乐业","code":"1800"},{"id":19,"name":"公共管理和社会组织","code":"1900"},{"id":20,"name":"国际组织","code":"2000"}]
public class IndustryInfo {

    private int id;
    private String name;
    private String code;

    public IndustryInfo(String code, int id, String name) {
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
