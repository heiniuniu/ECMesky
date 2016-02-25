package com.mesky.ui.common;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mesky.R;
import com.mesky.widgets.LoadingDialog;

/**
 * Created by lgpeng on 2016/2/16 0016.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    /**
     * 保存上一次点击时间
     */
    private SparseArray<Long> mLastClickTimes;
    private LoadingDialog loadingDialog;
    protected SystemBarTintManager tintManager;
    boolean isActive = true;// activity是否活动
    /**
     * 上一次退到后台时间
     */
    private long lastActive = 0L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //添加activity 到堆栈
        AppManager.getAppManager().addActivity(this);
        mLastClickTimes = new SparseArray<>();
        loadingDialog = new LoadingDialog(this);
        // 设置了全屏的界面需要排除

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && !(this instanceof WelcomeActivity)&&!(this instanceof AppStart)) {
            setTranslucentStatus(isSystemBarTranclucent());
            tintManager = new SystemBarTintManager(this);
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintResource(isSystemBarTranclucent()?R.color.system_bar_color:R.color.main_color);
        }
    }
    /**
     * APP字体大小，不随系统的字体大小的变化而变化的方法
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
    @Override
    protected void onDestroy() {
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        mLastClickTimes = null;
        if (null != loadingDialog) {
            dimissLoadingDialog();
            loadingDialog = null; // 加快gc
        }
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 检查是否可执行点击操作
     * 防重复点击
     *
     * @return 返回true则可执行
     */
    protected boolean checkClick(int id) {
        Long lastTime = mLastClickTimes.get(id);
        Long thisTime = System.currentTimeMillis();
        mLastClickTimes.put(id, thisTime);
        if (lastTime != null && thisTime - lastTime < 800) {
            // 快速双击，第二次不处理
            return false;
        } else {
            return true;
        }
    }

    /**
     * 初始化页面元素
     */
    protected void initView() {
        // 设置返回按钮事件
//        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    public void showLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.show(getString(R.string.loading));
        }
    }

    public void showLoadingDialog(String content) {
        if (null != loadingDialog) {
            loadingDialog.show(content);
        }
    }

    public void dimissLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public abstract boolean isSystemBarTranclucent();
}
