package cn.lawwing.jisporttactics.ui.activity.recordtactic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.lawwing.jisporttactics.R;
import cn.lawwing.jisporttactics.base.BaseActivity;
import cn.lawwing.jisporttactics.widget.JiDragShadowBuilder;
import cn.lawwing.jisporttactics.widget.SportNormalTacticsView;

/**
 * 录制战术的页面
 */
public class RecordtacticActivity extends BaseActivity {
    private static String TAG = "RecordtacticActivity";

    Unbinder unbinder;

    @BindView(R.id.sportTacticView)
    SportNormalTacticsView sportTacticView;
    @BindView(R.id.iv)
    ImageView imageView;

    @BindView(R.id.testLayout)
    LinearLayout testLayout;

    public static Intent getInstances(Activity activity) {
        Intent intent = new Intent(activity, RecordtacticActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this);
        sportTacticView.setDrawStrokeEnable(true);
        sportTacticView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (testLayout.getVisibility() == View.VISIBLE) {
                    sportTacticView.reloadPaths(false).refresh();
                } else {
                    sportTacticView.reloadPaths(true).refresh();

                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recordtactic;
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv:
                if (testLayout.getVisibility() == View.VISIBLE) {
                    testLayout.setVisibility(View.GONE);
                } else {
                    testLayout.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

}
