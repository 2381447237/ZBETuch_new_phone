package com.youli.zbetuch.jingan.naire;

public class WenJuanInfo {

	private int ID;
	private String TITLE_L;
	private String TITLE_R;
	private String CODE;
	private double NO;
	private boolean INPUT;
	private String INPUT_TYPE;
	private String JUMP_CODE;
	private int PARENT_ID;
	private int RecordCount;
	private String REMOVE_CODE;
	private int MultiSelect;
	private String BindInfo;
	
	
	public int getMultiSelect() {
		return MultiSelect;
	}

	public void setMultiSelect(int _MultiSelect) {
		MultiSelect = _MultiSelect;
	}
	
	public String getREMOVE_CODE() {
		return REMOVE_CODE;
	}

	public void setREMOVE_CODE(String rEMOVE_CODE) {
		REMOVE_CODE = rEMOVE_CODE;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTITLE_L() {
		return TITLE_L;
	}

	public void setTITLE_L(String tITLE_L) {
		TITLE_L = tITLE_L;
	}

	public String getTITLE_R() {
		return TITLE_R;
	}

	public void setTITLE_R(String tITLE_R) {
		TITLE_R = tITLE_R;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public double getNO() {
		return NO;
	}

	public void setNO(double nO) {
		NO = nO;
	}

	public boolean isINPUT() {
		return INPUT;
	}

	public void setINPUT(boolean iNPUT) {
		INPUT = iNPUT;
	}

	public String getINPUT_TYPE() {
		return INPUT_TYPE;
	}

	public void setINPUT_TYPE(String iNPUT_TYPE) {
		INPUT_TYPE = iNPUT_TYPE;
	}

	public String getJUMP_CODE() {
		return JUMP_CODE;
	}

	public void setJUMP_CODE(String jUMP_CODE) {
		JUMP_CODE = jUMP_CODE;
	}

	public int getPARENT_ID() {
		return PARENT_ID;
	}

	public void setPARENT_ID(int pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}
	
	public String getBindInfo() {
		return BindInfo;
	}

	public void setBindInfo(String _BindInfo) {
		BindInfo = _BindInfo;
	}

	@Override
	public String toString() {
		return "WenJuanInfo [ID=" + ID + ", TITLE_L=" + TITLE_L + ", TITLE_R="
				+ TITLE_R + ", CODE=" + CODE + ", NO=" + NO + ", INPUT="
				+ INPUT + ", INPUT_TYPE=" + INPUT_TYPE + ", JUMP_CODE="
				+ JUMP_CODE + ", PARENT_ID=" + PARENT_ID + ", RecordCount="
				+ RecordCount + ", REMOVE_CODE=" + REMOVE_CODE
				+ ", MultiSelect=" + MultiSelect + ", BindInfo=" + BindInfo
				+ "]";
	}

	
	
	
}
