package com.mesky.ui.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mesky.MyApplication;
import com.mesky.R;
import com.mesky.ui.MainActivity;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by lgpeng on 2016/2/16 0016.
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager viewPager;

    private MyPagerAdapter myAdapter;

    private LayoutInflater mInflater;

    private ArrayList<View> views;

    private ImageView point1;

    private ImageView point2;

    private ImageView point3;

    private int[] images = {R.drawable.welcome_1,R.drawable.welcome_2,R.drawable.welcome_3};

    private ImageView start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome);
        initView();
    }

    /**
     * 初始化视图
     */
    @SuppressLint("InflateParams")
    @Override
    protected void initView() {
        findViewById(R.id.welcomeClose).setOnClickListener(this);
        point1 = (ImageView) findViewById(R.id.loading_point_1);
        point2 = (ImageView) findViewById(R.id.loading_point_2);
        point3 = (ImageView) findViewById(R.id.loading_point_3);

        point1.setOnClickListener(this);
        point2.setOnClickListener(this);
        point3.setOnClickListener(this);

        views = new ArrayList<View>(2);
        myAdapter = new MyPagerAdapter(views);
        viewPager = (ViewPager) findViewById(R.id.welcome_viewpager);
        mInflater = getLayoutInflater();
        for (int i = 0; i < 3; i++) {

            ImageView imageView = new ImageView(this);
            InputStream inputStream = this.getResources().openRawResource(images[i]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            views.add(imageView);
        }

        start = (ImageView) findViewById(R.id.start_btn);
        start.setOnClickListener(this);
        viewPager.setAdapter(myAdapter);
        //初始化当前显示的view
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 0) {
                    start.setVisibility(View.GONE);
                    point1.setImageResource(R.drawable.icon_page_yellow_dot);
                    point2.setImageResource(R.drawable.icon_page_blue_dot);
                    point3.setImageResource(R.drawable.icon_page_blue_dot);
                } else if (arg0 == 1) {
                    start.setVisibility(View.GONE);
                    point2.setImageResource(R.drawable.icon_page_yellow_dot);
                    point1.setImageResource(R.drawable.icon_page_blue_dot);
                    point3.setImageResource(R.drawable.icon_page_blue_dot);
                } else if (arg0 == 2) {
                    start.setVisibility(View.VISIBLE);
                    point3.setImageResource(R.drawable.icon_page_yellow_dot);
                    point1.setImageResource(R.drawable.icon_page_blue_dot);
                    point2.setImageResource(R.drawable.icon_page_blue_dot);
                } else {
                    start.setVisibility(View.GONE);
                    point1.setImageResource(R.drawable.icon_page_blue_dot);
                    point2.setImageResource(R.drawable.icon_page_blue_dot);
                    point3.setImageResource(R.drawable.icon_page_blue_dot);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!checkClick(v.getId())) {
            return;
        }
        switch (v.getId()) {
            case R.id.loading_point_1:
                viewPager.setCurrentItem(0);
                point1.setImageResource(R.drawable.icon_page_yellow_dot);
                point2.setImageResource(R.drawable.icon_page_blue_dot);
                point3.setImageResource(R.drawable.icon_page_blue_dot);
                break;
            case R.id.loading_point_2:
                viewPager.setCurrentItem(1);
                point2.setImageResource(R.drawable.icon_page_yellow_dot);
                point1.setImageResource(R.drawable.icon_page_blue_dot);
                point3.setImageResource(R.drawable.icon_page_blue_dot);
                break;
            case R.id.loading_point_3:
                viewPager.setCurrentItem(2);
                point3.setImageResource(R.drawable.icon_page_yellow_dot);
                point1.setImageResource(R.drawable.icon_page_blue_dot);
                point2.setImageResource(R.drawable.icon_page_blue_dot);
                break;
            case R.id.start_btn:
                gotoMainPage();
                break;
            case R.id.welcomeClose:
                gotoMainPage();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isSystemBarTranclucent() {
        return false;
    }

    /**
     * 跳转到首页
     */
    private void gotoMainPage() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isFirst", false).apply();
        MyApplication.getInstance().isFirstRun = false;
        //跳转到首页
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //退出应用
            AppManager.getAppManager().AppExit(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyPagerAdapter extends PagerAdapter {
        // 界面列表
        private ArrayList<View> views;

        public MyPagerAdapter(ArrayList<View> views) {
            this.views = views;
        }

        /**
         * 获得当前界面数
         */
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        /**
         * 初始化position位置的界面
         */
        @Override
        public Object instantiateItem(View view, int position) {
            ((ViewPager) view).addView(views.get(position), 0);
            View temp = views.get(position);
            temp.setVisibility(View.VISIBLE);
            return temp;
        }

        /**
         * 判断是否由对象生成界面
         */
        @Override
        public boolean isViewFromObject(View view, Object arg1) {
            return (view == arg1);
        }

        /**
         * 销毁position位置的界面
         */
        @Override
        public void destroyItem(View view, int position, Object arg2) {
            ((ViewPager) view).removeView(views.get(position));
        }
    }
}
