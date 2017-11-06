package com.youli.zbetuch.jingan.naire;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class MainService extends Service implements Runnable {

	public static String STAFFID = "";
	public static String STAFFNAME = "";

	public static List<IService> allServices = new ArrayList<IService>();
	public static List<Activity> allActivity = new ArrayList<Activity>();
	//public static List<MainTask> allTasks = new ArrayList<MainTask>();
	private boolean isrun = true;

	//MainDao mainDao = new MainDao();

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			IActivity activity = null;
			switch (msg.what) {
//			case MainTask.ZAOPINQUERYACTIVITY_GETCBOCOMPROPERTY:
//				activity = (IActivity) getActivityByName("ZaoPinQueryActivity");
//				if (activity != null) {
//					activity.refresh(
//							ZaoPinQueryActivity.REFRESH_CBOCOMPROPERTY, msg.obj);
//				}
//				break;
//			case MainTask.ZAOPINQUERYACTIVITY_GET_CBOTYPEOFWORK:
//				activity = (IActivity) getActivityByName("ZaoPinQueryActivity");
//				if (activity != null) {
//					activity.refresh(ZaoPinQueryActivity.REFRESH_CBOTYPEOFWORK,
//							msg.obj);
//				}
//				break;
//			case MainTask.ZAOPINQUERYACTIVITY_GET_CBOEDU:
//				activity = (IActivity) getActivityByName("ZaoPinQueryActivity");
//				if (activity != null) {
//					activity.refresh(
//							ZaoPinQueryActivity.REFRESH_CBOCALTURELEVEL,
//							msg.obj);
//				}
//				break;
//			case MainTask.ZAOPINQUERYACTIVITY_GET_CBOINDUSTRYCLASS:
//				activity = (IActivity) getActivityByName("ZaoPinQueryActivity");
//				if (activity != null) {
//					activity.refresh(
//							ZaoPinQueryActivity.REFRESH_CBOINDUSTRYBIGSTYLE,
//							msg.obj);
//				}
//				break;
//			case MainTask.ZAOPINQUERYACTIVITY_GET_CBOINDUSTRYSMALL:
//				activity = (IActivity) getActivityByName("ZaoPinQueryActivity");
//				if (activity != null) {
//					activity.refresh(
//							ZaoPinQueryActivity.REFRESH_CBOINDUSTRYSMALLSTYLE,
//							msg.obj);
//				}
//				break;
//			case MainTask.ZAOPINQUERYACTIVITY_GET_CBOZYFL:
//				activity = (IActivity) getActivityByName("ZaoPinQueryActivity");
//				if (activity != null) {
//					activity.refresh(
//							ZaoPinQueryActivity.REFRESH_CBOJOBBIGSTYLE, msg.obj);
//				}
//				break;
//			case MainTask.ZAOPINQUERYACTIVITY_GET_CBOZYFLCHILD:
//				activity = (IActivity) getActivityByName("ZaoPinQueryActivity");
//				if (activity != null) {
//					activity.refresh(
//							ZaoPinQueryActivity.REFRESH_CBOJOBSMALLSTYLE,
//							msg.obj);
//				}
//
//				break;
//			case MainTask.ZAOPINQUERYACTIVITY_GET_CBOGZXZ:
//				activity = (IActivity) getActivityByName("ZaoPinQueryActivity");
//				if (activity != null) {
//					activity.refresh(ZaoPinQueryActivity.REFRESH_CBOCBOJOBKIND,
//							msg.obj);
//				}
//				break;
//			case MainTask.ZAOPINQUERYACTIVITY_GET_CBOGZBS:
//				activity = (IActivity) getActivityByName("ZaoPinQueryActivity");
//				if (activity != null) {
//					activity.refresh(ZaoPinQueryActivity.REFRESH_CBOWORKTIME,
//							msg.obj);
//				}
//
//				break;
//			case MainTask.ZAOPINQUERYACTIVITY_GET_CBOAREAINFO:
//				activity = (IActivity) getActivityByName("ZaoPinQueryActivity");
//				if (activity != null) {
//					activity.refresh(ZaoPinQueryActivity.REFRESH_CBOWORKAREA,
//							msg.obj);
//				}
//				break;
//			case MainTask.MAINPOLICYACTIVITY_GET_POLICYTYPE:
//				activity = (IActivity) getActivityByName("MainPolicyActivity");
//				if (activity != null) {
//					activity.refresh(MainPolicyActivity.REFRESH_CBOTYPE,
//							msg.obj);
//				}
//				break;
//			case MainTask.POLICYASKACTIVITY_GET_POLICYTYPE:
//				activity = (IActivity) getActivityByName("PolicyAskActivity");
//				if (activity != null) {
//					activity.refresh(PolicyAskActivity.REFRESH_CBOSTYLE,
//							msg.obj);
//				}
//				break;
//			case MainTask.FILEDOWNLOADACTIVITY_GET_POLICYTYPE:
//				activity = (IActivity) getActivityByName("FileDownloadActivity");
//				if (activity != null) {
//					activity.refresh(FileDownloadActivity.REFRESH_CBOSTYLE,
//							msg.obj);
//				}
//				break;
//			case MainTask.COMPANYQUERYACTIVITY_GET_COMPANYPROPERTY:
//				activity = (IActivity) getActivityByName("CompanyQueryActivity");
//				if (activity != null) {
//					activity.refresh(
//							CompanyQueryActivity.REFRESH_CBOCOMPANYTYPE,
//							msg.obj);
//				}
//				break;
//			case MainTask.COMPANYQUERYACTIVITY_GET_CBOLINKMAN:
//				activity = (IActivity) getActivityByName("CompanyQueryActivity");
//				if (activity != null) {
//					activity.refresh(CompanyQueryActivity.REFRESH_CBOLINKMAN,
//							msg.obj);
//				}
//				break;
			}
		};
	};

//	public void doTask(MainTask task) {
//		Message message = handler.obtainMessage();
//		message.what = task.getTaskId();
//		String value = "";
//		switch (task.getTaskId()) {
//		case MainTask.ZAOPINQUERYACTIVITY_GETCBOCOMPROPERTY:
//		case MainTask.COMPANYQUERYACTIVITY_GET_COMPANYPROPERTY:
//			value = mainDao.getCompanyProperty();
//			break;
//		case MainTask.ZAOPINQUERYACTIVITY_GET_CBOTYPEOFWORK:
//			value = mainDao.getTypeOfWork();
//			break;
//		case MainTask.ZAOPINQUERYACTIVITY_GET_CBOEDU:
//			value = mainDao.getEdu();
//			break;
//		case MainTask.ZAOPINQUERYACTIVITY_GET_CBOINDUSTRYCLASS:
//			value = mainDao.getIndustryClass();
//			break;
//		case MainTask.ZAOPINQUERYACTIVITY_GET_CBOINDUSTRYSMALL:
//			value = mainDao.getIndustryChild(Integer.valueOf(task.getParams()
//					.get("parentid").toString()));
//			break;
//		case MainTask.ZAOPINQUERYACTIVITY_GET_CBOZYFL:
//			value = mainDao.getZyfl();
//			break;
//		case MainTask.ZAOPINQUERYACTIVITY_GET_CBOZYFLCHILD:
//			value = mainDao.getZyflChild(Integer.valueOf(task.getParams()
//					.get("parentid").toString()));
//			break;
//		case MainTask.ZAOPINQUERYACTIVITY_GET_CBOGZXZ:
//			value = mainDao.getGzxz();
//			break;
//		case MainTask.ZAOPINQUERYACTIVITY_GET_CBOGZBS:
//			value = mainDao.getGzbs();
//			break;
//		case MainTask.ZAOPINQUERYACTIVITY_GET_CBOAREAINFO:
//			value = mainDao.getAreaInfo();
//			break;
//		case MainTask.MAINPOLICYACTIVITY_GET_POLICYTYPE:
//		case MainTask.POLICYASKACTIVITY_GET_POLICYTYPE:
//		case MainTask.FILEDOWNLOADACTIVITY_GET_POLICYTYPE:
//			value = mainDao.getPolicyInfo();
//			break;
//		case MainTask.COMPANYQUERYACTIVITY_GET_CBOLINKMAN:
//			value = mainDao.getLinkMan();
//			break;
//		}
//		message.obj = value;
//		handler.sendMessage(message);
//		allTasks.remove(0);
//	}

	public static Activity getActivityByName(String name) {
		for (Activity activity : allActivity) {
			if (activity.getClass().getName().indexOf(name) > 0) {
				return activity;
			}
		}
		return null;
	}

	public static void addActivity(Activity activity) {
		for (int i = 0; i < allActivity.size(); i++) {
			if (activity.getClass().getName()
					.equals(allActivity.get(i).getClass().getName())) {
				allActivity.remove(i);
			}
		}
		allActivity.add(activity);
	}

//	public static void newTask(MainTask task) {
//		allTasks.add(task);
//	}

	@Override
	public void run() {
//		while (isrun) {
//			MainTask lasTask = null;
//			synchronized (allTasks) {
//				if (allTasks.size() > 0) {
//					lasTask = allTasks.get(0);
//					doTask(lasTask);
//				}
//			}
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.v("MainService", "Star MainService");
		isrun = true;
		new Thread(this).start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v("PersionService", "stop personService");
		isrun = false;
		exitApp(getApplicationContext());
	}

	/**
	 * �رճ���
	 * 
	 * @param context
	 */
	public static void exitApp(Context context) {
		for (Activity activity : allActivity) {
			activity.finish();
		}
		Intent intent = new Intent("com.fc.main.newservice.MainService");
		context.stopService(intent);
		closeAllServices(context);

	}

	public static void closeAllServices(Context context) {
		for (IService service : allServices) {
			service.exitApp(context);
		}
	}

}
