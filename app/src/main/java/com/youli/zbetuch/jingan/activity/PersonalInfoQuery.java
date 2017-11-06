package com.youli.zbetuch.jingan.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.bean.addressBean.CommitteeInfo;
import com.youli.zbetuch.jingan.bean.addressBean.RegionInfo;
import com.youli.zbetuch.jingan.bean.addressBean.StreetInfo;
import com.youli.zbetuch.jingan.entity.PersonInfo;
import com.youli.zbetuch.jingan.utils.FileUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class PersonalInfoQuery extends BaseActivity implements View.OnClickListener {


    private static final int REQUEST_CODE_CAMERA = 9999;

    private final int SUCCEED=10000;
    private final int  PROBLEM=10001;
    private List<PersonInfo> personList=new ArrayList<>();

    private Button btn_scanning;
    private EditText et_id_num;
    private Button btn_query_id_num;
    private EditText et_personName;
    private Spinner spinner_sex;
    private Spinner spinner_country;
    private Spinner spinner_street;
    private Spinner spinner_neighborhood_committee;
    private EditText et_age_from;
    private EditText et_age_to;
    private Spinner spinner_identifying;
    private Spinner spinner_status;
    private Spinner spinner_situation;
    private Spinner spinner_current_intent;
    private String[] personSex;
    private String[] spinner_sex_array;

    private Context mContext = this;
    private List<RegionInfo> regionInfoList;
    private List<StreetInfo> streetList;
    private List<CommitteeInfo> committeeInfoList;
    private ProgressDialog progressDialog;
    private List<String> streetNameList = new ArrayList<String>();
    private List<String> committeeNameList = new ArrayList<String>();
    private CheckBox cb_resource;
    private Button btn_condition_query;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED:

                    personList.clear();
                    personList.addAll((List<PersonInfo>)msg.obj);
                    Intent intent=new Intent(mContext,PersonInfoActivity.class);
                    intent.putExtra("personInfos",(Serializable)personList.get(0));
                    startActivity(intent);

                    break;


                case PROBLEM:
                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

                    break;

            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_query_layout);

        // 初始化扫描身份证的功能
        initAccessTokenWithAkSk();

        initView();
    }


    private void initAccessTokenWithAkSk(){

        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                Log.e("2017-11-2", "onResult: " + result.toString());
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Log.e("2017-11-2", "onError: " + error.getMessage());
            }
        },getApplicationContext(),"d8GYLDKFKfEv58opiaB2yLSk","W2FpYRkd49pMimAQDWR1eC3v0Ng82oUv");

    }

    private void initView() {
        et_id_num = (EditText) findViewById(R.id.et_id_num);
        btn_scanning = (Button) findViewById(R.id.btn_scanning);
        btn_scanning.setOnClickListener(this);
        btn_query_id_num = (Button) findViewById(R.id.btn_query_id_num);
        et_personName = (EditText) findViewById(R.id.et_personal_name);
        spinner_sex = (Spinner) findViewById(R.id.spinner_sex);
        spinner_country = (Spinner) findViewById(R.id.spinner_country);
        spinner_street = (Spinner) findViewById(R.id.spinner_street);
        spinner_neighborhood_committee = (Spinner) findViewById(R.id
                .spinner_neighborhood_committee);
        et_age_from = (EditText) findViewById(R.id.et_age_from);
        et_age_to = (EditText) findViewById(R.id.et_age_to);
        spinner_identifying = (Spinner) findViewById(R.id.spinner_identifying);
        spinner_status = (Spinner) findViewById(R.id.spinner_status);
        spinner_current_intent = (Spinner) findViewById(R.id.spinner_current_intent);
        spinner_situation = (Spinner) findViewById(R.id.spinner_current_situation);
        cb_resource = (CheckBox) findViewById(R.id.cb_resources);
        btn_condition_query = (Button) findViewById(R.id.btn_condition_query);
        btn_condition_query.setOnClickListener(this);
        initSpinner();
        spinner_status.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
        spinner_country.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
        spinner_street.setOnItemSelectedListener(new MySpinnerItemSelectedListener());

    }

    /**
     * 初始化 Spinner
     */
    private void initSpinner() {
        btn_query_id_num.setOnClickListener(this);
        //性别 Spinner
        setSpinner(spinner_sex, R.array.spinner_sex);
        //标识 Spinner
        setSpinner(spinner_identifying, R.array.spinner_identifying);
        //状态 Spinner
        setSpinner(spinner_status, R.array.spinner_status);
        //当前意向
        setSpinner(spinner_current_intent, R.array.spinner_current_intent);

        //初始化 区县 列表
        refreshRegion();
    }

    //刷新 区县
    private void refreshRegion() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
        }
        progressDialog.setMessage("正在加载");
        progressDialog.show();

        regionInfoList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String regionUrl = MyOkHttpUtils.BaseUrl + "/Json/Get_Area.aspx?REGION=310100";
                    String regionBody = MyOkHttpUtils.okHttpGet(regionUrl).body().string();
                    if (regionBody != null) {
                        regionInfoList = new Gson().fromJson(regionBody, new
                                TypeToken<List<RegionInfo>>
                                        () {}.getType());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<String> regionNameList = new ArrayList<>();
                                for (RegionInfo regionInfo : regionInfoList) {
                                    regionNameList.add(regionInfo.getName());
                                }
                                ArrayAdapter<String> regionArrayAdapter = new
                                        ArrayAdapter<String>(mContext, R.layout
                                        .simple_spinner_item, regionNameList);
                                regionArrayAdapter.setDropDownViewResource(android.R.layout
                                        .simple_spinner_dropdown_item);
                                spinner_country.setAdapter(regionArrayAdapter);
                                if (spinner_country.getAdapter().getCount() > 1) {
                                    spinner_country.setSelection(6);
                                }
                                //然后刷新 街道
                                refreshStreet();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //刷新街道
    private void refreshStreet() {
        int id = spinner_country.getSelectedItemPosition();
        final int regionId = regionInfoList.get(id).getRegionId();
        Log.d("RegionId", "refreshStreet: " + regionId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String streetUrl = MyOkHttpUtils.BaseUrl + "/Json/Get_Area.aspx?STREET=" +
                        regionId;
                String streetBody;
                try {
                    streetBody = MyOkHttpUtils.okHttpGet(streetUrl).body().string();
                    if (!streetBody.equals("[]")) {
                        streetList = new ArrayList<StreetInfo>();
                        streetList = new Gson().fromJson(streetBody, new
                                TypeToken<List<StreetInfo>>() {}.getType());
                        List<StreetInfo> finalStreetList = streetList;
                        streetNameList.clear();
                        streetNameList.add("请选择");
                        for (StreetInfo street : finalStreetList) {
                            streetNameList.add(street.getStreetName());
                        }
                        showSpinner(streetNameList, spinner_street);
                        //刷新 居委会 列表
                        refreshCommittee();
                    } else {
                        streetNameList.clear();
                        streetNameList.add("未找到信息");
                        showSpinner(streetNameList, spinner_street);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //刷新居委会列表
    private void refreshCommittee() {
        String streetId = null;
        if (streetList != null && streetList.size() > 2&&spinner_street.getSelectedItemPosition()!=-1) {
            streetId = streetList.get(spinner_street.getSelectedItemPosition()).getId();
            final String committeeUrl = MyOkHttpUtils.BaseUrl + "/Json/Get_Area.aspx?COMMITTEE=" +
                    streetId;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        if(MyOkHttpUtils.okHttpGet(committeeUrl)==null){
                            return;
                        }
                        if(MyOkHttpUtils.okHttpGet(committeeUrl).body()==null){
                            return;
                        }
                        Log.e("2017/10/15","body=="+MyOkHttpUtils.okHttpGet(committeeUrl).body());
                         String committeeBody = MyOkHttpUtils.okHttpGet(committeeUrl).body().string();
                        Log.e("2017/10/15","cb=="+committeeBody);
                        if (!committeeBody.equals("[]")) {
                            committeeInfoList = new ArrayList<CommitteeInfo>();
                            committeeInfoList = new Gson().fromJson(committeeBody, new
                                    TypeToken<List<CommitteeInfo>>() {}.getType());
                            committeeNameList.clear();
                            committeeNameList.add("请选择");
                            for (CommitteeInfo committeeInfo : committeeInfoList) {
                                committeeNameList.add(committeeInfo.getCommitteeName());
                            }
                            showSpinner(committeeNameList, spinner_neighborhood_committee);
                        } else {
                            Looper.prepare();
                            Toast.makeText(mContext, "居委会列表为空", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            committeeNameList.clear();
            committeeNameList.add("请选择");
            showSpinner(committeeNameList, spinner_neighborhood_committee);
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 传入 String 类型的 list ，将内容填充到 spinner 中
     *
     * @param stringList
     */
    private void showSpinner(final List<String> stringList, final Spinner spinner) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter adapter = new ArrayAdapter<String>(mContext, R
                        .layout.simple_spinner_item, stringList);
                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                if (spinner.getAdapter().getCount() > 1) {
                    spinner.setSelection(0);
                }
            }
        });

    }

    /**
     * Spinner Utils
     *
     * @param spinner 控件名
     * @param resId   数组资源 Id
     */
    private void setSpinner(Spinner spinner, int resId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, resId, R
                .layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }

    //按钮的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query_id_num://查询
                final String id_card_num = et_id_num.getText().toString().trim();
                if (id_card_num.isEmpty()) {
                    Toast.makeText(this, "身份证号码不能为空", Toast.LENGTH_SHORT).show();
                    //et_id_num.setText("");
                    return;
                } else if (id_card_num.length() < 18) {
                    Toast.makeText(this, "对不起，您所输入的身份证号码有误，请重新输入", Toast.LENGTH_SHORT).show();
                   // et_id_num.setText("");
                    return;
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = MyOkHttpUtils.BaseUrl + "/Json/Get_BASIC_INFORMATION" +
                                    ".aspx?sfz=" + id_card_num;
                            Response response = MyOkHttpUtils.okHttpGet(url);
                            try {
                                String result = response.body().string().trim();
                                if (result.equals("[null]")) {
                                    Looper.prepare();
                                    Toast.makeText(mContext, "对不起，查无此人", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                } else {
                                    // TODO 解析数据并传递给下一个Activity
                                   Gson gson=new Gson();
                                    Message msg=Message.obtain();

                                    msg.obj=gson.fromJson(result,new TypeToken<List<PersonInfo>>(){}.getType());
                                    msg.what=SUCCEED;
                                    mHandler.sendMessage(msg);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
            case R.id.btn_condition_query:
                String etAgeFrom = et_age_from.getText().toString().trim();
                String etAgeTo = et_age_to.getText().toString().trim();
                int endAge = 0;
                int startAge = 0;
                List<String> str = new ArrayList<>();
                if (!etAgeFrom.equals("") && !etAgeTo.equals("")) {
                    startAge = Integer.parseInt(etAgeFrom);
                    endAge = Integer.parseInt(etAgeTo);
                    if (startAge > endAge && endAge < 0 && startAge < 0) {
                        Toast.makeText(mContext, "你输入的年龄有误，请重新输入", Toast.LENGTH_SHORT).show();
                        et_age_from.setText("");
                        et_age_to.setText("");
                        return;
                    }
                }

                String regionName = spinner_country.getSelectedItem().toString().trim();
                int regionId = 0;
                for (RegionInfo regionInfo : regionInfoList) {
                    if (regionInfo.getName() == regionName) {
                        regionId = regionInfo.getRegionId();
                    }
                }

                str.add(String.valueOf(regionId));

                String streetName = spinner_street.getSelectedItem().toString().trim();
                int streetId = 0;
                for (StreetInfo streetInfo : streetList) {
                    if (streetInfo.getStreetName() == streetName) {
                        streetId = Integer.parseInt(streetInfo.getId());
                    }
                }

                str.add(String.valueOf(streetId));

                if(spinner_neighborhood_committee.getSelectedItem()==null){
                    return;
                }

                String committeeName = spinner_neighborhood_committee.getSelectedItem().toString().trim();
                Log.e("committeeName", "committeeName: " + committeeName);
                int committeeId = 0;
                if(committeeInfoList==null){
                    return;
                }
                for (CommitteeInfo committeeInfo : committeeInfoList) {
                    if (committeeInfo.getCommitteeName().equals(committeeName)) {
                        committeeId = committeeInfo.getId();
                    }
                }
                str.add(String.valueOf(committeeId));

                String sex = "";
                if (!spinner_sex.getSelectedItem().toString().trim().equals("全部")) {
                    sex = spinner_sex.getSelectedItem().toString().trim();
                }

                str.add(sex);


                String mark = spinner_identifying.getSelectedItemPosition() == 0 ? "" :
                        spinner_identifying.getSelectedItem().toString().trim();
                String type = spinner_status.getSelectedItemPosition() == 0 ? "" : spinner_status
                        .getSelectedItem().toString().trim();
                String situation = spinner_situation.getSelectedItemPosition() == 0 ? "" :
                        spinner_situation.getSelectedItem().toString().trim();
                String current_intent = spinner_current_intent.getSelectedItemPosition() == 0 ?
                        "" : spinner_situation.getSelectedItem().toString().trim();
                boolean resources = !cb_resource.isChecked();

                str.add(mark);
                str.add(type);
                str.add(situation);
                str.add(current_intent);

                for (String string : str) {
                    Log.e("Str", "onClick: " + string);
                }
                String url_suffix= "&name=" + et_personName.getText().toString().trim()
                        + "&sex=" + sex
                        + "&start_age=" + (startAge == 0 ? "" : startAge)
                        + "&end_age=" + (endAge == 0 ? "" : endAge)
                        + "&regionid=" + (regionId == 0 ? "" : regionId)
                        + "&STREET_ID=" + (streetId == 0 ? "" : streetId)
                        + "&COMMITTEE_ID=" + (committeeId == 0 ? "" : committeeId)
                        + "&mark=" + mark
                        + "&TYPE=" + type
                        + "&Current_situation=" + situation
                        + "&Current_intent=" + current_intent
                        + "&Resources=" + resources;
                Intent intent = new Intent(mContext, PersonalInfoQueryResult.class);
                intent.putExtra("queryUrl", url_suffix);
                startActivity(intent);
                break;
            case R.id.btn_scanning://扫描
                Intent i= new Intent(mContext, CameraActivity.class);
                i.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtils.getSaveFile(getApplication()).getAbsolutePath());
                i.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(i, REQUEST_CODE_CAMERA);
                break;
        }
    }

    /**
     * 判断当前状态，来改变摸底情况 Spinner 的列表
     *
     * @param value
     */
    private void initSpinnerSituation(String value) {
        if (value.trim().equals("登记失业")) {
            setSpinner(spinner_situation, R.array.personmodishiye);
        } else {
            setSpinner(spinner_situation, R.array.personmodiwuye);
        }
    }

    class MySpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.spinner_country:
                    refreshStreet();
                    break;
                case R.id.spinner_street:
                    refreshCommittee();
                    break;
                case R.id.spinner_status:
                    initSpinnerSituation(spinner_status.getSelectedItem().toString());
                    Log.d("TAG", "onItemSelected: " + spinner_status.getSelectedItem().toString());
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_CAMERA&&resultCode== Activity.RESULT_OK){
            if(data!=null){
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtils.getSaveFile(getApplicationContext()).getAbsolutePath();
              if(!TextUtils.isEmpty(contentType)){
                  if(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)){
                      recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                  }
              }
            }
        }
    }

    /**
     * 解析身份证图片
     *
     * @param idCardSide 身份证正反面
     * @param filePath   图片路径
     */
    private void recIDCard(String idCardSide, String filePath) {

        IDCardParams param=new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(40);

        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if(result!=null){

                  String num = "";
                    if (result.getIdNumber() != null) {
                        num = result.getIdNumber().toString();

                    }

                    final String finalNum = num;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = MyOkHttpUtils.BaseUrl + "/Json/Get_BASIC_INFORMATION" +
                                    ".aspx?sfz=" + finalNum;
                            Response response = MyOkHttpUtils.okHttpGet(url);

                            try {
                                String result = response.body().string().trim();
                                Log.e("2017/11/2","result=="+result);
                                if (result.equals("[null]")||result.equals("")) {
                                    Looper.prepare();
                                    Toast.makeText(mContext, "对不起，查无此人", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                } else {
                                    // TODO 解析数据并传递给下一个Activity
                                    Gson gson=new Gson();
                                    Message msg=Message.obtain();

                                    msg.obj=gson.fromJson(result,new TypeToken<List<PersonInfo>>(){}.getType());
                                    msg.what=SUCCEED;
                                    mHandler.sendMessage(msg);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onError(OCRError error) {
                Toast.makeText(mContext, "识别出错,请重新扫描", Toast.LENGTH_SHORT).show();
                Log.e("2017-11-2", "onError: " + error.getMessage());
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 释放内存资源
        OCR.getInstance().release();
    }
}
