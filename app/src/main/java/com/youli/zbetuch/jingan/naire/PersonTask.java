package com.youli.zbetuch.jingan.naire;

import java.util.Map;

import org.apache.http.auth.NTCredentials;

import android.R.integer;

public class PersonTask {
	private int taskId;

	private Map<String, Object> params;

	public static final int PERSONINFO_GETREGION = 1;
	public static final int PERSONINFO_GETSTREET = 2;
	public static final int PERSONINFO_GETJUWEIHUI = 3;
	public static final int FAMILY_GETFAMILY = 4;
	public static final int FAMILY_GETFAMILY_IMAGE = 5;
	/**
	 * 得到同屋信息
	 */
	public static final int FAMILY_GETHOUSES = 6;
	/**
	 * 得到同屋照片信息
	 */
	public static final int FAMILY_GETHOUSE_IMAGE = 7;
	/**
	 * 根据PersoninfoActivity的参数查询人员信息
	 */
	public static final int PERSONQUERYLISTACTIVITY_GETPERSONS_BY_PARAMS = 8;

	/**
	 * 人员列表页面根据公司查询人员列表
	 */
	public static final int PERSONQUERYLISTACTIVITY_GETPERSONS_BY_COMPANY = 9;

	/**
	 * 根据资源页面查找人员列表
	 */
	public static final int PERSONQUERYLISTACTIVITY_GETPERSONS_BY_RESOURCES = 10;

	/**
	 * 个人信息主页面查询个人基本信息
	 */
	public static final int PERSONINFOMAINACTIVITY_GETPERSONBASE = 11;

	/**
	 * 个人信息主页面查询个人照片
	 */
	public static final int PERSONINFOMAINACTIVITY_GETPERSONIMG = 12;

	/**
	 * 查询个人权限
	 */
	public static final int PERSONINFOMAINACTIVITY_GETPERSONLEVEL = 13;

	/**
	 * 查询工作经历列表
	 */
	public static final int WORKINFOACTIVITY_GET_WORKINFOLIST = 14;

	/**
	 * 工作经历修改页面查询工作经历列表
	 */
	public static final int MODIFYWORKINFOACTIVITY_GET_WORKINFOLIST = 15;

	/**
	 * 更新工作经历
	 */
	public static final int MODIFYWORKINFOACTIVITY_UPDATE_WORKINFO = 16;

	/**
	 * 人员详细界面添加关注
	 */
	public static final int ADDPERSONINFOACTIVITY_UPDATE_ACTION = 17;

	/**
	 * 关注界面查询关注人员
	 */
	public static final int ATTENTIONACTIVITY_GET_ATTENTIONS = 18;

	/**
	 * 删除关注
	 */
	public static final int ATTENTIONACTIVITY_DELETE_ATTENTION = 19;

	/**
	 * 修改个人信息界面得到街道信息
	 */
	public static final int MODIFYPERSONINFOACTIVITY_GET_STREET = 20;

	/**
	 * 修改个人信息界面得到居委信息
	 */
	public static final int MODIFYPERSONINFOACTIVITY_GET_JUWEIHUI = 21;

	/**
	 * 修改个人信息界面得到照片信息
	 */
	public static final int MODIFYPERSONINFOACTIVITY_GET_PERSONIMAGE = 22;

	/**
	 * 修改个人信息界面得到标识信息
	 */
	public static final int MODIFYPERSONINFOACTIVITY_GET_PERSONMARK = 23;

	/**
	 * 修改个人信息界面上传个人图片
	 */
	public static final int MODIFYPERSONINFOACTIVITY_UPDATE_PERSONIMAGE = 24;

	/**
	 * 修改个人信息界面更新个人信息
	 */
	public static final int MODIFYPERSONINFOACTIVITY_UPDATE_PERSONINFO = 25;

	/**
	 * 教育信息界面查询教育信息列表
	 */
	public static final int SCHOOLINFOACTIVITY_GET_SCHOOLINFOLIST = 26;

	/**
	 * 修改教育信息界面更新教育信息
	 */
	public static final int MODIFYSCHOOLINFOACTIVITY_UPDATE_SCHOOLINFO = 27;

	/**
	 * 修改教育信息界面得到教育信息
	 */
	public static final int MODIFYSCHOOLINFOACTIVITY_GET_SCHOOLINFOLIST = 28;

	/**
	 * 得到服务选项卡的信息
	 */
	public static final int FUXUANGXIANGKA_GET_INFO = 29;

	/**
	 * 服务选项卡的服务内容标识
	 */
	public static final int FUXUANG_NEW_FUWUCONTENT = 30;

	/**
	 * 增加服务选项卡的标识
	 */
	public static final int NEW_FUWU_XUANXIANG = 31;

	/**
	 * 修改服务选项卡的标识
	 */
	public static final int UPDATE_FUWU_XUANXIANG = 32;

	public static final int FUWUANG_UPDATE_FUWUCONTENT = 33;

	public static final int DELETE_FUWU_INFO = 34;

	/**
	 * 资源调查
	 */
	public static final int ZHIYUANDIAOCHAACTIVITY_GETZHIYUANDIAOCHAINFO = 35;

	public static final int ZHIYUANDIAOCHAACTIVITY_QUEZHIYUANDIAOCHAINFO = 76;
	public static final int ZHIYUANDETAILACTIVITY_GET_INFOS = 36;

	public static final int ZHIYUANDETAILACTIVITY_GET_SFZ_IFNO = 37;

	public static final int ZHIYUANDETAILACTIVITY_SET_SHIYE_DETAIL_INFO = 38;
	public static final int ZHIYUANDETAILACTIVITY_SET_WUYE_DETAIL_INFO = 39;

	public static final int ZHIYUANDETAIL_GET_LASTINFO = 40;

	public static final int MODIFYPERSONMARK_GET_PERSONINFOS = 41;

	public static final int MODIFYPERSONMARK_GET_TYPE_INFO = 42;

	public static final int MODIFYPERSONMARK_GET_INFOS = 43;

	public static final int MODIFYPERSONMARK_TYPE_NAME = 44;

	public static final int MODIFYPERSONMARK_TYPE_DETAIL = 45;

	public static final int MODIFYPERSONMARK_INFO = 46;

	public static final int MODIFYPERSONMARK_TYPE_INFO = 47;

	public static final int ATTENTION_INFOS = 48;

	public static final int ATTENTION_INFOS_DELETE = 49;

	public static final int WORKTONGZHIINFOACTIVITY_GET_WORKTONGZHILIST = 50;

	public static final int WORKTONGZHIINFOACTIVITYDETAIL_GET_READNUM = 51;

	public static final int WORKTONGZHIINFOACTIVITYDETAIL_GET_BUTTONSTATUS = 52;

	public static final int WORKTONGZHIINFOACTIVITYDETAIL_SET_BUTTONSTATUS = 53;

	public static final int WORKTONGZHIINFOACTIVITYDETAIL_GET_DOWNFILE = 54;

	public static final int WORKTONGZHIINFOACTIVITYDETAIL_GET_DOC = 55;

	public static final int FIRST_FIRSTPAGEACTIVITY_GET_WORK = 56;

	public static final int FIRST_FIRST_INFOS = 57;

	public static final int WORKSTATUSINFOACTIVITY_GET_WORKSTATUSINFO_READ = 58;

	public static final int WORKSTATUSINFOACTIVITY_GET_WORKSTATUSINFO = 59;

	public static final int WORKSTATUSINFODETAILACTIVITY_GET_WORKSTATUSINFO_STATUS = 60;

	public static final int WORKSTATUSINFODETAILACTIVITY_GET_WORKSTATUSFILE = 61;

	public static final int WORKSTATUSDEATIL_GET_DOWNFILE = 62;

	public static final int WORKSTATUSINFODETAILACTIVITY_SET_WORKSTATUSINFO_STATUS = 63;

	public static final int WORKSTATUSDEATIL_SET_WORK_NEWS = 64;

	public static final int WORKSTATUSDEATIL_GET_WORK_NEWS = 65;

	public static final int ZHIYUANDETAILACTIVITY_SET_QH_DETAIL_INFO = 66;

	public static final int QIHANGDIAOCHAOACTIVITY_GET_INFOS = 67;

	public static final int QIHANGACTIVITY_GET_SFZ_IFNO = 68;

	public static final int WENJUANACTIVITY_GET_WENJUANPERSON = 70;
	

	public static final int UPLOADWENJUAN_SET_WENJUAN = 71;

	
	
	

	public static final int WENJUANACTIVITY_GET_WENJUANINFO = 72;
	public static final int GXWENJUANACTIVITY_GET_WENJUANINFO =77;

	public static final int WENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER = 73;
	public static final int WENJUANLISTACTIVITY_XUHUI_INFO_LIST_ANSWER = 84;
	public static final int ACTIVITY_HISTORY_LIST = 74;

	public static final int WENJUAN_HISTORY_LIST_ACTIVITY_GET_WENJUANINFO = 75;
	
	
	public static final int GXWENJUANACTIVITY_GET_WENJUANPERSON = 78;
	public static final int GXWENJUAN_HISTORY_LIST_ACTIVITY_GET_WENJUANINFO = 79;
	public static final int GXWENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER = 80;
	public static final int UPLOADWENJUAN_SET_WENJUAN_special = 81;
	public static final int GXWENJUAN_GetReceiv_Special = 82;
	public static final int GXUPLOADWENJUAN_SET_WENJUAN_Mark = 83;
	

	public PersonTask(int taskId, Map<String, Object> params) {
		this.taskId = taskId;
		this.params = params;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	
	private String jsonString;
	public void setJsonString(String  JsonString) {
		this.jsonString = JsonString;
	}
	public String GetJsonString() {
		return jsonString;
	}
}
