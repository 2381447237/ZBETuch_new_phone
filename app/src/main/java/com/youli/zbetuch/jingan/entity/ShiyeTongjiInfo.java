package com.youli.zbetuch.jingan.entity;

import java.util.List;

/**
 * 作者: zhengbin on 2017/10/12.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * http://web.youli.pw:89/Json/Get_Resources_Query.aspx
 *
 * [{"sum_value_man":8419,"sum_value_woman":3419,"type":"登记失业","committee_id":1,"name":"登记失业","order_id":"1","all":11838}]
 */

public class ShiyeTongjiInfo {

    private int sum_value_man;//男
    private int sum_value_woman;//女
    private String type;
    private int committee_id;
    private String name;
    private String order_id;
    private int all;

    private boolean isCheck;

    private TongjiInfo info;

    private List<ShiyeTongjiChildInfo> childList;

    public ShiyeTongjiInfo(TongjiInfo info, boolean isCheck) {
        this.info = info;
        this.all = info.getAll();
        this.committee_id = info.getCommittee_id();
        this.name = info.getName();
        this.order_id = info.getOrder_id();
        this.sum_value_man = info.getSum_value_man();
        this.sum_value_woman = info.getSum_value_woman();
        this.type = info.getType();

        this.isCheck = isCheck;

    }

    public static class ShiyeTongjiChildInfo{

        private int sum_value_man;//男
        private int sum_value_woman;//女
        private String type;
        private int committee_id;
        private String name;
        private String order_id;
        private int all;
        private TongjiInfo info;

        @Override
        public String toString() {
            return "ShiyeTongjiChildInfo{" +
                    "all=" + all +
                    ", committee_id=" + committee_id +
                    ", name='" + name + '\'' +
                    ", order_id='" + order_id + '\'' +
                    ", sum_value_man=" + sum_value_man +
                    ", sum_value_woman=" + sum_value_woman +
                    ", type='" + type + '\'' +
                    '}';
        }

        public ShiyeTongjiChildInfo(TongjiInfo info) {
            this.info = info;
            this.all = info.getAll();
            this.committee_id = info.getCommittee_id();
            this.name = info.getName();
            this.order_id = info.getOrder_id();
            this.sum_value_man = info.getSum_value_man();
            this.sum_value_woman = info.getSum_value_woman();
            this.type = info.getType();
        }

        public ShiyeTongjiChildInfo(ShiyeTongjiChildInfo info) {
            this.all = info.getAll();
            this.committee_id = info.getCommittee_id();
            this.name = info.getName();
            this.order_id = info.getOrder_id();
            this.sum_value_man = info.getSum_value_man();
            this.sum_value_woman = info.getSum_value_woman();
            this.type = info.getType();
        }
        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }

        public int getCommittee_id() {
            return committee_id;
        }

        public void setCommittee_id(int committee_id) {
            this.committee_id = committee_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public int getSum_value_man() {
            return sum_value_man;
        }

        public void setSum_value_man(int sum_value_man) {
            this.sum_value_man = sum_value_man;
        }

        public int getSum_value_woman() {
            return sum_value_woman;
        }

        public void setSum_value_woman(int sum_value_woman) {
            this.sum_value_woman = sum_value_woman;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public List<ShiyeTongjiChildInfo> getChildList() {
        return childList;
    }

    public void setChildList(List<ShiyeTongjiChildInfo> childList) {
        this.childList = childList;
    }

    public int getCommittee_id() {
        return committee_id;
    }

    public void setCommittee_id(int committee_id) {
        this.committee_id = committee_id;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getSum_value_man() {
        return sum_value_man;
    }

    public void setSum_value_man(int sum_value_man) {
        this.sum_value_man = sum_value_man;
    }

    public int getSum_value_woman() {
        return sum_value_woman;
    }

    public void setSum_value_woman(int sum_value_woman) {
        this.sum_value_woman = sum_value_woman;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ShiyeTongjiInfo{" +
                "all=" + all +
                ", sum_value_man=" + sum_value_man +
                ", sum_value_woman=" + sum_value_woman +
                ", type='" + type + '\'' +
                ", committee_id=" + committee_id +
                ", name='" + name + '\'' +
                ", order_id='" + order_id + '\'' +
                ", isCheck=" + isCheck +
                ", childList=" + childList +
                '}';
    }
}
