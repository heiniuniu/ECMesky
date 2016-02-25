package com.mesky.ui.discovery;

import android.view.View;

import com.mesky.R;
import com.mesky.ui.common.BaseFragment;

/**
 * Created by lgpeng on 2016/2/17 0017.
 */
public class DiscoveryFragment extends BaseFragment {

    @Override
    public View getContentView() {

        return View.inflate(getActivity(),R.layout.fragment_discovery,null);
    }

    @Override
    protected boolean showSysBarPadding() {

        return true;
    }

    @Override
    protected int getSysBarColor() {
        return R.color.discovert_sysbar;
    }
}
