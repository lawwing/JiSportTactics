package cn.lawwing.jisporttactics.view;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import cn.lawwing.jisporttactics.beans.MainMenuBean;

/**
 * Created by lawwing on 2018/1/19.
 */

public interface IMainView {
    void showMainList(ArrayList<MainMenuBean> list, ArrayList<Fragment> fragments);

    /**
     * 初始化当前时间的VIEW
     *
     * @param timeFormatString
     */
    void initTimeView(String timeFormatString);

    void showModeText(String modeText);
}
