package com.mesky.ui.mine;

import android.view.View;

import com.mesky.R;
import com.mesky.ui.common.BaseFragment;

/**
 * Created by lgpeng on 2016/2/17 0017.
 */
public class MyFragment extends BaseFragment {

    @Override
    protected boolean showSysBarPadding() {
        return true;
    }

    @Override
    protected int getSysBarColor() {
        return R.color.system_bar_color;
    }

    @Override
    public View getContentView() {

        return View.inflate(getActivity(),R.layout.fragment_my,null);
    }
}
