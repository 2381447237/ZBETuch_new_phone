package com.youli.zbetuch.jingan.entity;

/**
 * Created by liutao on 2017/8/25.
 */
//http://web.youli.pw:89/Json/Get_Zyfl.aspx
    //http://web.youli.pw:89/Json/Get_Zyfl_Child.aspx?parent_id=105000(code)
    //[{"id":1,"name":"企业管理人员","code":"105000"},{"id":2,"name":"科研人员","code":"201000"},{"id":3,"name":"工程技术人员","code":"202000"},{"id":4,"name":"农业技术人员","code":"203000"},{"id":5,"name":"飞机船舶技术人员","code":"204000"},{"id":6,"name":"卫生专业技术人员","code":"205000"},{"id":7,"name":"经济业务人员","code":"206000"},{"id":8,"name":"金融业务人员","code":"207000"},{"id":9,"name":"法律专业人员","code":"208000"},{"id":10,"name":"教学人员","code":"209000"},{"id":11,"name":"文学艺术工作者","code":"210000"},{"id":12,"name":"体育工作者","code":"211000"},{"id":13,"name":"新闻出版文化工作者","code":"212000"},{"id":14,"name":"行政办公人员","code":"301000"},{"id":15,"name":"安全保卫和消防人员","code":"302000"},{"id":16,"name":"邮政电信业务人员","code":"303000"},{"id":17,"name":"购销人员","code":"401000"},{"id":18,"name":"仓储人员","code":"402000"},{"id":19,"name":"餐饮服务人员","code":"403000"},{"id":20,"name":"饭店、旅游娱乐服务员","code":"404000"},{"id":21,"name":"运输服务人员","code":"405000"},{"id":22,"name":"医疗卫生辅助服务人员","code":"406000"},{"id":23,"name":"社会服务人员","code":"407000"},{"id":24,"name":"种植业生产人员","code":"501000"},{"id":25,"name":"林业及动植物保护人员","code":"502000"},{"id":26,"name":"畜牧业生产人员","code":"503000"},{"id":27,"name":"渔业生产人员","code":"504000"},{"id":28,"name":"水利设施管理养护人员","code":"505000"},{"id":29,"name":"农林机械操作和能源开发人员","code":"509000"},{"id":30,"name":"勘测及矿物开采工","code":"601000"},{"id":31,"name":"金属冶炼轧制工","code":"602000"},{"id":32,"name":"化工产品生产工","code":"603000"},{"id":33,"name":"机械制造加工工","code":"604000"},{"id":34,"name":"机电产品装配工","code":"605000"},{"id":35,"name":"机械设备修理工","code":"606000"},{"id":36,"name":"电力设备装运检修工","code":"607000"},{"id":37,"name":"电子元器件制造装调工","code":"608000"},{"id":38,"name":"橡胶塑料制品生产工","code":"609000"},{"id":39,"name":"纺织针织印染工","code":"610000"},{"id":40,"name":"裁剪缝纫毛皮革制作工","code":"611000"},{"id":41,"name":"粮油食品饮料生产工","code":"612000"},{"id":42,"name":"烟草制品加工工","code":"613000"},{"id":43,"name":"药品生产制造工","code":"614000"},{"id":44,"name":"木材人造板生产制作工","code":"615000"},{"id":45,"name":"制浆造纸纸制品生产工","code":"616000"},{"id":46,"name":"建筑材料生产加工工","code":"617000"},{"id":47,"name":"玻璃陶瓷搪瓷生产工","code":"618000"},{"id":48,"name":"广播影视品制作播放人员","code":"619000"},{"id":49,"name":"制版印刷人员","code":"620000"},{"id":50,"name":"工艺、美术品制作人员","code":"621000"},{"id":51,"name":"文体用品乐器制作人员","code":"622000"},{"id":52,"name":"建筑和工程施工人员","code":"623000"},{"id":53,"name":"驾驶员和运输工人","code":"624000"},{"id":54,"name":"环境监测废物处理人员","code":"625000"},{"id":55,"name":"检验、计量人员","code":"626000"},{"id":56,"name":"体力工人","code":"627000"}]
//[{"id":1,"name":"部门经理","code":"1050100","parent_id":"105000"},{"id":2,"name":"其他企业管理人员","code":"1059900","parent_id":"105000"}]
public class OccInfo {

    private int id;
    private String name;
    private String code;
    private String parent_id;

    public OccInfo(String code, int id, String name, String parent_id) {
        this.code = code;
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
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

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return name;
    }
}
