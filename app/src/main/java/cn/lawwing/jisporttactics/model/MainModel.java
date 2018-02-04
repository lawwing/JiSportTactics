package cn.lawwing.jisporttactics.model;

import android.graphics.Color;

import java.util.ArrayList;

import cn.lawwing.jisporttactics.base.JiApp;
import cn.lawwing.jisporttactics.beans.MainMenuBean;
import cn.lawwing.jisporttactics.ui.MainActivity;
import cn.lawwing.jisporttactics.ui.activity.recordtactic.RecordtacticActivity;
import cn.lawwing.jisporttactics.utils.SharedPreferencesUtils;

import static cn.lawwing.jisporttactics.common.StaticDatas.FOOTBALL_MODE;

/**
 * Created by lawwing on 2018/1/19.
 */

public class MainModel implements IMainModel {
    private String[] names = {"即时战术", "录战术", "战术库", "训练安排", "阵型管理", "个人设置"};

    private int[] bgcolors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY,
            Color.GREEN, Color.RED};

    private int[] textcolors = {Color.WHITE, Color.WHITE, Color.WHITE,
            Color.BLUE, Color.WHITE, Color.WHITE};

    @Override
    public ArrayList<MainMenuBean> getMainMenu() {
        ArrayList<MainMenuBean> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            MainMenuBean bean = new MainMenuBean();
            bean.setName(names[i]);
            bean.setBgcolor(bgcolors[i]);
            bean.setTextcolor(textcolors[i]);
            bean.setUpdatetime(0);
            bean.setGotoActivity(RecordtacticActivity.class);
            list.add(bean);
        }
        return list;
    }

    @Override
    public String getTimeFormatString() {
        return "yyyy年MM月dd日\nEEEE HH:mm:ss";
    }

    @Override
    public String getModeText() {
        int mode = SharedPreferencesUtils.getAppMode(JiApp.getContext());
        if (mode == FOOTBALL_MODE) {
            return "足球·极战术";
        } else {
            return "篮球·极战术";
        }
    }
}
