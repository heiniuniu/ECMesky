package com.mesky.ui.common;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mesky.R;
import com.mesky.utils.UiUtils;
import com.mesky.widgets.LoadingDialog;

/**
 * Created by lgpeng on 2016/2/17 0017.
 */
public abstract class BaseFragment extends Fragment {

    private LoadingDialog loadingDialog;

    /**
     * 保存上一次点击时间
     */
    private SparseArray<Long> lastClickTimes;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        loadingDialog = new LoadingDialog(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lastClickTimes = new SparseArray<Long>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout baseLayout = (FrameLayout) inflater.inflate(R.layout.fragment_base,container,false);
        baseLayout.setBackgroundColor(getSysBarColor());
        //设置padding属性
        if(showSysBarPadding()) {
            
            baseLayout.setPadding(0, UiUtils.getSysbarHeight(getActivity()), 0, 0);
        }
        baseLayout.addView(getContentView());
        return baseLayout;
    }

    protected abstract boolean showSysBarPadding();

    protected abstract int getSysBarColor();

    public abstract View getContentView();

    @Override
    public void onDestroy() {
        super.onDestroy();
        lastClickTimes = null;
    }

    /**
     * 检查是否可执行点击操作
     * 防重复点击
     *
     * @return 返回true则可执行
     */
    protected boolean checkClick(int id) {
        Long lastTime = lastClickTimes.get(id);
        Long thisTime = System.currentTimeMillis();
        lastClickTimes.put(id, thisTime);
        if (lastTime != null && thisTime - lastTime < 800) {
            //快速双击，第二次不处理
            //            Toast.makeText(this, "亲，你点的太快了，请慢点儿！", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void showLoadingDialog() {
        loadingDialog.show(getString(R.string.loading));
    }

    public void showLoadingDialog(String content) {
        loadingDialog.show(content);
    }

    public void dimissLoadingDialog() {
        loadingDialog.dismiss();
    }
}
