package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者: zhengbin on 2017/10/22.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class NaireListInfo implements Serializable{


    /**
     * ID : 1
     * TITLE : 上海市2014年三季度失业预警专项调查问卷
     * TEXT : Get_Qa_Master_Text.aspx?master_id=1
     * NO : 0800000
     * CREATE_TIME : 2014-12-01T00:00:00
     * ISJYSTATUS : false
     * RecordCount : 6
     * Detils : [{"ID":1,"TITLE_L":"Q1.您的文化程度是:","TITLE_R":"","CODE":"Q1","NO":1,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":2,"TITLE_L":"(1)小学及以下","TITLE_R":"","CODE":"1","NO":1.1,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":1,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":3,"TITLE_L":"(2)初中","TITLE_R":"","CODE":"2","NO":1.2,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":1,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":4,"TITLE_L":"(3)高中","TITLE_R":"","CODE":"3","NO":1.3,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":1,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":5,"TITLE_L":"(4)大专","TITLE_R":"","CODE":"4","NO":1.4,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":1,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":6,"TITLE_L":"(5)本科","TITLE_R":"","CODE":"5","NO":1.5,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":1,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":7,"TITLE_L":"(6)研究生","TITLE_R":"","CODE":"6","NO":1.6,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":1,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":8,"TITLE_L":"Q2.2014年9月26日-10月10日,您是否为取得收入而工作了一小时以上?","TITLE_R":"","CODE":"Q2","NO":2,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":9,"TITLE_L":"(1)是，上周（指调查走访日的上一周）工作时间","TITLE_R":"小时(请直接从第Q8题开始回答)","CODE":"1","NO":2.1,"INPUT":true,"INPUT_TYPE":"int","JUMP_CODE":"Q8","PARENT_ID":8,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":10,"TITLE_L":"(2)在职休假、学习、临时停工或季节性歇业(请直接从第Q8题开始回答)","TITLE_R":"","CODE":"2","NO":2.2,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"Q8","PARENT_ID":8,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":11,"TITLE_L":"(3)未作任何工作","TITLE_R":"","CODE":"3","NO":2.3,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":8,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":21,"TITLE_L":"Q3.您在调查期间没有工作的原因是什么?","TITLE_R":"","CODE":"Q3","NO":3,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":22,"TITLE_L":"(1)在校学习(请直接从第Q8题开始问答)","TITLE_R":"","CODE":"1","NO":3.1,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"Q8","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":23,"TITLE_L":"(2)丧失工作能力(请直接从第Q8题开始问答)","TITLE_R":"","CODE":"2","NO":3.2,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"Q8","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":24,"TITLE_L":"(3)离退休(请直接从第Q8题开始问答)","TITLE_R":"","CODE":"3","NO":3.3,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"Q8","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":"","RecordCount":0},{"ID":25,"TITLE_L":"(4)毕业后未工作","TITLE_R":"","CODE":"4","NO":3.4,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":26,"TITLE_L":"(5)因单位原因失去工作","TITLE_R":"","CODE":"5","NO":3.5,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":27,"TITLE_L":"(6)因本人原因失去工作","TITLE_R":"","CODE":"6","NO":3.6,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":28,"TITLE_L":"(7)承包土地被征用","TITLE_R":"","CODE":"7","NO":3.7,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":29,"TITLE_L":"(8)料理家务","TITLE_R":"","CODE":"8","NO":3.8,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":30,"TITLE_L":"(9)其他，请注明：","TITLE_R":"","CODE":"9","NO":3.9,"INPUT":true,"INPUT_TYPE":"string","JUMP_CODE":"","PARENT_ID":21,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":31,"TITLE_L":"Q4.过去的三个月内您是否找过工作？","TITLE_R":"","CODE":"Q4","NO":4,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":32,"TITLE_L":"(1)是","TITLE_R":"","CODE":"1","NO":4.1,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":31,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":33,"TITLE_L":"(2)否(请直接从第Q6题开始回答)","TITLE_R":"","CODE":"2","NO":4.2,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"Q6","PARENT_ID":31,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":34,"TITLE_L":"Q5.您主要是采用以下哪种方式寻找工作的？","TITLE_R":"","CODE":"Q5","NO":5,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":35,"TITLE_L":"(1)在职业介绍机构求职","TITLE_R":"","CODE":"1","NO":5.1,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":34,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":12,"TITLE_L":"Q7.您连续未工作的时间","TITLE_R":"个月?","CODE":"Q7","NO":7,"INPUT":true,"INPUT_TYPE":"int","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":13,"TITLE_L":"Q8.您主要的生活来源是什么?","TITLE_R":"","CODE":"Q8","NO":8,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":0,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":14,"TITLE_L":"(1)劳动收入","TITLE_R":"","CODE":"1","NO":8.1,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":13,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":15,"TITLE_L":"(2)离退休金/养老金","TITLE_R":"","CODE":"2","NO":8.2,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":13,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":16,"TITLE_L":"(3)失业保险金","TITLE_R":"","CODE":"3","NO":8.3,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":13,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":17,"TITLE_L":"(4)最低生活保障金","TITLE_R":"","CODE":"4","NO":8.4,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":13,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":18,"TITLE_L":"(5)财产性收入","TITLE_R":"","CODE":"5","NO":8.5,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":13,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":19,"TITLE_L":"(6)家庭其他成员供养","TITLE_R":"","CODE":"6","NO":8.6,"INPUT":false,"INPUT_TYPE":"","JUMP_CODE":"","PARENT_ID":13,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0},{"ID":20,"TITLE_L":"(7)其他,请注明:","TITLE_R":"","CODE":"7","NO":8.7,"INPUT":true,"INPUT_TYPE":"string","JUMP_CODE":"","PARENT_ID":13,"MASTER_ID":1,"REMOVE_CODE":null,"RecordCount":0}]
     */

    private int ID;
    private String TITLE;//标题
    private String TEXT;
    private String NO;//问卷编号
    private String CREATE_TIME;//调查时间
    private boolean ISJYSTATUS;
    private int RecordCount;
    private List<DetilsBean> Detils;

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

    public boolean isISJYSTATUS() {
        return ISJYSTATUS;
    }

    public void setISJYSTATUS(boolean ISJYSTATUS) {
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

    public static class DetilsBean implements Serializable{
        /**
         * ID : 1
         * TITLE_L : Q1.您的文化程度是:
         * TITLE_R :
         * CODE : Q1
         * NO : 1.0
         * INPUT : false
         * INPUT_TYPE :
         * JUMP_CODE :
         * PARENT_ID : 0
         * MASTER_ID : 1
         * REMOVE_CODE : null
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
        private Object REMOVE_CODE;
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

        public Object getREMOVE_CODE() {
            return REMOVE_CODE;
        }

        public void setREMOVE_CODE(Object REMOVE_CODE) {
            this.REMOVE_CODE = REMOVE_CODE;
        }

        public int getRecordCount() {
            return RecordCount;
        }

        public void setRecordCount(int RecordCount) {
            this.RecordCount = RecordCount;
        }
    }

    @Override
    public String toString() {
        return TITLE ;
    }
}
