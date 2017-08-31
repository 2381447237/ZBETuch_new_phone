package com.youli.zbetuch.jingan.entity;

import java.io.Serializable;

/**
 * Created by liutao on 2017/8/4.
 */
//http://web.youli.pw:89/Json/Get_Work_Notice.aspx?page=0&rows=4
//工作通知
//[{"ID":2,"TITLE":"工作通知2","DOC":"分热风啊","NOTICE_TIME":"2014-08-19T08:00:00","CREATE_STAFF":2,"CREATE_DATE":"2014-08-19T17:06:18.347",
// "UPDATE_STAFF":2,"UPDATE_DATE":"2017-02-21T13:36:59.13","RecordCount":2,"CREATE_STAFF_NAME":"admin","Checks":null},
// {"ID":1,"TITLE":"工作通知1","DOC":"内容那个111","NOTICE_TIME":"2014-08-18T12:35:48.757","CREATE_STAFF":2,"CREATE_DATE":"2014-08-18T12:35:48.757","UPDATE_STAFF":2,"UPDATE_DATE":"2014-08-18T12:35:48.757","RecordCount":2,"CREATE_STAFF_NAME":"admin","Checks":null}]
public class WorkNoticeInfo implements Serializable{

  private int ID;
  private String   TITLE;//显示这个
  private String   DOC;
    private String   NOTICE_TIME;
    private int   CREATE_STAFF;
    private String   CREATE_DATE;//显示这个
    private int   UPDATE_STAFF;
    private String   UPDATE_DATE;
    private int  RecordCount;
    private String   CREATE_STAFF_NAME;
    private String   Checks;

  public WorkNoticeInfo(String TITLE, String CREATE_DATE) {
    this.TITLE = TITLE;
    this.CREATE_DATE = CREATE_DATE;
  }

  public String getChecks() {
    return Checks;
  }

  public void setChecks(String checks) {
    Checks = checks;
  }

  public String getCREATE_DATE() {
    return CREATE_DATE;
  }

  public void setCREATE_DATE(String CREATE_DATE) {
    this.CREATE_DATE = CREATE_DATE;
  }

  public int getCREATE_STAFF() {
    return CREATE_STAFF;
  }

  public void setCREATE_STAFF(int CREATE_STAFF) {
    this.CREATE_STAFF = CREATE_STAFF;
  }

  public String getCREATE_STAFF_NAME() {
    return CREATE_STAFF_NAME;
  }

  public void setCREATE_STAFF_NAME(String CREATE_STAFF_NAME) {
    this.CREATE_STAFF_NAME = CREATE_STAFF_NAME;
  }

  public String getDOC() {
    return DOC;
  }

  public void setDOC(String DOC) {
    this.DOC = DOC;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public String getNOTICE_TIME() {
    return NOTICE_TIME;
  }

  public void setNOTICE_TIME(String NOTICE_TIME) {
    this.NOTICE_TIME = NOTICE_TIME;
  }

  public int getRecordCount() {
    return RecordCount;
  }

  public void setRecordCount(int recordCount) {
    RecordCount = recordCount;
  }

  public String getTITLE() {
    return TITLE;
  }

  public void setTITLE(String TITLE) {
    this.TITLE = TITLE;
  }

  public String getUPDATE_DATE() {
    return UPDATE_DATE;
  }

  public void setUPDATE_DATE(String UPDATE_DATE) {
    this.UPDATE_DATE = UPDATE_DATE;
  }

  public int getUPDATE_STAFF() {
    return UPDATE_STAFF;
  }

  public void setUPDATE_STAFF(int UPDATE_STAFF) {
    this.UPDATE_STAFF = UPDATE_STAFF;
  }
}
