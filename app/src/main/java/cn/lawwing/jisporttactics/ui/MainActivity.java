package cn.lawwing.jisporttactics.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.lawwing.jisporttactics.R;
import cn.lawwing.jisporttactics.adapter.MainViewPagerAdapter;
import cn.lawwing.jisporttactics.base.BaseActivity;
import cn.lawwing.jisporttactics.base.JiApp;
import cn.lawwing.jisporttactics.beans.MainMenuBean;
import cn.lawwing.jisporttactics.dialog.SelectModeDialog;
import cn.lawwing.jisporttactics.event.MainClickEvent;
import cn.lawwing.jisporttactics.presenter.MainParsenterImpl;
import cn.lawwing.jisporttactics.utils.SharedPreferencesUtils;
import cn.lawwing.jisporttactics.utils.TimeUtils;
import cn.lawwing.jisporttactics.view.IMainView;

public class MainActivity extends BaseActivity implements IMainView, SelectModeDialog.ISelectModeDialogListener {
    @BindView(R.id.cm_now_time)
    Chronometer mNowTime;
    @BindView(R.id.tv_home_title)
    TextView mHomeTitle;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    Unbinder unbinder;

    MainParsenterImpl mPresenter;

    private MainViewPagerAdapter adapter;

    public static Intent newIntance(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this);
        mPresenter = new MainParsenterImpl(this);
        mPresenter.loadMainInfo();
        mPresenter.initNowTimeArea();
        mPresenter.initModeShow();
        JiApp.getEventBus().register(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        mPresenter = null;
        JiApp.getEventBus().unregister(this);
        super.onDestroy();
    }


    @Override
    public void showMainList(ArrayList<MainMenuBean> list, ArrayList<Fragment> fragments) {
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), list, fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initTimeView(final String timeFormatString) {
        mNowTime.setText(TimeUtils.getNowTime(timeFormatString));
        mNowTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                mNowTime.setText(TimeUtils.getNowTime(timeFormatString));
            }
        });
        mNowTime.start();
    }

    @Override
    public void showModeText(String modeText) {
        mHomeTitle.setText(modeText);
    }

    @OnClick({R.id.tv_home_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home_title:
                SelectModeDialog selectModeDialog = new SelectModeDialog();
                selectModeDialog.setSelectModeDialogListener(MainActivity.this);
                selectModeDialog.show(getFragmentManager(), "更换模式");
                break;
        }
    }

    @Subscribe
    public void onMainMenuClick(MainClickEvent event) {
        if (event != null) {
            //    mPresenter.onItemClick(event.getPoi(), event.getMainMenuBean());
            startActivity(new Intent(MainActivity.this,
                    event.getMainMenuBean().getGotoActivity()));
        }
    }

    @Override
    public void onModeSelect(int mode) {
        SharedPreferencesUtils.setIsFirstOpen(MainActivity.this, false);
        SharedPreferencesUtils.setAppMode(MainActivity.this, mode);

        mPresenter.initModeShow();
    }
}
