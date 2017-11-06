package com.youli.zbetuch.jingan.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

/**
 * 作者: zhengbin on 2017/10/16.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 统计报表详情
 */

public class ReportDetailActivity extends BaseActivity{

    private String url;
    private WebView wv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        url=getIntent().getStringExtra("url");

        initViews();
    }

    private void initViews(){

        wv= (WebView) findViewById(R.id.wv_report_detail);
        loadUrl(wv);
    }

    private void loadUrl(WebView wv) {
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);

        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        // wvReport.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        // 加载数据
//        wv.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    if (progressDialog != null && progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                        progressDialog = null;
//                    }
//                }
//            }
//        });
        wv.loadUrl(url);

    }

}
