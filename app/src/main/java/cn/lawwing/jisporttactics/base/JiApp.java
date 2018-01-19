package cn.lawwing.jisporttactics.base;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by lawwing on 2018/1/19.
 */

public class JiApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        CrashReport
                .initCrashReport(getApplicationContext(), "c2d1ac1967", false);
    }

    public static Context getContext() {
        return context;
    }
}
