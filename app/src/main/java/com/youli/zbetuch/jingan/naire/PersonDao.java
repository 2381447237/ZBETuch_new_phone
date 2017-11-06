package com.youli.zbetuch.jingan.naire;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import android.util.Log;

public class PersonDao {

	/**
	 * 查询区县信息
	 * 
	 * @return 区县json字符串
	 */
	public String getRegionStr() {
		String urlStr = "/Json/Get_Area.aspx?REGION=310100";
		try {

			return HttpUtil.getRequest(urlStr);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到街道消息
	 * 
	 * @param regionId
	 * @return
	 */
	public String getStreetStr(int regionId) {
		
		String url = "/Json/Get_Area.aspx?STREET=" + regionId;
		try {
			return HttpUtil.getRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到居委会信息
	 * 
	 * @param streetId
	 * @return
	 */
	public String getJuweihuiStr(int streetId) {
		String url = "/Json/Get_Area.aspx?COMMITTEE=" + streetId;
		try {
			return HttpUtil.getRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到家庭信息
	 * 
	 * @param sfz
	 * @return
	 */
	public String getFamilyStr(String sfz) {
		String url = "/Json/Get_family_Info.aspx?sfz=" + sfz;
		try {
			return HttpUtil.getRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到同屋信息
	 * 
	 * @param sfz
	 * @return
	 */
	public String getHousesStr(String sfz) {
		String url = "/Json/Get_family_Info_Now.aspx?sfz=" + sfz;
		try {
			return HttpUtil.getRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到个人照片
	 * 
	 * @param sfz
	 * @return
	 */
	public byte[] getPersonImage(String sfz) {
		String url = "/Web/Personal/windows/ShowPic.aspx?sfz=" + sfz;
		try {
			return HttpUtil.getImage(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据条件查询个人列表
	 * 
	 * @param data
	 * @return
	 */
	public String getPersonListStrByParams(Map<String, String> data) {

		String url = "/Json/Get_BASIC_INFORMATION.aspx?page="
				+ data.get("page") + "&rows=15" + "&name="
				+ URLEncoder.encode(data.get("name")) + "&sex="
				+ URLEncoder.encode(data.get("sex")) + "&start_age="
				+ data.get("start_age") + "&end_age=" + data.get("end_age")
				+ "&regionid=" + data.get("regionid") + "&STREET_ID="
				+ data.get("STREET_ID") + "&COMMITTEE_ID="
				+ data.get("COMMITTEE_ID") + "&mark="
				+ URLEncoder.encode(data.get("mark")) + "&TYPE="
				+ URLEncoder.encode(data.get("TYPE")) + "&Current_situation="
				+ URLEncoder.encode(data.get("Current_situation"))
				+ "&Current_intent="
				+ URLEncoder.encode(data.get("Current_intent")) + "&Resources="
				+ data.get("Resources");
		try {
			return HttpUtil.getRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据公司名称查询人员信息
	 * 
	 * @param data
	 * @return
	 */
	public String getPersonsListStrByCompany(Map<String, String> data) {
		String url = "/Json/GetSeekersInfo.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据资源信息查找人员信息
	 * 
	 * @param data
	 * @return
	 */
	public String getPersonsListStrByResources(Map<String, String> data) {
		String url = "/Json/Get_BASIC_INFORMATION.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据身份证查询个人
	 * 
	 * @param sfz
	 * @return
	 */
	public String getPersonBase(String sfz) {
		String url = "/Json/Get_BASIC_INFORMATION.aspx?sfz=" + sfz;
		try {
			String value = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 查询用户权限
	 * 
	 * @param name
	 * @return
	 */
	public String getPersonLevel(String name) {
		try {
			String url = "/Json/GetCheckLineLevel.aspx?module_name="
					+ URLEncoder.encode(name, "UTF-8");
			
			String value = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 查询工作经历列表
	 * 
	 * @param data
	 * @return
	 */
	public String getWorkInfoList(Map<String, String> data) {
		String url = "/Json/Get_Work_Experience.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 更新工作经历
	 * 
	 * @param data
	 * @return
	 */
	public String updateWorkInfo(Map<String, String> data) {
		String url = "/Json/Set_Work_Experience.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 更新关注信息 szf type 0增 1表示删
	 * 
	 * @param data
	 * @return
	 */
	public String updateAction(Map<String, String> data) {
		String url = "/Json/Set_Attention.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 查询关注信息
	 * 
	 * @param data
	 *            page 页 rows 行
	 * @return
	 */
	public String getActions(Map<String, String> data) {
		String url = "/Json/Get_Attention.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据身份证查询标识信息
	 * 
	 * @param data
	 * @return
	 */
	public String getPersonMark(Map<String, String> data) {
		String url = "/Json/Get_Tb_Mark.aspx?sfz=" + data.get("sfz");
		try {
			String value = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 上传个人图片
	 * 
	 * @param data
	 * @return
	 */
	public String setPersonImg(Map<String, String> data) {
		String url = "/Json/Set_Photo.aspx";
		try {
			
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 更新个人基本信息
	 * 
	 * @param data
	 * @return
	 */
	public String setPersonInfoBase(Map<String, String> data) {
		String url = "/Json/Set_BASIC_INFORMATION.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 查询教育经历列表
	 * 
	 * @param data
	 * @return
	 */
	public String getSchoolInfoList(Map<String, String> data) {
		String url = "/Json/Get_Educational_Information.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 更新教育经历
	 * 
	 * @param data
	 * @return
	 */
	public String updateSchoolInfo(Map<String, String> data) {
		String url = "/Json/Set_Educational_Information.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取服务选项卡内容
	 * 
	 * @param data
	 * @return
	 */
	public String getFuWuXuanString(Map<String, String> data) {

		String url = "/Json/Get_Sfz_Service.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 获取服务内容
	 */
	public String getFuWuXuanContent() {

		String url = "/Json/Get_Service_Type.aspx";

		try {
			String value = HttpUtil.postRequest(url, null);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 保存服务
	 * 
	 * @param data
	 * @return
	 */
	public String setFuWUContext(Map<String, String> data) {
		String url = "/Json/Set_Sfz_Service.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 删除服务选项
	 */
	public String deleteFuWu(Map<String, String> data) {
		String url = "/Json/Set_Sfz_Service.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getStaff_Marks(Map<String, String> data) {
		String url = "/Json/Get_TB_Staff_Marks.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getStaff_Marks_type(Map<String, String> data) {
		String url = "/Json/Get_TB_Staff_Mark_Type.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String setStaff_Marks_type(Map<String, String> data) {
		String url = "/Json/Set_TB_Staff_Mark_Type.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String setPost_Type(Map<String, String> data) {
		String url = "/Json/Set_TB_Staff_Marks.aspx";
		String value = HttpUtil.postJson(url, data.get("json"));
		return value;
	}

	public String getQihang_info(Map<String, String> data) {
		String url = "/Json/Get_Resource_Survey_Detil_QH.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getQihang_info_by_sfz(Map<String, String> data) {
		String url = "/Json/Get_Resource_Survey_Detil_QH.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String setWenJuanJsonInfo(Map<String, String> data) {
		String url = "/Json/Set_Qa_Receiv.aspx";
		Map<String, String> data1 = new HashMap<String, String>();
		data1.put("Personnel_id", data.get("Personnel_id"));
		data1.put("mark", data.get("mark"));
		String value = HttpUtil.postJson_GXWenJuan(url, data.get("data"), data1);
		return value;
	}

	public String setGXWenJuanJsonInfoMark(Map<String, String> data) {
		String url = "/Json/Set_Qa_Receiv_Special.aspx";
		Map<String, String> data1 = new HashMap<String, String>();
		data1.put("Personnel_id", data.get("Personnel_id"));
		data1.put("mark", data.get("mark"));
		String value = HttpUtil.postJson_GXWenJuan(url, data.get("data"), data1);
		return value;
	}
	
	/**
	 * 添加删除问卷答案（每一题提交一次）
	 * @param data
	 * @return
	 */
	public String setGXWenJuanJsonInfo(Map<String, String> data){
		String url="/Json/Set_Qa_Receiv_Special.aspx";
		Map<String, String> data1 = new HashMap<String, String>();

		data1.put("Receiv_id", data.get("Receiv_id"));
		//Log.i("qwj","Receiv_id2:"+data.get("Receiv_id"));
		
		if(data.containsKey("del"))
			data1.put("del", data.get("del"));
		data1.put("index", data.get("index"));
		data1.put("Personnel_id", data.get("Personnel_id"));
		//Log.i("qwj","Personnel_id2:"+data.get("Personnel_id"));
		
		data1.put("mark", data.get("mark"));
		String value = HttpUtil.postJson_GXWenJuan(url, data.get("data"), data1);
		
		//Log.i("qwj","Personnel_id3:"+value);
		return value;
	}
	public String getWenJuanPerson(Map<String, String> data) {
		String url = "/Json/Get_Qa_UpLoad_Personnel.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 查询高校调查问卷人员信息
	 * @param data
	 * @return
	 */
	public String getGXWenJuanPerson(Map<String, String> data) {
		String url = "/Json/Get_Qa_UpLoad_Personnel_Special.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public String getWenJuanInfo(Map<String, String> data) {
		String url = "/Json/Get_Qa_Master.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取问卷信息
	 * 
	 * @return
	 */
	public String getReceiv_Special(Map<String, String> data) {
		String url = "/Json/Get_Qa_Receiv_Special.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 获取问卷说明
	 * 
	 * @return
	 */
	public String getText_Special(Map<String, String> data) {
		String url="/Json/Get_Qa_Master_Text_Special.aspx";
		
		try {
			String value = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}
	/**
	 *  获取问卷题目
	 * @return
	 */
	public String getSpecial(Map<String, String> data) {

		String url = "/Json/Get_Qa_Master_Special.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public String getWenJuanAnswerInfo(Map<String, String> data) {
		String url = "/Json/Get_Qa_Receiv.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String getXUHUIAnswerInfo(Map<String, String> data) {
		String url = "/Json/Get_Tb_Home_Answer_Info.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public String getHistoryWenjuanInfo(Map<String, String> data) {
		String url = "/Json/Get_Qa_history.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
