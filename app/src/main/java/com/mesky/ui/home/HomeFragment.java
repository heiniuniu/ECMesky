package com.mesky.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mesky.R;
import com.mesky.ui.common.BaseFragment;
import com.mesky.ui.home.NewFragments.BaseNewsFragment;
import com.mesky.ui.home.NewFragments.ListViewFragment;
import com.mesky.ui.home.NewFragments.ListViewFragment1;
import com.mesky.ui.home.NewFragments.ListViewFragment2;
import com.mesky.ui.home.NewFragments.ListViewFragment3;
import com.mesky.ui.home.NewFragments.ListViewFragment4;
import com.mesky.ui.home.NewFragments.ListViewFragment5;
import com.mesky.utils.DensityUtil;
import com.mesky.utils.Lg;
import com.mesky.widgets.TabsLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;

/**
 * Created by lgpeng on 2016/2/17 0017.
 */
public class HomeFragment extends BaseFragment {
    private static final String ARG_LAST_SCROLL_Y = "arg.LastScrollY";
    private ScrollableLayout mScrollableLayout;
    private View mContentView;

    /**
     * tabs的初始状态
     */
    private boolean isTabGone = true;

    @Override
    public View getContentView() {

        mContentView = View.inflate(getActivity(), R.layout.fragment_home, null);
        return mContentView;
    }
    @Override
    protected boolean showSysBarPadding() {

        return false;
    }
    @Override
    protected int getSysBarColor() {
        return R.color.system_bar_color;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        final View header = mContentView.findViewById(R.id.header);
        final TabsLayout tabs = (TabsLayout) mContentView.findViewById(R.id.tabs);
        mScrollableLayout = (ScrollableLayout) mContentView.findViewById(R.id.scrollable_layout);
        mScrollableLayout.setDraggableView(tabs);

        final ViewPager viewPager = (ViewPager) mContentView.findViewById(R.id.view_pager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), getResources(), getFragments());
        viewPager.setAdapter(adapter);

        tabs.setViewPager(viewPager);

        mScrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                return adapter.canScrollVertically(viewPager.getCurrentItem(), direction);
            }
        });

        mScrollableLayout.setOnScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int y, int oldY, int maxY) {

                final float tabsTranslationY;
                if (y < maxY) {
                    tabsTranslationY = .0F;
                } else {
                    tabsTranslationY = y - maxY;
                }
                tabs.setTranslationY(tabsTranslationY);

                header.setTranslationY(y / 2);

                Lg.d(y);

                controlTabsHeadState(y, tabs,maxY);
            }
        });

        if (savedInstanceState != null) {
            final int y = savedInstanceState.getInt(ARG_LAST_SCROLL_Y);
            mScrollableLayout.post(new Runnable() {
                @Override
                public void run() {
                    mScrollableLayout.scrollTo(0, y);
                }
            });
        }
    }

    /**
     * 处理tab 的头部隐藏状态
     * @param y
     * @param tabs
     */
    private void controlTabsHeadState(int y, TabsLayout tabs,int maxY) {
        if(!isTabGone) {

            tabs.mTabHead.setVisibility(View.GONE);
            isTabGone = true;
        }
        Lg.d(DensityUtil.dip2px(getActivity(),300));
        if(y == DensityUtil.dip2px(getActivity(),300)) {

            tabs.mTabHead.setVisibility(View.VISIBLE);
            isTabGone = false;
        }
    }

    private List<BaseNewsFragment> getFragments() {

        final FragmentManager manager = getActivity().getSupportFragmentManager();
        final ColorRandomizer colorRandomizer = new ColorRandomizer(getResources().getIntArray(R.array.fragment_colors));
        final List<BaseNewsFragment> list = new ArrayList<>();

        ListViewFragment listViewFragment
                = (ListViewFragment) manager.findFragmentByTag(ListViewFragment.TAG);
        if (listViewFragment == null) {
            listViewFragment = ListViewFragment.newInstance(colorRandomizer.next());
        }
        ListViewFragment1 listViewFragment1
                = (ListViewFragment1) manager.findFragmentByTag(ListViewFragment.TAG);
        if (listViewFragment1 == null) {

            listViewFragment1 = ListViewFragment1.newInstance(colorRandomizer.next());
        }
        ListViewFragment2 listViewFragment2
                = (ListViewFragment2) manager.findFragmentByTag(ListViewFragment2.TAG);
        if (listViewFragment2 == null) {

            listViewFragment2 = ListViewFragment2.newInstance(colorRandomizer.next());
        }
        ListViewFragment3 listViewFragment3
                = (ListViewFragment3) manager.findFragmentByTag(ListViewFragment3.TAG);
        if (listViewFragment3 == null) {
            listViewFragment3 = ListViewFragment3.newInstance(colorRandomizer.next());
        }
        ListViewFragment4 listViewFragment4
                = (ListViewFragment4) manager.findFragmentByTag(ListViewFragment4.TAG);
        if (listViewFragment4 == null) {
            listViewFragment4 = ListViewFragment4.newInstance(colorRandomizer.next());
        }
        ListViewFragment5 listViewFragment5
                = (ListViewFragment5) manager.findFragmentByTag(ListViewFragment5.TAG);
        if (listViewFragment5 == null) {
            listViewFragment5 = ListViewFragment5.newInstance(colorRandomizer.next());
        }

        Collections.addAll(list, listViewFragment,listViewFragment1,listViewFragment2,listViewFragment3,listViewFragment4,listViewFragment5);

        return list;
    }
}
