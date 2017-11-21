package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者: zhengbin on 2017/11/20.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class ComNaireInfo implements Serializable {


    /**
     * ID : 1
     * TITLE : 上海个人版《就业素质表》
     * TEXT : Get_Qa_Company_Master_Text.aspx?master_id=1
     * NO : 1
     * CREATE_TIME : 2017-11-15T15:09:01.607
     * ISJYSTATUS : null
     * RecordCount : 1
     * Detils : [{"ID":1,"TITLE_L":"您的性别","TITLE_R":"","CODE":"Q1","NO":1,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":2,"TITLE_L":"男","TITLE_R":"","CODE":"01","NO":1.01,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":1,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":3,"TITLE_L":"女","TITLE_R":"","CODE":"02","NO":1.02,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":1,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":4,"TITLE_L":"您的出生年月","TITLE_R":"","CODE":"Q2","NO":2,"INPUT":true,"INPUT_TYPE":"datetime","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":5,"TITLE_L":"您现在从事的职位属于","TITLE_R":"","CODE":"Q3","NO":3,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":6,"TITLE_L":"①经营管理类","TITLE_R":"","CODE":"01","NO":3.01,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":5,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":7,"TITLE_L":"②行政/人力资源管理类","TITLE_R":"","CODE":"02","NO":3.02,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":5,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":8,"TITLE_L":"③公关/市场销售类","TITLE_R":"","CODE":"03","NO":3.03,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":5,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":9,"TITLE_L":"④财务类","TITLE_R":"","CODE":"04","NO":3.04,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":5,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":10,"TITLE_L":"⑤客户服务类","TITLE_R":"","CODE":"05","NO":3.05,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":5,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":11,"TITLE_L":"⑥生产服务类","TITLE_R":"","CODE":"06","NO":3.06,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":5,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":12,"TITLE_L":"⑦技术类","TITLE_R":"","CODE":"07","NO":3.07,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":5,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":13,"TITLE_L":"⑧物流/运输类","TITLE_R":"","CODE":"08","NO":3.08,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":5,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":14,"TITLE_L":"其他","TITLE_R":"","CODE":"09","NO":3.09,"INPUT":true,"INPUT_TYPE":"string","JUMP_CODE":"","PARENT_ID":5,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":15,"TITLE_L":"您在公司的职位层级是：","TITLE_R":"","CODE":"Q4","NO":4,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":16,"TITLE_L":"①公司高管","TITLE_R":"","CODE":"01","NO":4.01,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":15,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":17,"TITLE_L":"②中层管理者","TITLE_R":"","CODE":"02","NO":4.02,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":15,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":18,"TITLE_L":"③基层管理者","TITLE_R":"","CODE":"03","NO":4.03,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":15,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":19,"TITLE_L":"④基层员工","TITLE_R":"","CODE":"04","NO":4.04,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":15,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":20,"TITLE_L":"您的工作经验是:","TITLE_R":"年","CODE":"Q5","NO":5,"INPUT":true,"INPUT_TYPE":"int","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":21,"TITLE_L":"您的学历是","TITLE_R":"","CODE":"Q6","NO":6,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":22,"TITLE_L":"①初中及以下","TITLE_R":"","CODE":"01","NO":6.01,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":23,"TITLE_L":"②高中（职高、技校）","TITLE_R":"","CODE":"02","NO":6.02,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":24,"TITLE_L":"③大专（高职、高专）","TITLE_R":"","CODE":"03","NO":6.03,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":25,"TITLE_L":"④大学本科","TITLE_R":"","CODE":"04","NO":6.04,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":26,"TITLE_L":"⑤研究生及以上","TITLE_R":"","CODE":"05","NO":6.05,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":27,"TITLE_L":"在感觉自己专业知识不够时，我会自己找机会学习。","TITLE_R":"","CODE":"Q7","NO":7,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":28,"TITLE_L":"①完全不符合","TITLE_R":"","CODE":"01","NO":7.01,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":27,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":29,"TITLE_L":"②有些不符合","TITLE_R":"","CODE":"02","NO":7.02,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":27,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":30,"TITLE_L":"③不确定","TITLE_R":"","CODE":"03","NO":7.03,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":27,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":31,"TITLE_L":"④比较符合","TITLE_R":"","CODE":"04","NO":7.04,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":27,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":32,"TITLE_L":"⑤完全符合","TITLE_R":"","CODE":"05","NO":7.05,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":27,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0}]
     */

    private int ID;
    private String TITLE;//标题
    private String TEXT;
    private String NO;
    private String CREATE_TIME;
    private Object ISJYSTATUS;
    private int RecordCount;
    private List<DetilsBean> Detils;

    public ComNaireInfo(String CREATE_TIME, String NO, String TITLE) {
        this.CREATE_TIME = CREATE_TIME;
        this.NO = NO;
        this.TITLE = TITLE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getTEXT() {
        return TEXT;
    }

    public void setTEXT(String TEXT) {
        this.TEXT = TEXT;
    }

    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public Object getISJYSTATUS() {
        return ISJYSTATUS;
    }

    public void setISJYSTATUS(Object ISJYSTATUS) {
        this.ISJYSTATUS = ISJYSTATUS;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int RecordCount) {
        this.RecordCount = RecordCount;
    }

    public List<DetilsBean> getDetils() {
        return Detils;
    }

    public void setDetils(List<DetilsBean> Detils) {
        this.Detils = Detils;
    }

    public static class DetilsBean {
        /**
         * ID : 1
         * TITLE_L : 您的性别
         * TITLE_R :
         * CODE : Q1
         * NO : 1.0
         * INPUT : false
         * INPUT_TYPE :
         * JUMP_CODE :
         * PARENT_ID : 0
         * MASTER_ID : 1
         * REMOVE_CODE :
         * RecordCount : 0
         */

        private int ID;
        private String TITLE_L;
        private String TITLE_R;
        private String CODE;
        private double NO;
        private boolean INPUT;
        private String INPUT_TYPE;
        private String JUMP_CODE;
        private int PARENT_ID;
        private int MASTER_ID;
        private String REMOVE_CODE;
        private int RecordCount;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTITLE_L() {
            return TITLE_L;
        }

        public void setTITLE_L(String TITLE_L) {
            this.TITLE_L = TITLE_L;
        }

        public String getTITLE_R() {
            return TITLE_R;
        }

        public void setTITLE_R(String TITLE_R) {
            this.TITLE_R = TITLE_R;
        }

        public String getCODE() {
            return CODE;
        }

        public void setCODE(String CODE) {
            this.CODE = CODE;
        }

        public double getNO() {
            return NO;
        }

        public void setNO(double NO) {
            this.NO = NO;
        }

        public boolean isINPUT() {
            return INPUT;
        }

        public void setINPUT(boolean INPUT) {
            this.INPUT = INPUT;
        }

        public String getINPUT_TYPE() {
            return INPUT_TYPE;
        }

        public void setINPUT_TYPE(String INPUT_TYPE) {
            this.INPUT_TYPE = INPUT_TYPE;
        }

        public String getJUMP_CODE() {
            return JUMP_CODE;
        }

        public void setJUMP_CODE(String JUMP_CODE) {
            this.JUMP_CODE = JUMP_CODE;
        }

        public int getPARENT_ID() {
            return PARENT_ID;
        }

        public void setPARENT_ID(int PARENT_ID) {
            this.PARENT_ID = PARENT_ID;
        }

        public int getMASTER_ID() {
            return MASTER_ID;
        }

        public void setMASTER_ID(int MASTER_ID) {
            this.MASTER_ID = MASTER_ID;
        }

        public String getREMOVE_CODE() {
            return REMOVE_CODE;
        }

        public void setREMOVE_CODE(String REMOVE_CODE) {
            this.REMOVE_CODE = REMOVE_CODE;
        }

        public int getRecordCount() {
            return RecordCount;
        }

        public void setRecordCount(int RecordCount) {
            this.RecordCount = RecordCount;
        }
    }
}
