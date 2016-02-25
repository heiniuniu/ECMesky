package com.mesky.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lgpeng on 2016/2/16 0016.
 */
public class PreferenceUtils {
    public static final String CHECK_TOKEN = "token";

    public static SharedPreferences getSharedPreferences(Context ctx) {
        return ctx.getSharedPreferences("easycode", Context.MODE_PRIVATE);
    }

    public static void setToken(Context ctx, String token) {
        getSharedPreferences(ctx).edit().putString(CHECK_TOKEN, token).apply();
    }

    public static String getToken(Context ctx) {
        return getSharedPreferences(ctx).getString(CHECK_TOKEN, "");
    }

    /**
     * 存放boolean值
     */
    public static void setBoolean(Context ctx, String key, boolean value) {
        getSharedPreferences(ctx).edit().putBoolean(key, value).apply();
    }

    /**
     * 取出boolean值
     */
    public static boolean getBoolean(Context ctx, String key) {
        return getSharedPreferences(ctx).getBoolean(key, false);
    }
}
