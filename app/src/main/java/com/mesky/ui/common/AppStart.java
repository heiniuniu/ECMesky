package com.mesky.ui.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.mesky.MyApplication;
import com.mesky.R;
import com.mesky.ui.MainActivity;

/**
 * Created by lgpeng on 2016/2/16 0016.
 */
public class AppStart extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //点击安装包进行安装，安装结束后不点击完成，而是点击打开应用，应用启动后，再回到桌面，从桌面点击应用图标会造成反复重启应用的现象。
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            this.finish();
            return;
        }
        setContentView(R.layout.activity_app_start);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo();
            }
        }, 2000);
    }

    private void redirectTo() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (MyApplication.getInstance().isFirstRun) {

            //第一次安装使用显示欢迎、指引页
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }
}
