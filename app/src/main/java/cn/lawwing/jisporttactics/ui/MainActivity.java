package cn.lawwing.jisporttactics.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.lawwing.jisporttactics.R;
import cn.lawwing.jisporttactics.widget.SportNormalTacticsView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.sportNormalTacticsView)
    SportNormalTacticsView sportNormalTacticsView;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        sportNormalTacticsView.setBackgroundColor(Color.RED);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
