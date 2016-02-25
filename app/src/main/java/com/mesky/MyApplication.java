package com.mesky;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.SparseBooleanArray;

import com.mesky.bean.UserInfo;
import com.mesky.utils.PreferenceUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by lgpeng on 2016/2/16 0016.
 */
public class MyApplication extends Application {
    /**
     * 应用实例
     */
    private static MyApplication instance;
    /**
     * 是否调试模式
     */
    public boolean isDebug = true;

    /**
     * 是否安装后第一次使用
     */
    public boolean isFirstRun = true;

    //	/**
    //	 * 是否设置过手势密码
    //	 */
    //	public boolean hasLocked = false;

    public SparseBooleanArray hasLockedUsers = new SparseBooleanArray();
    /**
     * 当前应用包名
     */
    private String packageName = "com.mesky";
    /**
     * 用户信息
     */
    private UserInfo userInfo;

    /**
     * 手机设备号
     */
    private String imei;
    private String version;

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //		config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 返回 application对象
     *
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        isFirstRun();
        initIMEI();
        initImageLoader(getApplicationContext());
    }

    /**
     * 是否第一次使用App
     */
    private void isFirstRun() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        isFirstRun = sharedPreferences.getBoolean("isFirst", true);
    }

    /**
     * 判断是否登录过
     *
     * @return
     */
    public boolean isLogined() {
        boolean isLogined = false;
        String token = PreferenceUtils.getToken(getInstance());
        if (token == null || token.isEmpty()) {
            isLogined = false;

        } else {
            isLogined = true;
        }

        return isLogined;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * 唯一的设备ID：GSM手机的 IMEI 或 CDMA手机的 MEID.
     *
     * @see [类、类#方法、类#成员]
     */
    private void initIMEI() {
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imei = tm.getDeviceId();
    }

    public String getImei() {
        return imei;
    }

    public String getVersion() {
        if (version == null) {
            PackageManager pm = getPackageManager();
            PackageInfo pi;
            try {
                pi = pm.getPackageInfo(packageName, 0);
                version = pi.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return version;
    }

}
