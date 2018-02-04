package cn.lawwing.jisporttactics.presenter;

import cn.lawwing.jisporttactics.base.BasePersenter;

/**
 * Created by lawwing on 2018/1/19.
 */

public interface IMainPrisenter extends BasePersenter {
    void loadMainInfo();

    /**
     * 初始化现在的时间以及启动
     */
    void initNowTimeArea();

    void initModeShow();
}
