package cn.lawwing.jisporttactics.beans;

import android.graphics.Color;

/**
 * Created by lawwing on 2017/12/14.
 */

public class MainMenuBean {
    private String name;

    private long updatetime;

    private int bgcolor = Color.parseColor("#32b4ff");

    private int textcolor = Color.parseColor("#ffffff");

    private Class<?> gotoActivity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public int getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(int bgcolor) {
        this.bgcolor = bgcolor;
    }

    public int getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(int textcolor) {
        this.textcolor = textcolor;
    }

    public Class<?> getGotoActivity() {
        return gotoActivity;
    }

    public void setGotoActivity(Class<?> gotoActivity) {
        this.gotoActivity = gotoActivity;
    }
}
