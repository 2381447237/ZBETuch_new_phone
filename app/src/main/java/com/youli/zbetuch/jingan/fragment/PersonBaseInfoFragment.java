package com.youli.zbetuch.jingan.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.CompanyPropertyInfo;
import com.youli.zbetuch.jingan.entity.GraduateInfo;
import com.youli.zbetuch.jingan.entity.IndustryInfo;
import com.youli.zbetuch.jingan.entity.JinAnStreetInfo;
import com.youli.zbetuch.jingan.entity.JwInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/16.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 从应届毕业生界面进去的个人基本信息界面
 */

public class PersonBaseInfoFragment extends BaseFragment {

    private final int SUCCESS_STREET=10001;
    private final int SUCCESS_JW=10002;
    private final int SUCCESS_SAVE=10003;
    private final int SUCCESS_NODATA=10004;
    private final int FAIL=10005;

    private GraduateInfo gInfo;

    public static final PersonBaseInfoFragment newInstance(GraduateInfo gInfo){

        PersonBaseInfoFragment fragment = new PersonBaseInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("gInfo",gInfo);
        fragment.setArguments(bundle);

        return fragment;
    }

    private View contentView;
    private EditText etSfz;//身份证
    private EditText etName;//姓名
    private EditText etSchool;//就读学校
    private EditText etMajor;//所学专业
    private EditText etDaddress;//详细地址
    private EditText etLaddress;//居住地址
    private EditText etPhoneOne;//电话1
    private EditText etPhoneTwo;//电话2
    private Button btnSave;//保存按钮

    private Spinner spSex;//性别
    private Spinner spNation;//民族
    private Spinner spPolit;//政治面貌
    private Spinner spEdu;//文化程度
    private Spinner spStreet;//街道
    private Spinner spJw;//居委

    private LinearLayout llEspecial;
    private CheckBox cbDibao;//低保
    private CheckBox cbZero;//零就业
    private CheckBox cbCanji;//残疾

    private List<JinAnStreetInfo> spStreetData=new ArrayList<>();//街道
    private List<JwInfo> spJwData=new ArrayList<>();//居委

    private Handler mHandler=new Handler() {


        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SUCCESS_STREET://街道
                    spSetAdapter("街道", msg);
                    setStreetSelect(spStreet, gInfo.getSTREET_ID());
                    getSpinnerInfo("居委");
                    break;
                case SUCCESS_JW://居委
                    spSetAdapter("居委",msg);
                    setCommitteeSelect(spJw, gInfo.getCOMMITTEE_ID());
                    break;

                case SUCCESS_SAVE://保存信息

                    EventBus.getDefault().post(new GraduateInfo());
                    if (getActivity() != null) {

                        Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case FAIL:
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case SUCCESS_NODATA:

                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       gInfo=(GraduateInfo)getArguments().getSerializable("gInfo");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_person_base_info, container, false);

        isViewCreated = true;//标记

        return contentView;
    }

    @Override
    public void lazyLoadData() {
        if (isViewCreated) {
            //逻辑都写这里面

            initViews();
        }
    }

    private void initViews() {

        etSfz = (EditText) contentView.findViewById(R.id.et_fmt_pbase_info_sfz);
        etSfz.setBackgroundResource(R.drawable.txtbgnouse);
        etName = (EditText) contentView.findViewById(R.id.et_fmt_pbase_info_name);
        etName.setBackgroundResource(R.drawable.txtbgnouse);
        etSchool = (EditText) contentView.findViewById(R.id.et_fmt_pbase_info_school);
        etMajor = (EditText) contentView.findViewById(R.id.et_fmt_pbase_info_major);
        etDaddress = (EditText) contentView.findViewById(R.id.et_fmt_pbase_info_detail_address);
        etLaddress = (EditText) contentView.findViewById(R.id.et_fmt_pbase_info_live_address);
        etPhoneOne = (EditText) contentView.findViewById(R.id.et_fmt_pbase_info_phone_one);
        etPhoneTwo = (EditText) contentView.findViewById(R.id.et_fmt_pbase_info_phone_two);
        btnSave = (Button) contentView.findViewById(R.id.btn_fmt_pbase_info_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(checkFrm()){
                  showSaveDialog();
              }

            }
        });


        spSex = (Spinner) contentView.findViewById(R.id.sp_fmt_pbase_info_sex);
        spNation = (Spinner) contentView.findViewById(R.id.sp_fmt_pbase_info_nation);
        spPolit = (Spinner) contentView.findViewById(R.id.sp_fmt_pbase_info_polit);
        spEdu = (Spinner) contentView.findViewById(R.id.sp_fmt_pbase_info_edu);
        spStreet = (Spinner) contentView.findViewById(R.id.sp_fmt_pbase_info_street);
        spJw = (Spinner) contentView.findViewById(R.id.sp_fmt_pbase_info_jw);

        llEspecial = (LinearLayout) contentView.findViewById(R.id.llEspecial);

       cbDibao= (CheckBox) contentView.findViewById(R.id.cb_fmt_pbase_info_one);
       cbZero= (CheckBox) contentView.findViewById(R.id.cb_fmt_pbase_info_two);
       cbCanji= (CheckBox) contentView.findViewById(R.id.cb_fmt_pbase_info_three);

        initDates();

    }

    private void initDates() {

        etSfz.setText(gInfo.getSFZ());
        etName.setText(gInfo.getNAME());
        etSchool.setText(gInfo.getSCHOOL());
        etMajor.setText(gInfo.getSPECIALTY());
        etDaddress.setText(gInfo.getADDRESS());
        etLaddress.setText(gInfo.getNOW_ADDRESS());
        String phoneNum = gInfo.getCONTACT_NUMBER().trim();
        String[] nums = phoneNum.split(",");
        etPhoneOne.setText(nums[0].trim());
        if (nums.length > 1) {
            etPhoneTwo.setText(nums[1].trim());
        }

        setSpinner(spSex, R.array.gradeate_sex);
        setSpinner(spNation, R.array.gradeate_nation);
        setSpinner(spPolit, R.array.gradeate_polityStyle);
        setSpinner(spEdu, R.array.gradeate_educationLevel);
        setSpinnerSelect(spSex, gInfo.getSEX());
        setSpinnerSelect(spNation, gInfo.getNATIONS().trim());
        setSpinnerSelect(spPolit, gInfo.getPOLITICAL_STATUS().trim());
        setSpinnerSelect(spEdu, gInfo.getEDU().trim());
        getSpinnerInfo("街道");

        if(gInfo.getMARK().contains(cbDibao.getText().toString().trim())){
            cbDibao.setChecked(true);
        }
        if(gInfo.getMARK().contains(cbZero.getText().toString().trim())){
            cbZero.setChecked(true);
        }
        if(gInfo.getMARK().contains(cbCanji.getText().toString().trim())){
            cbCanji.setChecked(true);
        }
    }


    /**
     * 根据名称设定下拉框的选中项
     *
     * @param spinner
     * @param value
     */
    private void setSpinnerSelect(Spinner spinner, String value) {
        if(spinner.getAdapter()!=null) {
            for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                if (spinner.getAdapter().getItem(i).toString().trim()
                        .equals(value.trim())) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
    }


    /**
     * 设置下拉框工具类
     *
     * @param spinner
     * @param dataid
     */
    private void setSpinner(Spinner spinner, int dataid) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), dataid, android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);
    }


    private void getSpinnerInfo(final String content){

        String urlSp = null;

        if(TextUtils.equals(content,"街道")){
            // http://web.youli.pw:89/Json/Get_Area.aspx?STREET=310108
            urlSp= MyOkHttpUtils.BaseUrl+"/Json/Get_Area.aspx?STREET=310108";

        }else if(TextUtils.equals(content,"居委")){
            //http://web.youli.pw:89/Json/Get_Area.aspx?COMMITTEE=6013
            urlSp=MyOkHttpUtils.BaseUrl+"/Json/Get_Area.aspx?COMMITTEE="+gInfo.getSTREET_ID();

        }

        final String finalUrlSp = urlSp;
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        Response response=MyOkHttpUtils.okHttpGet(finalUrlSp);

                        Message msg=Message.obtain();

                        if(response!=null){

                            if(response.body()!=null){

                                try {
                                    String resStr=response.body().string();

                                    if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")) {

                                        Gson gson = new Gson();
                                        if (TextUtils.equals(content, "街道")) {
                                            msg.obj = gson.fromJson(resStr, new TypeToken<List<JinAnStreetInfo>>() {
                                            }.getType());
                                            msg.what = SUCCESS_STREET;
                                        } else if (TextUtils.equals(content, "居委")) {
                                            msg.obj = gson.fromJson(resStr, new TypeToken<List<JwInfo>>() {
                                            }.getType());
                                            msg.what = SUCCESS_JW;
                                        }
                                    }else{
                                        msg.what = SUCCESS_NODATA;
                                    }
                                    mHandler.sendMessage(msg);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }else{

                                msg.what=FAIL;
                                mHandler.sendMessage(msg);
                            }

                        }else{

                            msg.what=FAIL;
                            mHandler.sendMessage(msg);

                        }

                    }
                }

        ).start();

    }

    private void spSetAdapter(String type,Message msg){

        if(getActivity()==null){
            return;
        }

        switch (type){

            case "街道":

                spStreetData.clear();
                spStreetData.add(new JinAnStreetInfo("-1","请选择","-1"));
                spStreetData.addAll((List<JinAnStreetInfo>)msg.obj);
                spStreet.setAdapter(new ArrayAdapter<JinAnStreetInfo>(getActivity(),android.R.layout.simple_list_item_1,spStreetData));

                break;

            case "居委":

                spJwData.clear();
                spJwData.add(new JwInfo(-1,"请选择"));
                spJwData.addAll((List<JwInfo>)msg.obj);
                spJw.setAdapter(new ArrayAdapter<JwInfo>(getActivity(),android.R.layout.simple_list_item_1,spJwData));

                break;
        }

    }

    /**
     * 设置选中的街道
     *
     * @param spinner
     * @param streetId
     */
    private void setStreetSelect(Spinner spinner, int streetId) {
        if(spinner.getAdapter()!=null) {
            for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                if (((JinAnStreetInfo) spinner.getAdapter().getItem(i))
                        .getID().trim().equals("" + streetId)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
    }

    /**
     * 设置选中的居委
     *
     * @param spinner
     * @param committeeId
     */
    private void setCommitteeSelect(Spinner spinner, int committeeId) {

        if(spinner.getAdapter()!=null) {
            for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                if ((((JwInfo) spinner.getAdapter().getItem(i))
                        .getID() + "").trim().equals("" + committeeId)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
    }

    /**
     * 页面验证
     *
     * @return
     */
    private boolean checkFrm() {
        if (etSfz.getText().toString().trim().length() != 18) {
            Toast.makeText(getActivity(), "身份证号必须为18位！", Toast.LENGTH_SHORT).show();
            etSfz.requestFocus();
            return false;
        }
        if (etName.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "姓名不能为空！", Toast.LENGTH_SHORT).show();
            etName.requestFocus();
            return false;
        }
        if (spSex.getSelectedItem().toString().trim().equals("请选择")) {
            Toast.makeText(getActivity(), "请选择性别！", Toast.LENGTH_SHORT).show();
            spSex.requestFocus();
            return false;
        }
        if (spNation.getSelectedItem().toString().trim().equals("请选择")) {
            Toast.makeText(getActivity(), "请选择民族！", Toast.LENGTH_SHORT).show();
            spNation.requestFocus();
            return false;
        }
        if (spPolit.getSelectedItem().toString().trim().equals("请选择")) {
            Toast.makeText(getActivity(), "请选择政治面貌！", Toast.LENGTH_SHORT).show();
            spPolit.requestFocus();
            return false;
        }
        if (spEdu.getSelectedItem().toString().trim().equals("请选择")) {
            Toast.makeText(getActivity(), "请选择文化程度！", Toast.LENGTH_SHORT).show();
            spEdu.requestFocus();
            return false;
        }
        if (etSchool.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "请输入就读学校！", Toast.LENGTH_SHORT).show();
            etSchool.requestFocus();
            return false;
        }
        if (etMajor.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "请选择所学专业！", Toast.LENGTH_SHORT).show();
            etMajor.requestFocus();
            return false;
        }
        if (spStreet.getSelectedItem().toString().trim().equals("请选择")) {
            Toast.makeText(getActivity(), "请选择街镇！", Toast.LENGTH_SHORT).show();
            spStreet.requestFocus();
            return false;
        }
        if (spJw.getSelectedItem().toString().trim().equals("请选择")) {
            Toast.makeText(getActivity(), "请选择居委会！", Toast.LENGTH_SHORT).show();
            spJw.requestFocus();
            return false;
        }
        if (etDaddress.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "请输入详细地址！", Toast.LENGTH_SHORT).show();
            etDaddress.requestFocus();
            return false;
        }
        if (etLaddress.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "请输入居住地址！", Toast.LENGTH_SHORT).show();
            etLaddress.requestFocus();
            return false;
        }
        if (!(etPhoneOne.getText().toString().trim().length() == 8 || etPhoneOne
                .getText().toString().trim().length() == 11)) {
            Toast.makeText(getActivity(), "电话号码必须是8位或11位", Toast.LENGTH_SHORT)
                    .show();
            etPhoneOne.requestFocus();
            return false;
        }
        if (!(etPhoneTwo.getText().toString().trim().length() == 8
                || etPhoneTwo.getText().toString().trim().length() == 11 || etPhoneTwo
                .getText().toString().trim().length() == 0)) {
            Toast.makeText(getActivity(), "电话号码必须是8位或11位", Toast.LENGTH_SHORT)
                    .show();
            etPhoneTwo.requestFocus();
            return false;
        }

        return true;
    }

    private void  showSaveDialog(){

        if(getActivity()!=null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("保存信息提示");
            builder.setMessage("您确定保存此毕业生信息吗?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    saveInfo();

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    }

    //保存信息
    //http://web.youli.pw:89/Json/Set_Graduate_Master.aspx
    //{ADDRESS=长安西路123弄13号, MARK=低保家庭成员,零就业家庭成员, NAME=李祯元,
    // ID=100, SPECIALTY=H, SFZ=310108199302100016, EDU=大学专科/高职, POLITICAL_STATUS=共青团员, NATIONS=汉族,
    // CONTACT_NUMBER=58785258, SEX=男, NOW_ADDRESS=长安西路123弄13号, COMMITTEE_ID=201, SURVEY=,
    // SCHOOL=D, STREET_ID=8001}
    private void saveInfo(){

        final Map<String,String> saveMap=new HashMap<String,String>();
        saveMap.put("ADDRESS",etDaddress.getText().toString().trim());

        String mark = "";
        for (int i = 0; i < llEspecial.getChildCount(); i++) {
            CheckBox box = (CheckBox) llEspecial.getChildAt(i);
            if (box.isChecked()) {
                mark += box.getText().toString().trim() + ",";
            }
        }
        if (mark.length() > 0) {
            mark = mark.substring(0, mark.length() - 1);
        }

        saveMap.put("MARK",mark);
        saveMap.put("NAME",etName.getText().toString().trim());
        saveMap.put("ID",gInfo.getID()+"");
        saveMap.put("SPECIALTY",etMajor.getText().toString().trim());
        saveMap.put("SFZ",etSfz.getText().toString().trim());
        saveMap.put("EDU",(String)spEdu.getSelectedItem());
        saveMap.put("POLITICAL_STATUS",(String)spPolit.getSelectedItem());
        saveMap.put("NATIONS",(String)spNation.getSelectedItem());
        String phoneNum = etPhoneOne.getText().toString().trim();
        if (etPhoneTwo.getText().toString().trim().length() != 0) {
            phoneNum += "," + etPhoneTwo.getText().toString().trim();
        }
        saveMap.put("CONTACT_NUMBER",phoneNum);
        saveMap.put("SEX",(String)spSex.getSelectedItem());
        saveMap.put("NOW_ADDRESS",etLaddress.getText().toString().trim());
        saveMap.put("COMMITTEE_ID",((JwInfo)spJw.getSelectedItem()).getID()+"");
        saveMap.put("SURVEY","");
        saveMap.put("SCHOOL",etSchool.getText().toString().trim());
        saveMap.put("STREET_ID",((JinAnStreetInfo)spStreet.getSelectedItem()).getID());

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {


                        String url=MyOkHttpUtils.BaseUrl+"/Json/Set_Graduate_Master.aspx";

                        Response response=MyOkHttpUtils.okHttpPostFormBody(url,(HashMap<String, String>) saveMap);


                            Message msg=Message.obtain();


                            if(response.body()!=null){


                                try {
                                    String resStr=response.body().string();

                                    if(TextUtils.equals(resStr,gInfo.getID()+"")){

                                        msg.what=SUCCESS_SAVE;


                                    }else{
                                        msg.what=FAIL;
                                    }
                                    mHandler.sendMessage(msg);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }else{

                                msg.what=FAIL;
                                mHandler.sendMessage(msg);

                            }
                    }
                }

        ).start();

    }

}