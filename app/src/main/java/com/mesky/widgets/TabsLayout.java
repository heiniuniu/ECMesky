package com.mesky.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mesky.R;
import com.mesky.utils.DensityUtil;

/**
 * Created by Dimitry Ivanov (mail@dimitryivanov.ru) on 29.03.2015.
 */
public class TabsLayout extends HorizontalScrollView {

    private LinearLayout mContainer;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private LayoutInflater mInflater;
    private Context mContext;

    private final Rect mRect;
    public TextView mTabHead;

    {
        mRect = new Rect();
    }

    public TabsLayout(Context context) {
        super(context);
        mContext = context;
        init(context, null);
    }

    public TabsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context, attrs);
    }

    public TabsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {

        mInflater = LayoutInflater.from(context);

        View tasLayoutContainer = View.inflate(context,R.layout.tabs_layout_container,null);
        mContainer = (LinearLayout) tasLayoutContainer.findViewById(R.id.tabs_content);
        mTabHead = (TextView)tasLayoutContainer.findViewById(R.id.tabs_head);

        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) mTabHead.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = DensityUtil.getSysbarHeight(mContext);// 控件的高强制设成20

        linearParams.width = LinearLayout.LayoutParams.MATCH_PARENT;// 控件的宽强制设成30

        mTabHead.setLayoutParams(linearParams);

        addView(tasLayoutContainer);
    }

    public void setViewPager(ViewPager pager) {

        if (mPagerAdapter != null) {
            mContainer.removeAllViews();
        }
        mPager = pager;
        mPagerAdapter = pager.getAdapter();
        populateViews();
        setItemSelected(0);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setItemSelected(position);
                mContainer.getChildAt(position).getHitRect(mRect);
                post(new Runnable() {
                    @Override
                    public void run() {
                        smoothScrollTo(mRect.left, 0);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void populateViews() {
        final int count = mPagerAdapter != null ? mPagerAdapter.getCount() : 0;
        if (count < 0) {
            return;
        }

        TextView view;
        for (int i = 0; i < count; i++) {
            view = createTabView();
            view.setText(mPagerAdapter.getPageTitle(i));
            final int position = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPager.setCurrentItem(position);
                }
            });
            mContainer.addView(view, i);
        }
    }

    private TextView createTabView() {

        TextView textView = (TextView)mInflater.inflate(R.layout.view_tab_item, mContainer, false);
        //获取屏幕的宽度
        int screenWidth = DensityUtil.getScreenWidth(mContext);
        textView.setWidth(screenWidth / 6);
        textView.setHeight(DensityUtil.dip2px(mContext,40));
        return textView;
    }

    public void setItemSelected(int position) {
        final int count = mContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            mContainer.getChildAt(i).setSelected(i == position);
        }
    }
}
