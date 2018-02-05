package cn.lawwing.jisporttactics.ui.fragment.mainmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lawwing.jisporttactics.R;
import cn.lawwing.jisporttactics.adapter.MainAdapter;
import cn.lawwing.jisporttactics.base.BaseFragment;
import cn.lawwing.jisporttactics.beans.MainMenuBean;
import cn.lawwing.jisporttactics.ui.activity.recordtactic.RecordtacticActivity;

/**
 * Created by lawwing on 2018/2/5.
 */

public class MainTrainTacticFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MainAdapter adapter;
    private ArrayList<MainMenuBean> datas;

    private String[] names = {"角球战术", "任意球战术", "自由训练战术"};

    private int[] bgcolors = {Color.BLACK, Color.RED, Color.BLUE};

    private int[] textcolors = {Color.WHITE, Color.WHITE, Color.WHITE};

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        initDatas();
        initRecyclerView();
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), datas.size());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MainAdapter(getActivity(), datas);
        recyclerView.setAdapter(adapter);

    }

    private void initDatas() {
        datas = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            MainMenuBean bean = new MainMenuBean();
            bean.setName(names[i]);
            bean.setBgcolor(bgcolors[i]);
            bean.setTextcolor(textcolors[i]);
            bean.setUpdatetime(0);
            bean.setGotoActivity(RecordtacticActivity.class);
            datas.add(bean);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_common_fragment;
    }
}
