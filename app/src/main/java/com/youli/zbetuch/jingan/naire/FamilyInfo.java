package com.youli.zbetuch.jingan.naire;

import java.io.Serializable;

public class FamilyInfo implements Serializable {
//	Name nvarchar(50),--姓名
//	Sfz nvarchar(18),--身份证
//	LxR nvarchar(50),--联系人
//	LxDf nvarchar(50),--联系电话
//	JzNum int,--居住人数
//	Nan int,--男人数
//	Nv int,--女人数
//	XSQ nvarchar(50),--县、市、区
//	XZJD nvarchar(50),--乡、镇、街道
//	SQJWC nvarchar(50),--社区、村居委
//	Adress nvarchar(50),--详细地址
//	QuestionMasterId int, --调查表Id
//	Create_Staff int--创建人

	private int ID;
	private String NAME;
	private String SFZ;
	private String LXR;
	private String LXDF;
	private int JZNUM;
	private int NAN;
	private int NV;
	private String XSQ;
	private String XZJD;
	private String SQJWC;
	private String ADRESS;
	private int QUESTIONMASTERID;
	private String CREATE_TIME;
	private int CREATE_STAFF;
	private int ZT;
	private int ZS;
	private String SQR;
	private int RecordCount;
	
	public String getSQR() {
		return SQR;
	}
	public void setSQR(String sQR) {
		SQR = sQR;
	}
	public int getZT() {
		return ZT;
	}
	public void setZT(int zT) {
		ZT = zT;
	}
	public int getZS() {
		return ZS;
	}
	public void setZS(int zS) {
		ZS = zS;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
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
	public String getLXR() {
		return LXR;
	}
	public void setLXR(String lXR) {
		LXR = lXR;
	}
	public String getLXDF() {
		return LXDF;
	}
	public void setLXDF(String lXDF) {
		LXDF = lXDF;
	}
	public int getJZNUM() {
		return JZNUM;
	}
	public void setJZNUM(int jZNUM) {
		JZNUM = jZNUM;
	}
	public int getNAN() {
		return NAN;
	}
	public void setNAN(int nAN) {
		NAN = nAN;
	}
	public int getNV() {
		return NV;
	}
	public void setNV(int nV) {
		NV = nV;
	}
	public String getXSQ() {
		return XSQ;
	}
	public void setXSQ(String xSQ) {
		XSQ = xSQ;
	}
	public String getXZJD() {
		return XZJD;
	}
	public void setXZJD(String xZJD) {
		XZJD = xZJD;
	}
	public String getSQJWC() {
		return SQJWC;
	}
	public void setSQJWC(String sQJWC) {
		SQJWC = sQJWC;
	}
	public String getADRESS() {
		return ADRESS;
	}
	public void setADRESS(String aDRESS) {
		ADRESS = aDRESS;
	}
	public int getQUESTIONMASTERID() {
		return QUESTIONMASTERID;
	}
	public void setQUESTIONMASTERID(int qUESTIONMASTERID) {
		QUESTIONMASTERID = qUESTIONMASTERID;
	}
	public String getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	public int getCREATE_STAFF() {
		return CREATE_STAFF;
	}
	public void setCREATE_STAFF(int cREATE_STAFF) {
		CREATE_STAFF = cREATE_STAFF;
	}
	public int getRecordCount() {
		return RecordCount;
	}
	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}
	@Override
	public String toString() {
		return "FamilyInfo [ID=" + ID + ", NAME=" + NAME + ", SFZ=" + SFZ
				+ ", LXR=" + LXR + ", LXDF=" + LXDF + ", JZNUM=" + JZNUM
				+ ", NAN=" + NAN + ", NV=" + NV + ", XSQ=" + XSQ + ", XZJD="
				+ XZJD + ", SQJWC=" + SQJWC + ", ADRESS=" + ADRESS
				+ ", QUESTIONMASTERID=" + QUESTIONMASTERID + ", CREATE_TIME="
				+ CREATE_TIME + ", CREATE_STAFF=" + CREATE_STAFF + ", ZT=" + ZT
				+ ", ZS=" + ZS + ", RecordCount=" + RecordCount + "]";
	}
	
	
}


//[{"ID":17,"NAME":"叶晨露(测试)","SFZ":"310108198809052629","LXR":"k","LXDF":"56770167、13901783823","JZNUM":5,"NAN":4,"NV":3,
//"XSQ":"闸北","XZJD":"大宁","SQJWC":"延峰居委","ADRESS":"延长中路561弄21号502室","QUESTIONMASTERID":5,"CREATE_TIME":"2017-03-06T13:30:11.207","CREATE_STAFF":2,"RecordCount":0},
//{"ID":18,"NAME":"叶晨露(测试)","SFZ":"310108198809052629","LXR":"kkk","LXDF":"56770167、13901783823","JZNUM":5,"NAN":4,"NV":3,"XSQ":"闸北","XZJD":"大宁","SQJWC":"延峰居委","ADRESS":"延长中路561弄21号502室","QUESTIONMASTERID":5,"CREATE_TIME":"2017-03-06T13:31:52.043","CREATE_STAFF":2,"RecordCount":0},{"ID":19,"NAME":"叶晨露(测试)","SFZ":"310108198809052629","LXR":"jjj","LXDF":"56770167、13901783823","JZNUM":6,"NAN":3,"NV":2,"XSQ":"闸北","XZJD":"大宁","SQJWC":"延峰居委","ADRESS":"延长中路561弄21号502室","QUESTIONMASTERID":5,"CREATE_TIME":"2017-03-06T13:44:54.63","CREATE_STAFF":2,"RecordCount":0},{"ID":20,"NAME":"叶晨露(测试)","SFZ":"310108198809052629","LXR":"kk","LXDF":"56770167、13901783823","JZNUM":6,"NAN":5,"NV":4,"XSQ":"闸北","XZJD":"大宁","SQJWC":"延峰居委","ADRESS":"延长中路561弄21号502室","QUESTIONMASTERID":5,"CREATE_TIME":"2017-03-06T13:50:03.43","CREATE_STAFF":2,"RecordCount":0}]