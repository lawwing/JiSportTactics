package cn.lawwing.jisporttactics.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/4 0004.
 */

public class TimeUtils {
    /**
     * 获取当前时间
     *
     * @param formatString 格式化后的样式
     * @return
     */
    public static String getNowTime(String formatString) {
        long nowTimeSteam = 0;
        nowTimeSteam = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        String resultString = simpleDateFormat.format(new Date(nowTimeSteam));
        return resultString;
    }
}
