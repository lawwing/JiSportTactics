package cn.lawwing.jisporttactics.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.lawwing.jisporttactics.R;
import cn.lawwing.jisporttactics.adapter.MainAdapter;
import cn.lawwing.jisporttactics.base.BaseActivity;
import cn.lawwing.jisporttactics.base.JiApp;
import cn.lawwing.jisporttactics.beans.MainMenuBean;
import cn.lawwing.jisporttactics.event.MainClickEvent;
import cn.lawwing.jisporttactics.presenter.MainParsenterImpl;
import cn.lawwing.jisporttactics.view.IMainView;

public class MainActivity extends BaseActivity implements IMainView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Unbinder unbinder;

    private MainAdapter adapter;

    MainParsenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this);
        mPresenter = new MainParsenterImpl(this);
        mPresenter.loadMainInfo();
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
    public void showMainList(ArrayList<MainMenuBean> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MainAdapter(MainActivity.this, list);
        recyclerView.setAdapter(adapter);
    }

    @Subscribe
    public void onMainMenuClick(MainClickEvent event) {
        if (event != null) {
            //    mPresenter.onItemClick(event.getPoi(), event.getMainMenuBean());
            startActivity(new Intent(MainActivity.this,
                    event.getMainMenuBean().getGotoActivity()));
        }
    }
}
