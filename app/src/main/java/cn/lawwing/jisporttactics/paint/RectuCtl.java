package cn.lawwing.jisporttactics.paint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 *
 */
public class RectuCtl implements ISketchpadDraw {

    private Paint mPaint = new Paint();
    private boolean m_hasDrawn = false;

    private float startx = 0;
    private float starty = 0;
    private float endx = 0;
    private float endy = 0;

    public RectuCtl(int penSize, int penColor) {
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(penColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(penSize);//设置画笔粗细
    }

    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
        if (null != canvas) {
            if (startx > endx && starty < endy) {
                canvas.drawRect(endx, starty, startx, endy, mPaint);
            } else if (startx < endx && starty < endy) {
                canvas.drawRect(startx, starty, endx, endy, mPaint);
            } else if (startx < endx && starty > endy) {
                canvas.drawRect(startx, endy, endx, starty, mPaint);
            } else if (startx > endx && starty > endy) {
                canvas.drawRect(endx, endy, startx, starty, mPaint);
            }
            //canvas.drawRect(startx,starty,endx,endy, mPaint);
            Log.i("sada022", "Rect实例");
        }
    }


    public boolean hasDraw() {
        // TODO Auto-generated method stub
        return m_hasDrawn;
        //return false;
    }


    public void cleanAll() {
        // TODO Auto-generated method stub

    }


    public void touchDown(float x, float y) {
        // TODO Auto-generated method stub
        startx = x;
        starty = y;
        endx = x;
        endy = y;
    }


    public void touchMove(float x, float y) {
        // TODO Auto-generated method stub
        endx = x;
        endy = y;
        m_hasDrawn = true;//表示已经操作了
    }


    public void touchUp(float x, float y) {
        // TODO Auto-generated method stub
        endx = x;
        endy = y;
    }

}
