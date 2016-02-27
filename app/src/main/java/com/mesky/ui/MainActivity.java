package com.mesky.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.mesky.MyApplication;
import com.mesky.R;
import com.mesky.ui.common.AppManager;
import com.mesky.ui.common.BaseActivity;
import com.mesky.ui.discovery.DiscoveryFragment;
import com.mesky.ui.home.HomeFragment;
import com.mesky.ui.message.MessageFragment;
import com.mesky.ui.mine.LoginActivity;
import com.mesky.ui.mine.MyFragment;
import com.mesky.utils.PreferenceUtils;
import com.mesky.utils.ToastUtil;
import com.mesky.widgets.DMFragmentTabHost;

/**
 * Created by lgpeng on 2016/2/16 0016.
 */
public class MainActivity extends BaseActivity {
    /**
     * 指定当前在哪一页
     */
    public static int index;

    public static DMFragmentTabHost mTabHost = null;
    private String[] titles = null;
    private Class[] clazzs = {HomeFragment.class, DiscoveryFragment.class,DiscoveryFragment.class, MessageFragment.class, MyFragment.class};
    private int[] imagesR = {R.drawable.selector_tab_home,R.drawable.selector_tab_discovery,R.drawable.icon_tab_add,R.drawable.selector_tab_message,R.drawable.selector_tab_mine};
    private String preTab = "tabSpec0";
    /**
     * 按返回键时的时间
     */
    private long mExitTime;
    /**
     * 加载进度
     */
    private ProgressDialog progressDialog;
    private static DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        index = getIntent().getIntExtra("index", 0);
        mTabHost = (DMFragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        titles = getResources().getStringArray(R.array.bottom_titles);
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            Class clazz = clazzs[i];
            String tabSpec = "tabSpec" + i;
            View indicator = getIndicatorView(R.layout.common_tab_indicator, imagesR[i]);
            mTabHost.addTab(mTabHost.newTabSpec(tabSpec).setIndicator(indicator), clazz, null);
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                if (tabId.equals("tabSpec4")) {
                    //我的帐户页面要判断是否登录过
                    if (!MyApplication.getInstance().isLogined()) {
                        index = 0;
                        mTabHost.setCurrentTabByTag(preTab);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent); //TODO 需使用 startactivityForResult
                    } else {
                        index = mTabHost.getCurrentTab();
                        preTab = tabId;
                    }
                } else if(tabId.equals("tabSpec2")) {
                    mTabHost.setCurrentTabByTag(preTab);
                    //弹出几个按钮
                    ToastUtil.getInstant().show("弹出按钮");
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                else {
                    preTab = tabId;
                    index = mTabHost.getCurrentTab();
                }
            }
        });
        mTabHost.setCurrentTab(index);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTabHost.setCurrentTab(index);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            index = intent.getIntExtra("index", 0);
        }
        mTabHost.setCurrentTab(index);
    }

    private View getIndicatorView(int layoutId, int drawableId) {
        View v = getLayoutInflater().inflate(layoutId, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.tabImage);
        imageView.setImageResource(drawableId);
        return v;
    }
    @Override
    public boolean isSystemBarTranclucent() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按两次返回退出应用程序
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                ToastUtil.getInstant().show(R.string.app_exit);
                mExitTime = System.currentTimeMillis();
            } else {

                PreferenceUtils.setBoolean(this, "isShowedAuth", false);
                AppManager.getAppManager().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
