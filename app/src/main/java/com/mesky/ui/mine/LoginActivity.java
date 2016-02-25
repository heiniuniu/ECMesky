package com.mesky.ui.mine;

import android.os.Bundle;

import com.mesky.R;
import com.mesky.ui.common.BaseActivity;

/**
 * Created by lgpeng on 2016/2/17 0017.
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }
}
