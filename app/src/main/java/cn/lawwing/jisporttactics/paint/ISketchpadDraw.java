package cn.lawwing.jisporttactics.paint;

import android.graphics.Canvas;

/**
 * Created by Administrator on 2018/1/12 0012.
 * <p>
 * 绘图相关接口
 */

public interface ISketchpadDraw {
    public void draw(Canvas canvas);

    public boolean hasDraw();

    public void cleanAll();

    public void touchDown(float x, float y);

    public void touchMove(float x, float y);

    public void touchUp(float x, float y);
}
