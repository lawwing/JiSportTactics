package cn.lawwing.jisporttactics.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lawwing.jisporttactics.R;
import cn.lawwing.jisporttactics.base.JiApp;
import cn.lawwing.jisporttactics.utils.ScreenUtils;

import static cn.lawwing.jisporttactics.common.StaticDatas.BASKETBALL_MODE;
import static cn.lawwing.jisporttactics.common.StaticDatas.FOOTBALL_MODE;

/**
 * Created by lawwing on 2018/2/4.
 */

public class SelectModeDialog extends DialogFragment {

    @BindView(R.id.ll_football_mode)
    LinearLayout mFootballModeBtn;
    @BindView(R.id.ll_basketball_mode)
    LinearLayout mBasketballModeBtn;
    @BindView(R.id.iv_close_btn)
    ImageView mCloseBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        getDialog().setCanceledOnTouchOutside(false); // 外部点击取消关闭
        View view = inflater.inflate(R.layout.dialog_select_mode_layout, container, false);
        ButterKnife.bind(this, view);
        mFootballModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != selectModeDialogListener) {
                    selectModeDialogListener.onModeSelect(FOOTBALL_MODE);
                    dismiss();
                }
            }
        });
        mBasketballModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != selectModeDialogListener) {
                    selectModeDialogListener.onModeSelect(BASKETBALL_MODE);
                    dismiss();
                }
            }
        });
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null)
                window.setLayout((int) (ScreenUtils.getScreenWidth(JiApp.getContext()) * 0.5), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private ISelectModeDialogListener selectModeDialogListener;

    public void setSelectModeDialogListener(ISelectModeDialogListener selectModeDialogListener) {
        this.selectModeDialogListener = selectModeDialogListener;
    }

    public interface ISelectModeDialogListener {
        public void onModeSelect(int mode);
    }
}
