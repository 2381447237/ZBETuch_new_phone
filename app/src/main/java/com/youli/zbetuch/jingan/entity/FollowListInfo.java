package com.youli.zbetuch.jingan.entity;

/**
 * 作者: zhengbin on 2017/9/28.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class FollowListInfo {

    private String name;
    private String idCard;

    public FollowListInfo(String idCard, String name) {
        this.idCard = idCard;
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
