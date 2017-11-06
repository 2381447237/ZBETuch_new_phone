package com.youli.zbetuch.jingan.naire;

import java.io.Serializable;

public class HistoryInfo implements Serializable{
	// [{"ID":29,"NO":"080030","PID":"200506205006940","NAME":"周斌","SFZ":"310108198703171072","SEX":"男","EDU":"技工
	//
	// 学校","ZT":"无业","DZ":"止园路221号","QX":"闸北","JD":"宝山","JW":"华德新村居委","LXDZ":"止园路221
	//
	// 号","PHONE":"56301330、13671904137","QA_MASTER":1,"CREATE_DATE":"2014-12-
	//
	// 01T14:16:10.353","CREATE_STAFF":2,"RECEIVED":1,"MARK":"Hh","RECEIVED_STAFF":2,"RECEIVED_TIME":"2015-01-
	//
	// 21T10:07:33.11","RecordCount":0,"IsCreate":false,"IsDelete":false}]

	private int ID;
	private String NO;
	private String PID;
	private String NAME;
	private String SFZ;
	private String SEX;
	private String EDU;
	private String ZT;
	private String DZ;
	private String QX;
	private String JD;
	private String JW;
	private String LXDZ;
	private String PHONE;
	private int QA_MASTER;
	private String CREATE_DATE;
	private int CREATE_STAFF;
	private int RECEIVED;
	private String MARK;
	private int RECEIVED_STAFF;
	private String RECEIVED_TIME;
	private int RecordCount;
	private boolean IsCreate;
	private boolean IsDelete;
	private boolean IsJYStatus;

	public boolean isIsJYStatus() {
		return IsJYStatus;
	}

	public void setIsJYStatus(boolean isJYStatus) {
		IsJYStatus = isJYStatus;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNO() {
		return NO;
	}

	public void setNO(String nO) {
		NO = nO;
	}

	public String getPID() {
		return PID;
	}

	public void setPID(String pID) {
		PID = pID;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getSFZ() {
		return SFZ;
	}

	public void setSFZ(String sFZ) {
		SFZ = sFZ;
	}

	public String getSEX() {
		return SEX;
	}

	public void setSEX(String sEX) {
		SEX = sEX;
	}

	public String getEDU() {
		return EDU;
	}

	public void setEDU(String eDU) {
		EDU = eDU;
	}

	public String getZT() {
		return ZT;
	}

	public void setZT(String zT) {
		ZT = zT;
	}

	public String getDZ() {
		return DZ;
	}

	public void setDZ(String dZ) {
		DZ = dZ;
	}

	public String getQX() {
		return QX;
	}

	public void setQX(String qX) {
		QX = qX;
	}

	public String getJD() {
		return JD;
	}

	public void setJD(String jD) {
		JD = jD;
	}

	public String getJW() {
		return JW;
	}

	public void setJW(String jW) {
		JW = jW;
	}

	public String getLXDZ() {
		return LXDZ;
	}

	public void setLXDZ(String lXDZ) {
		LXDZ = lXDZ;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public int getQA_MASTER() {
		return QA_MASTER;
	}

	public void setQA_MASTER(int qA_MASTER) {
		QA_MASTER = qA_MASTER;
	}

	public String getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public int getCREATE_STAFF() {
		return CREATE_STAFF;
	}

	public void setCREATE_STAFF(int cREATE_STAFF) {
		CREATE_STAFF = cREATE_STAFF;
	}

	public int getRECEIVED() {
		return RECEIVED;
	}

	public void setRECEIVED(int rECEIVED) {
		RECEIVED = rECEIVED;
	}

	public String getMARK() {
		return MARK;
	}

	public void setMARK(String mARK) {
		MARK = mARK;
	}

	public int getRECEIVED_STAFF() {
		return RECEIVED_STAFF;
	}

	public void setRECEIVED_STAFF(int rECEIVED_STAFF) {
		RECEIVED_STAFF = rECEIVED_STAFF;
	}

	public String getRECEIVED_TIME() {
		return RECEIVED_TIME;
	}

	public void setRECEIVED_TIME(String rECEIVED_TIME) {
		RECEIVED_TIME = rECEIVED_TIME;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	public boolean isIsCreate() {
		return IsCreate;
	}

	public void setIsCreate(boolean isCreate) {
		IsCreate = isCreate;
	}

	public boolean isIsDelete() {
		return IsDelete;
	}

	public void setIsDelete(boolean isDelete) {
		IsDelete = isDelete;
	}

}
