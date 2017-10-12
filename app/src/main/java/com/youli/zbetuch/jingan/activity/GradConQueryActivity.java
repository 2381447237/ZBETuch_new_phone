package com.youli.zbetuch.jingan.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;

/**
 * 作者: zhengbin on 2017/10/11.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 应届毕业生里面的条件查询界面
 */

public class GradConQueryActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=GradConQueryActivity.this;
    private Button btnQuery;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradcon_query);

        initViews();
    }

    private void initViews(){

        btnQuery= (Button) findViewById(R.id.btn_gradcon_query_find);
        btnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_gradcon_query_find:

                Toast.makeText(mContext,"查询",Toast.LENGTH_SHORT).show();

                break;

        }

    }
}
