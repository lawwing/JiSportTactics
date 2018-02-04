package cn.lawwing.jisporttactics.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static cn.lawwing.jisporttactics.common.StaticDatas.FOOTBALL_MODE;
import static cn.lawwing.jisporttactics.common.StaticDatas.SP_KEY_APPMODE;
import static cn.lawwing.jisporttactics.common.StaticDatas.SP_KEY_ISFIRST;

/**
 * Created by lawwing on 2018/2/4.
 */

public class SharedPreferencesUtils {
    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setIsFirstOpen(Context context, boolean isOpen) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putBoolean(SP_KEY_ISFIRST, isOpen).apply();
    }

    public static boolean getIsFirstOpen(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(SP_KEY_ISFIRST, true);
    }

    /**
     * 设置应用的模式，默认是足球模式
     *
     * @param context
     * @return
     */
    public static void setAppMode(Context context, int appmode) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(SP_KEY_APPMODE, appmode).apply();
    }

    /**
     * 获取APP模式
     *
     * @param context
     * @return
     */
    public static int getAppMode(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(SP_KEY_APPMODE, FOOTBALL_MODE);
    }
}
