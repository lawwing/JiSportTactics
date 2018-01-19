package cn.lawwing.jisporttactics.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lawwing.jisporttactics.R;
import cn.lawwing.jisporttactics.base.JiApp;
import cn.lawwing.jisporttactics.beans.MainMenuBean;
import cn.lawwing.jisporttactics.event.MainClickEvent;
import cn.lawwing.jisporttactics.utils.ScreenUtils;

/**
 * Created by lawwing on 2017/12/13.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
    private LayoutInflater inflater;

    private Activity activity;

    private ArrayList<MainMenuBean> datas;

    private int itemHeight = 100;

    private int itemWidth = 100;

    public MainAdapter(Activity activity, ArrayList<MainMenuBean> datas) {
        this.activity = activity;
        this.datas = datas;
        inflater = LayoutInflater.from(activity);
        int screemHeight = ScreenUtils.getScreenHeight(activity);
        int screemWidth = ScreenUtils.getScreenWidth(activity);

        itemHeight = screemHeight / 2;
        itemWidth = screemWidth / 3;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_main_layout, parent, false);
        view.getLayoutParams().height = itemHeight;
        view.getLayoutParams().width = itemWidth;
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainAdapter.MainHolder holder,
                                 final int position) {
        MainMenuBean bean = datas.get(position);
        if (bean != null) {
            holder.textView.setText(bean.getName());
            holder.textView.setTextColor(bean.getTextcolor());
            holder.textView.setBackgroundColor(bean.getBgcolor());
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JiApp.getEventBus().post(
                            new MainClickEvent(position, datas.get(position)));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }

    public class MainHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.menu_name)
        TextView textView;
        @BindView(R.id.bossLayout)
        LinearLayout bossLayout;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
