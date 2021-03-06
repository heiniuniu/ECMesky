package com.mesky.utils;

import android.widget.Toast;

import com.mesky.MyApplication;

/**
 * @author jiaohongyun
 * @date 2015年7月21日
 */
public class ToastUtil {
    private static ToastUtil util;
    private Toast toast;

    public static ToastUtil getInstant() {
        if (util == null) {
            util = new ToastUtil();
        }
        return util;
    }

    /**
     * 显示Toast
     *
     * @param text
     */
    public void show(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    /**
     * 显示Toast
     *
     */
    public void show( int resId) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getInstance(), resId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(resId);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
