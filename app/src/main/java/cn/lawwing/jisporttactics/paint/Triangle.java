package cn.lawwing.jisporttactics.paint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

/**
 * 三角形
 */
public class Triangle implements ISketchpadDraw {
    private static final String TAG = "Triangle";

    private Path m_path = new Path();
    private Paint mPaint = new Paint();
    private boolean m_hasDrawn = false;

    private float startx = 0;
    private float starty = 0;
    private float endx = 0;
    private float endy = 0;
    private boolean m_is_up = false;

    public Triangle(int penSize, int penColor) {
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(penColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(penSize);//设置画笔粗细
    }

    @Override
    public void draw(Canvas canvas) {
        if (null != canvas) {
            //m_path.lineTo(endx,endy);
            //m_path.lineTo((endx + startx) / 2, 200);

            float x = (endx - startx) / 2;
            float z = endx - startx;
            float yy = z * z - x * x;
            float y = starty - (float) Math.sqrt(yy);
            Log.i(TAG, "draw: x = " + x + " z = " + z + " yy = " + yy + " y = " + y);
            Log.i(TAG, "draw: startX = " + startx + " startY = " + starty + " endX = " + endx + " endY = " + endy);

            canvas.drawLine(startx, starty, endx, endy, mPaint);
            canvas.drawLine(endx, endy, (endx + startx) / 2, y, mPaint);
            canvas.drawLine((endx + startx) / 2, y, startx, starty, mPaint);
        }
    }

    @Override
    public boolean hasDraw() {
        return m_hasDrawn;
    }

    @Override
    public void cleanAll() {
        m_path.reset();
    }

    @Override
    public void touchDown(float x, float y) {
        m_path.moveTo(x, y);

        startx = x;
        starty = y;
        endx = x;
        endy = y;
    }

    @Override
    public void touchMove(float x, float y) {
        //  m_path.lineTo(x,y);
        m_hasDrawn = true;

        endx = x;
        endy = y;
    }

    @Override
    public void touchUp(float x, float y) {
       /* m_path.lineTo(x,y);
        m_path.close();*/
        m_is_up = true;
        endx = x;
        endy = y;
    }
}
