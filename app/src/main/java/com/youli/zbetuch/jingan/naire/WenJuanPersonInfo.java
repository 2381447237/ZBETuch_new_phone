package com.youli.zbetuch.jingan.naire;

import java.io.Serializable;

public class WenJuanPersonInfo implements Serializable {

	private int ID;
	private String NO;
	private String PID;
	private String NAME;
	private String SFZ;
	private String MZ;// 民族
	private String CSRQ;// 出生日期	
	private String MDQK;// 目前摸底状况
	private String DQYX;// 当前意向
	private String JYZT;// 就业状态
	private String KSXX;//  学习起始日期
	private String JSXX;// 学习终止日期
	private String XXMC;// 学校名称
	private String SXZY;// 所学专业
	private String SFBYY;// 是否毕肄业
	private String SFQRZ;// 是否全日制
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
	private int RECEIVED_ID;
	private String MARK;
	private int RecordCount;
	private int JTCYSL;

	public int getJTCYSL() {
		return JTCYSL;
	}

	public void setJTCYSL(int jTCYSL) {
		JTCYSL = jTCYSL;
	}

	public String getMZ() {
		return MZ;
	}

	public void setMZ(String mZ) {
		MZ = mZ;
	}

	public String getCSRQ() {
		return CSRQ;
	}

	public void setCSRQ(String cSRQ) {
		CSRQ = cSRQ;
	}

	public String getMDQK() {
		return MDQK;
	}

	public void setMDQK(String mDQK) {
		MDQK = mDQK;
	}

	public String getDQYX() {
		return DQYX;
	}

	public void setDQYX(String dQYX) {
		DQYX = dQYX;
	}

	public String getJYZT() {
		return JYZT;
	}

	public void setJYZT(String jYZT) {
		JYZT = jYZT;
	}

	public String getKSXX() {
		return KSXX;
	}

	public void setKSXX(String kSXX) {
		KSXX = kSXX;
	}

	public String getJSXX() {
		return JSXX;
	}

	public void setJSXX(String jSXX) {
		JSXX = jSXX;
	}

	public String getXXMC() {
		return XXMC;
	}

	public void setXXMC(String xXMC) {
		XXMC = xXMC;
	}

	public String getSXZY() {
		return SXZY;
	}

	public void setSXZY(String sXZY) {
		SXZY = sXZY;
	}

	public String getSFBYY() {
		return SFBYY;
	}

	public void setSFBYY(String sFBYY) {
		SFBYY = sFBYY;
	}

	public String getSFQRZ() {
		return SFQRZ;
	}

	public void setSFQRZ(String sFQRZ) {
		SFQRZ = sFQRZ;
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

	public int getRECEIVED_ID() {
		return RECEIVED_ID;
	}

	public void setRECEIVED_ID(int rECEIVED_ID) {
		RECEIVED_ID = rECEIVED_ID;
	}

	public String getMARK() {
		return MARK;
	}

	public void setMARK(String mARK) {
		MARK = mARK;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	@Override
	public String toString() {
		return "WenJuanPersonInfo [ID=" + ID + ", NO=" + NO + ", PID=" + PID
				+ ", NAME=" + NAME + ", SFZ=" + SFZ + ", MZ=" + MZ + ", CSRQ="
				+ CSRQ + ", MDQK=" + MDQK + ", DQYX=" + DQYX + ", JYZT=" + JYZT
				+ ", KSXX=" + KSXX + ", JSXX=" + JSXX + ", XXMC=" + XXMC
				+ ", SXZY=" + SXZY + ", SFBYY=" + SFBYY + ", SFQRZ=" + SFQRZ
				+ ", SEX=" + SEX + ", EDU=" + EDU + ", ZT=" + ZT + ", DZ=" + DZ
				+ ", QX=" + QX + ", JD=" + JD + ", JW=" + JW + ", LXDZ=" + LXDZ
				+ ", PHONE=" + PHONE + ", QA_MASTER=" + QA_MASTER
				+ ", CREATE_DATE=" + CREATE_DATE + ", CREATE_STAFF="
				+ CREATE_STAFF + ", RECEIVED_ID=" + RECEIVED_ID + ", MARK="
				+ MARK + ", RecordCount=" + RecordCount + ", JTCYSL=" + JTCYSL
				+ "]";
	}

}
