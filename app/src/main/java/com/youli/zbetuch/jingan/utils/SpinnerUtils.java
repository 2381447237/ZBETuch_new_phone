package com.youli.zbetuch.jingan.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * 作者: zhengbin on 2017/10/19.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class SpinnerUtils {

    /**
     * 设置下拉框工具类
     *
     * @param spinner
     * @param dataid
     */
    public static  void setSpinner(Spinner spinner, int dataid, Context context) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context, dataid, android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);

    }

    /**
     * 根据名称设定下拉框的选中项
     *
     * @param spinner
     * @param value
     */
    public static void setSpinnerSelect(Spinner spinner, String value) {

        if (spinner.getAdapter() != null) {

            for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                if (spinner.getAdapter().getItem(i).toString().trim()
                        .equals(value.trim())) {
                    spinner.setSelection(i);
                    ((ArrayAdapter<CharSequence>)spinner.getAdapter()).notifyDataSetChanged();
                    break;
                }
            }
        }
    }
}
