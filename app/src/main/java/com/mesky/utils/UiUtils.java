package com.mesky.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;

/**
 * Created by lgpeng on 2016/2/20 0020.
 */
public class UiUtils {

    public static int getViewMeasuredHeight(View view) {

        view.measure(0, 0);
        return view.getMeasuredHeight();
    }

    public static int getViewMeasuredWidth(View view) {

        view.measure(0, 0);
        return view.getMeasuredWidth();
    }

    /**
     * <p>
     * 设置ListView自适应高度
     * </p>
     * <p>
     * 子item必须是LinearLayout，不能是其他的，因为其他的Layout(如RelativeLayout)没有重写onMeasure()，
     * 所以会在onMeasure()时抛出异常
     * </p>
     *
     * @param listView
     * @param attHeight
     */
    public static void setListViewHeightBasedOnChildren(ListView listView,
                                                        int attHeight) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
// pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1))
                + attHeight;
        listView.setLayoutParams(params);
    }

    public static void setEditTextHintTextSize(EditText editText, String hint,
                                               int textSize) {
// 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(hint);

// 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textSize, true);

// 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

// 设置hint
        editText.setHint(new SpannedString(ss));
    }

    public static int getSysbarHeight(Context context){

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch(Exception e1) {

            Lg.e("获取状态栏高度失败...");
            return 50;
        }
    }
}
