package com.youli.zbetuch.jingan.naire;

public class YiChaWenJuan {

	private int ID;
	private int PERSONNEL_ID;
	private int DETIL_ID;
	private String INPUT_VALUE;
	private int RecordCount;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getPERSONNEL_ID() {
		return PERSONNEL_ID;
	}

	public void setPERSONNEL_ID(int pERSONNEL_ID) {
		PERSONNEL_ID = pERSONNEL_ID;
	}

	public int getDETIL_ID() {
		return DETIL_ID;
	}

	public void setDETIL_ID(int dETIL_ID) {
		DETIL_ID = dETIL_ID;
	}

	public String getINPUT_VALUE() {
		return INPUT_VALUE;
	}

	public void setINPUT_VALUE(String iNPUT_VALUE) {
		INPUT_VALUE = iNPUT_VALUE;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	@Override
	public String toString() {
		return "YiChaWenJuan [ID=" + ID + ", PERSONNEL_ID=" + PERSONNEL_ID
				+ ", DETIL_ID=" + DETIL_ID + ", INPUT_VALUE=" + INPUT_VALUE
				+ ", RecordCount=" + RecordCount + "]";
	}

}
