package com.youli.zbetuch.jingan.naire;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.utils.SharedPreferencesUtils;
import android.util.Base64;
import android.util.Log;

public class HttpUtil {
	// 超时时间设置
	private static final int timeout = 20 * 1000;


	public static String getRequest(String url) throws ClientProtocolException,
			IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(MyOkHttpUtils.BaseUrl + url);
		String value = "";
		try {
			//if (!HttpUrls_.staffName.trim().equals("")) {
			String cookies= SharedPreferencesUtils.getString("cookies");
				get.setHeader("cookie", cookies);
			//}
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);

			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				value = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			get.abort();
		}
		return value;

	}

	public static String postRequest(String url, Map<String, String> data)
			throws ClientProtocolException, IOException {

		Log.e("2017/10/24","来来来来了");

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(MyOkHttpUtils.BaseUrl+ url);
		try {
			//if (!HttpUrls_.staffName.trim().equals("")) {
			String cookies= SharedPreferencesUtils.getString("cookies");
				post.setHeader("cookie", cookies);
			//}
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if (data != null && data.size() > 0) {
				for (String key : data.keySet()) {
					params.add(new BasicNameValuePair(key, data.get(key)));
				}
			}

			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String value = EntityUtils.toString(response.getEntity());
				return value;
			}
		} finally {
			post.abort();
		}
		return "";
	}

//	public static String upLoadImage(String url, String filePath,
//			Map<String, String> data) throws ClientProtocolException,
//			IOException {
//		HttpClient client = new DefaultHttpClient();
//		HttpPost post = new HttpPost(MyOkHttpUtils.BaseUrl + url);
//		try {
//			//if (!HttpUrls_.staffName.trim().equals("")) {
//			String cookies= SharedPreferencesUtils.getString("cookies");
//				post.setHeader("cookie", cookies);
//			//}
//			HttpParams httpParams = client.getParams();
//			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
//			HttpConnectionParams.setSoTimeout(httpParams, timeout);
//			MultipartEntity entity = new MultipartEntity();
//			if (data != null) {
//				for (String key : data.keySet()) {
//					StringBody stringBody = new StringBody(data.get(key));
//					entity.addPart(key, stringBody);
//				}
//			}
//
//			if (filePath != null && filePath.trim().length() > 0) {
//				FileBody fileBody = new FileBody(new File(filePath));
//				entity.addPart("FILE", fileBody);
//			}
//
//			post.setEntity(entity);
//
//			HttpResponse response = client.execute(post);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				String value = EntityUtils.toString(response.getEntity());
//				return value;
//			}
//		} finally {
//			post.abort();
//		}
//		return "";
//	}

	/**
	 * �õ�ͼƬ
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static byte[] getImage(String url) throws ClientProtocolException,
			IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(MyOkHttpUtils.BaseUrl + url);
		try {
			//if (!HttpUrls_.staffName.trim().equals("")) {
			String cookies= SharedPreferencesUtils.getString("cookies");
				get.setHeader("cookie", cookies);
			//}
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);

			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				InputStream inStream = response.getEntity().getContent();
				byte[] data = IOUtil.getBytesByStream(inStream);
				return data;
			}
		} finally {
			get.abort();
		}
		return null;
	}

	public static String postJson(String url, String jsonString) {
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(MyOkHttpUtils.BaseUrl + url);
		try {
			//if (!HttpUrls_.staffName.trim().equals("")) {
			String cookies= SharedPreferencesUtils.getString("cookies");
				post.setHeader("cookie", cookies);
			//}
			byte[] json = jsonString.getBytes("utf-8");
			String str = Base64.encodeToString(json, Base64.DEFAULT);
			StringEntity stringEntity = new StringEntity(str);
			stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			post.setEntity(stringEntity);
			HttpResponse response = client.execute(post);

			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			post.abort();
		}
		return "";
	}

	public static String postJson_GXWenJuan(String url, String jsonString,
		Map<String, String> data) {
		HttpClient client = new DefaultHttpClient();
		String strhttp = MyOkHttpUtils.BaseUrl + url;
		if (data != null && data.size() > 0) {
			strhttp += "?Personnel_id=" + data.get("Personnel_id") + "&mark="
					+ data.get("mark")+"&Phone="+ data.get("Phone");
			if(data.containsKey("Receiv_id"))
			{
				strhttp+="&Receiv_id="+ data.get("Receiv_id");
			}
			if(data.containsKey("del"))
			{
				strhttp+="&del="+ data.get("del");
			}
		}

		HttpPost post = new HttpPost(strhttp);
		try {
			//if (!HttpUrls_.staffName.trim().equals("")) {
			String cookies= SharedPreferencesUtils.getString("cookies");
				post.setHeader("cookie", cookies);
			//}
			if (!"".equals(jsonString) && null != jsonString) {
				byte[] json = jsonString.getBytes("utf-8");
				String str = Base64.encodeToString(json, Base64.DEFAULT);
				StringEntity stringEntity = new StringEntity(str);
				stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				stringEntity.setContentEncoding(new BasicHeader(
						HTTP.CONTENT_ENCODING, HTTP.UTF_8));
				post.setEntity(stringEntity);
			}
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			post.abort();
		}
		return "";
	}
	
	public static String postJson_WenJuan(String url,Map<String, String> data) {

			HttpClient client = new DefaultHttpClient();
			String strhttp = MyOkHttpUtils.BaseUrl + url;
			HttpPost post = new HttpPost(strhttp);

			try {
				//if (!HttpUrls_.staffName.trim().equals("")) {
				String cookies= SharedPreferencesUtils.getString("cookies");
					post.setHeader("cookie",cookies);
				//}
			
					if (data!=null) {
				
						org.json.JSONObject jo=new org.json.JSONObject(data);
					byte[] json=jo.toString().getBytes("utf-8");
					String str = Base64.encodeToString(json, Base64.DEFAULT);
					StringEntity stringEntity = new StringEntity(str);
					stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
							"application/json"));
					stringEntity.setContentEncoding(new BasicHeader(
							HTTP.CONTENT_ENCODING, HTTP.UTF_8));
					post.setEntity(stringEntity);
				}
				HttpResponse response = client.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				post.abort();
			}
			return "";
		}


	public static String postLongRequest(String url, Map<String, String> data)
			throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(MyOkHttpUtils.BaseUrl + url);
		try {
			HttpParams httpParams = client.getParams();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if (data != null && data.size() > 0) {
				for (String key : data.keySet()) {
					params.add(new BasicNameValuePair(key, data.get(key)));
				}
			}
			System.out.println("params==>" + params);
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String value = EntityUtils.toString(response.getEntity());
				System.out.println("returnvalue===>" + value);
				return value;
			}
		} finally {
			post.abort();
		}
		return "";
	}
}
