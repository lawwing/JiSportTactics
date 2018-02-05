package cn.lawwing.jisporttactics.presenter;

import cn.lawwing.jisporttactics.model.IMainModel;
import cn.lawwing.jisporttactics.model.MainModel;
import cn.lawwing.jisporttactics.view.IMainView;

/**
 * Created by lawwing on 2018/1/19.
 */

public class MainParsenterImpl implements IMainPrisenter {

    private IMainView mainView;
    private IMainModel mainModel;

    public MainParsenterImpl(IMainView mainView) {
        this.mainView = mainView;
        mainModel = new MainModel();
    }

    @Override
    public void loadMainInfo() {
        mainView.showMainList(mainModel.getMainMenu(),mainModel.getMainFragment());
    }

    @Override
    public void initNowTimeArea() {
        mainView.initTimeView(mainModel.getTimeFormatString());
    }

    @Override
    public void initModeShow() {
        mainView.showModeText(mainModel.getModeText());
    }
}
