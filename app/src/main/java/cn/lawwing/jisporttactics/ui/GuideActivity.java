package cn.lawwing.jisporttactics.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import cn.lawwing.jisporttactics.R;
import cn.lawwing.jisporttactics.base.BaseActivity;
import cn.lawwing.jisporttactics.dialog.SelectModeDialog;
import cn.lawwing.jisporttactics.utils.SharedPreferencesUtils;

import static cn.lawwing.jisporttactics.common.StaticDatas.FIRST_OPEN;
import static cn.lawwing.jisporttactics.common.StaticDatas.OTHER_OPEN;

public class GuideActivity extends BaseActivity implements SelectModeDialog.ISelectModeDialogListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        boolean isFirstTime = SharedPreferencesUtils.getIsFirstOpen(GuideActivity.this);
        if (isFirstTime) {
            handler.sendEmptyMessage(FIRST_OPEN);
        } else {
            handler.sendEmptyMessageDelayed(OTHER_OPEN, 1000);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FIRST_OPEN) {
                //弹出框
                SelectModeDialog selectModeDialog = new SelectModeDialog();
                selectModeDialog.setSelectModeDialogListener(GuideActivity.this);
                selectModeDialog.show(getFragmentManager(), "选择模式");
            } else if (msg.what == OTHER_OPEN) {
                //进入首页
                startActivity(MainActivity.newIntance(GuideActivity.this));
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void onModeSelect(int mode) {
        SharedPreferencesUtils.setIsFirstOpen(GuideActivity.this, false);
        SharedPreferencesUtils.setAppMode(GuideActivity.this, mode);
        startActivity(MainActivity.newIntance(GuideActivity.this));
    }
}
