package cn.lawwing.jisporttactics.model;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import cn.lawwing.jisporttactics.beans.MainMenuBean;

/**
 * Created by lawwing on 2018/1/19.
 */

public interface IMainModel {
    ArrayList<MainMenuBean> getMainMenu();

    ArrayList<Fragment> getMainFragment();

    String getTimeFormatString();

    String getModeText();
}
