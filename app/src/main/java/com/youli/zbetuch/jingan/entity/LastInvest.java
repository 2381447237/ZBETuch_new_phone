package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/11/6.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 *[{"sfz":"310108198004026642","mqzk":"有劳动收入","dqyx":"无需意向分类","mark":"备注2","master_id":4}]
 * http://web.youli.pw:89/Json/Get_Resource_Survey_Last.aspx?SFZ=310108198004026642
 * 最后一次调查情况
 */

public class LastInvest {

 private String sfz;
    private String mqzk;//目前状况
    private String dqyx;//当前意向
    private String mark;//备注
    private int master_id;

    public String getDqyx() {
        return dqyx;
    }

    public void setDqyx(String dqyx) {
        this.dqyx = dqyx;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getMaster_id() {
        return master_id;
    }

    public void setMaster_id(int master_id) {
        this.master_id = master_id;
    }

    public String getMqzk() {
        return mqzk;
    }

    public void setMqzk(String mqzk) {
        this.mqzk = mqzk;
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz;
    }
}
