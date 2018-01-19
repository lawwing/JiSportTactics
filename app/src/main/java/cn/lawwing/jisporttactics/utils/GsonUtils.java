package cn.lawwing.jisporttactics.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class GsonUtils {
    /**
     * 使用gson把json字符串转化为对象
     *
     * @param gsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getEntetyByString(String gsonString, Class<T> cls) {
        T t = null;
        Gson gson = new Gson();
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> getListByString(String gsonString, Class<T> cls) {
        List<T> list = null;
        Gson gson = new Gson();
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> getListMapByString(String gsonString) {
        List<Map<String, T>> list = null;
        Gson gson = new Gson();
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> getMapByString(String gsonString) {
        Map<String, T> map = null;
        Gson gson = new Gson();
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
