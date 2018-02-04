package cn.lawwing.jisporttactics.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import cn.lawwing.jisporttactics.paint.BitmapCtl;
import cn.lawwing.jisporttactics.paint.Circlectl;
import cn.lawwing.jisporttactics.paint.EraserCtl;
import cn.lawwing.jisporttactics.paint.ISketchpadDraw;
import cn.lawwing.jisporttactics.paint.IUndoRedoCommand;
import cn.lawwing.jisporttactics.paint.PenuCtl;
import cn.lawwing.jisporttactics.paint.RectuCtl;
import cn.lawwing.jisporttactics.paint.Triangle;
import cn.lawwing.jisporttactics.utils.BitmapUtils;

/**
 * Created by lawwing on 2018/1/19.
 */

public class SportNormalTacticsView extends View implements IUndoRedoCommand {
    private static final String TAG = "SportNormalTacticsView";
    //画笔常量
    public static final int STROKE_PEN = 1;       //画笔1
    public static final int STROKE_ERASER = 2;    //橡皮擦2
    public static final int STROKE_PLYGON = 3;   //多边形3
    public static final int STROKE_RECT = 4;      //矩形 4
    public static final int STROKE_TRIANGLE = 5;    //三角形5
    public static final int STROKE_CIRCLE = 6;    //圆 6
    public static final int STROKE_OVAL = 7;      //椭圆 7
    public static final int STROKE_LINE = 8;      //直线8
    public static final int STROKE_SPRAYGUN = 9;      //喷枪9
    public static final int STROKE_PAINTPOT = 10;     //油漆桶10

    public static final int UNDO_SIZE = 20;       //撤销栈的大小
    public static final int BITMAP_WIDTH = 650;        //画布高
    public static final int BITMAP_HEIGHT = 400;    //画布宽

    private int m_strokeType = STROKE_PEN;   //画笔风格
    public static int flag = 0;                    //油漆桶参数
    private static int m_strokeColor = Color.RED;   //画笔颜色
    private static int m_penSize = 10;         //画笔大小
    private static int m_eraserSize = 30;   //橡皮擦大小
    //实例新画布
    private boolean m_isEnableDraw = true;   //标记是否可以画
    private boolean m_isDirty = false;     //标记
    private boolean m_isTouchUp = false;    //标记是否鼠标弹起
    private boolean m_isSetForeBmp = false;   //标记是否设置了前bitmap
    private int m_bkColor = Color.TRANSPARENT;    //背景色
    private int m_boundColor = Color.TRANSPARENT;    //边框颜色

    private int m_canvasWidth = 100;    //画布宽
    private int m_canvasHeight = 100;    //画布高
    private boolean m_canClear = true;   //标记是否可清除

    //控件的宽度
    private int width;
    //控件的高度
    private int height;

    private Bitmap m_foreBitmap = null;     //用于显示的bitmap
    private Bitmap m_tempForeBitmap = null; //用于缓冲的bitmap
    private Bitmap m_bkBitmap = null;       //用于背后画的bitmap

    private Canvas m_canvas;     //画布
    private Paint m_bitmapPaint = null;   //画笔
    private SketchPadUndoStack m_undoStack = null;//栈存放执行的操作
    private ISketchpadDraw m_curTool = null;   //记录操作的对象画笔类

    int antiontemp = 0;//获取鼠标点击画布的event
    boolean myLoop = false;// 喷枪结束标识符
    //这是画板边距以内paintPaddingWidth可画(x轴),默认为20
    private int paintPaddingWidth = 20;
    //这是画板边距以内paintPaddingWidth可画(y轴),默认为20
    private int paintPaddingHeight = 20;

    public SportNormalTacticsView(Context context) {
        super(context);
        initialize();
    }

    public SportNormalTacticsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public SportNormalTacticsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null != m_bkBitmap) {
            //如果背景图不为空那就绘制背景
            drawBackoundBitmap(canvas);
        } else {
            //如果为空就是绘制背景色
            canvas.drawColor(m_bkColor);
        }
        if (null != m_foreBitmap) {
            canvas.drawBitmap(m_foreBitmap, 0, 0, m_bitmapPaint);
        }
        if (null != m_curTool) {
            if (STROKE_ERASER != m_strokeType) {
                if (!m_isTouchUp) {   //调用绘图功能
                    m_curTool.draw(canvas);
                }
            }
        }

        drawBound(canvas);
        if (hasScale) {
            canvas.scale(0.5f, 1f);
            hasScale = false;
        } else {
            canvas.scale(2.0f, 2.0f);
            hasScale = false;
        }
    }

    /**
     * 初始化
     */
    private void initialize() {
        m_canvas = new Canvas();//实例画布用于整个绘图操作
        m_bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  //实例化画笔用于bitmap设置画布canvas
        m_undoStack = new SketchPadUndoStack(this, UNDO_SIZE);//实例化队列
    }

    /**
     * 画背景
     *
     * @param canvas
     */
    private void drawBackoundBitmap(Canvas canvas) {
        //画背景色
        canvas.drawColor(m_bkColor);
        float ratio = (m_bkBitmap.getWidth() * 1.0f) / (m_bkBitmap.getHeight() * 1.0f);
        int tempheight = (int) (width / ratio);
        int paddingTop = (height - tempheight) / 2;
        //判断比例是否不符合，负数的时候则不需要padding
        paddingTop = paddingTop > 0 ? paddingTop : 0;
        RectF dst = new RectF(getLeft(), getTop() + paddingTop, getRight(), getBottom() - paddingTop);
        Rect rst = new Rect(0, 0, m_bkBitmap.getWidth(), m_bkBitmap.getHeight());
        canvas.drawBitmap(m_bkBitmap, rst, dst, m_bitmapPaint);
    }

    /**
     * 画边框
     *
     * @param canvas
     */
    private void drawBound(Canvas canvas) {
        Paint mHorPaint = new Paint();
        mHorPaint.setAntiAlias(true);
        mHorPaint.setStrokeWidth(paintPaddingHeight * 2);
        mHorPaint.setStyle(Paint.Style.FILL);
        mHorPaint.setColor(m_boundColor);

        Paint mVerPaint = new Paint();
        mVerPaint.setAntiAlias(true);
        mVerPaint.setStrokeWidth(paintPaddingWidth * 2);
        mVerPaint.setStyle(Paint.Style.FILL);
        mVerPaint.setColor(m_boundColor);

        canvas.drawLine(0, 0, width, 0, mHorPaint);
        canvas.drawLine(0, 0, 0, height, mVerPaint);
        canvas.drawLine(width, height, width, 0, mVerPaint);
        canvas.drawLine(width, height, 0, height, mHorPaint);
    }

    /**
     * 外部调用刷新画板的接口
     */
    public void refresh() {
        invalidate();
    }

    @Override
    public void undo() {
        if (null != m_undoStack) {
            m_undoStack.undo();
        }
    }

    @Override
    public void redo() {
        if (null != m_undoStack) {
            m_undoStack.redo();
        }
    }

    @Override
    public boolean canRedo() {
        if (null != m_undoStack) {
            return m_undoStack.canRedo();
        }
        return false;
    }

    @Override
    public boolean canUndo() {
        if (null != m_undoStack) {
            return m_undoStack.canUndo();
        }
        return false;
    }

    @Override
    public void onDeleteFromUndoStack() {

    }

    @Override
    public void onDeleteFromRedoStack() {

    }

    public boolean isDirty() {
        return m_isDirty;
    }

    public void setDrawStrokeEnable(boolean isEnable) {
        //确定是否可绘图
        m_isEnableDraw = isEnable;
    }

    /**
     * 设置边框颜色
     *
     * @param m_boundColor
     * @return
     */
    public SportNormalTacticsView setM_boundColor(int m_boundColor) {
        this.m_boundColor = m_boundColor;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param color
     */
    public SportNormalTacticsView setBkColor(int color) {
        if (m_bkColor != color) {
            m_bkColor = color;
            invalidate();
        }
        return this;
    }

    /**
     * 设置画笔的大小和橡皮擦大小
     *
     * @param size
     * @param type
     */
    public void setStrokeSize(int size, int type) {
        switch (type) {
            case STROKE_PEN:
                m_penSize = size;
                break;
            case STROKE_ERASER:
                m_eraserSize = size;
                break;
        }
    }

    /**
     * 获取画笔大小
     *
     * @return
     */
    public int getStrokeSize() {   //得到画笔的大小
        return m_penSize;
    }

    /**
     * 设置画笔颜色
     *
     * @param color
     */
    public void setStrokeColor(int color) {   //设置画笔颜色
        m_strokeColor = color;
    }

    /**
     * 获取画笔颜色
     *
     * @return
     */
    public int getStrokeColor() {   //得到画笔的大小
        return m_strokeColor;
    }

    public static int getEraser() {   //得到橡皮擦的大小
        return m_eraserSize;
    }

    /**
     * 清空全部画笔
     */
    public void clearAllStrokes() {   //清空设置
        if (m_canClear) {
            // 清空撤销栈
            m_undoStack.clearAll();
            // 设置当前的bitmap对象为空
            if (null != m_tempForeBitmap) {
                m_tempForeBitmap.recycle();
                m_tempForeBitmap = null;
            }
            // Create a new fore bitmap and set to canvas.
            createStrokeBitmap(m_canvasWidth, m_canvasHeight);

            invalidate();
            m_isDirty = true;
            m_canClear = false;
        }
    }

    /**
     * 保存时对当前绘图板的图片进行快照
     */
    public Bitmap getCanvasSnapshot() {
        setDrawingCacheEnabled(true);
        buildDrawingCache(true);
        Bitmap bmp = getDrawingCache(true);
        if (null == bmp) {
            Log.d(TAG, "getCanvasSnapshot getDrawingCache == null");
        }
        return BitmapUtils.duplicateBitmap(bmp, BITMAP_WIDTH, BITMAP_HEIGHT);
    }

    /**
     * 打开图像文件时，设置当前视图为foreBitmap
     */
    public void setForeBitmap(Bitmap foreBitmap) {
        if (foreBitmap != m_foreBitmap && null != foreBitmap) {
            // Recycle the bitmap.
            if (null != m_foreBitmap) {
                m_foreBitmap.recycle();
            }
            m_foreBitmap = BitmapUtils.duplicateBitmap(foreBitmap, BITMAP_WIDTH, BITMAP_HEIGHT);
            if (null != m_foreBitmap && null != m_canvas) {
                m_canvas.setBitmap(m_foreBitmap);
            }
            invalidate();
        }
    }

    /**
     * 获取前景
     *
     * @return
     */
    public Bitmap getForeBitmap() {
        return m_foreBitmap;
    }

    /**
     * 设置背景bitmap
     *
     * @param bmp
     */
    public void setBkBitmap(Bitmap bmp) {
        if (m_bkBitmap != bmp) {
            m_bkBitmap = bmp;
            //  m_bkBitmap = BitmapUtils.duplicateBitmap(bmp, BITMAP_WIDTH, BITMAP_HEIGHT);
            invalidate();
        }
    }

    /**
     * 获取背景bitmap
     *
     * @return
     */
    public Bitmap getBkBitmap() {
        return m_bkBitmap;
    }

    protected void createStrokeBitmap(int w, int h) {
        m_canvasWidth = w;
        m_canvasHeight = h;
        Bitmap bitmap = Bitmap.createBitmap(m_canvasWidth, m_canvasHeight, Bitmap.Config.ARGB_8888);
        if (null != bitmap) {
            m_foreBitmap = bitmap;
            // Set the fore bitmap to m_canvas to be as canvas of strokes.
            m_canvas.setBitmap(m_foreBitmap);
        }
    }

    protected void setTempForeBitmap(Bitmap tempForeBitmap) {
        if (null != tempForeBitmap) {
            if (null != m_foreBitmap) {
                m_foreBitmap.recycle();
            }
            m_foreBitmap = BitmapCtl.duplicateBitmap(tempForeBitmap);
            if (null != m_foreBitmap && null != m_canvas) {
                m_canvas.setBitmap(m_foreBitmap);
                invalidate();
            }
        }
    }

    /**
     * 设置画笔大小
     *
     * @param width
     * @param height
     */
    protected void setCanvasSize(int width, int height) {//设置画布大小
        if (width > 0 && height > 0) {
            if (m_canvasWidth != width || m_canvasHeight != height) {
                m_canvasWidth = width;
                m_canvasHeight = height;
                createStrokeBitmap(m_canvasWidth, m_canvasHeight);
            }
        }
    }

    /**
     * 启动设置画笔的颜色和大小    调用修改
     */
    public void setStrokeType(int type) {
        m_strokeColor = getStrokeColor();
        m_penSize = getStrokeSize();
        switch (type) {
            case STROKE_PEN:
                m_curTool = new PenuCtl(m_penSize, m_strokeColor);
                Log.i(TAG, "pen实例化");
                break;
            case STROKE_ERASER:
                m_curTool = new EraserCtl(m_eraserSize);
                break;
            case STROKE_RECT:
                m_curTool = new RectuCtl(m_penSize, m_strokeColor);
                Log.i(TAG, "Rect实例化！");
                break;
            case STROKE_CIRCLE:
                m_curTool = new
                        Circlectl(m_penSize, m_strokeColor);
                Log.i(TAG, "Circle实例化！");
                break;
            case STROKE_TRIANGLE:
                m_curTool = new Triangle(m_penSize, m_strokeColor);
                break;
        }
        //用于记录操作动作名称
        m_strokeType = type;
    }

    private boolean hasScale = false;

    /**
     * 重新加载路劲
     */
    public SportNormalTacticsView reloadPaths(boolean hasScale) {
        for (ISketchpadDraw draw : m_undoStack.getM_undoStack()) {
            draw.draw(m_canvas);
        }
        if (hasScale) {
            m_canvas.scale(0.5f, 1f);
        } else {
            m_canvas.scale(2f, 1f);
        }
        return this;
    }

    public class SketchPadUndoStack {
        private int m_stackSize = 0;   //栈大小
        private SportNormalTacticsView m_sketchPad = null;  //视图对象
        private ArrayList<ISketchpadDraw> m_undoStack = new ArrayList<ISketchpadDraw>();
        private ArrayList<ISketchpadDraw> m_redoStack = new ArrayList<ISketchpadDraw>();
        private ArrayList<ISketchpadDraw> m_removedStack = new ArrayList<ISketchpadDraw>();

        public ArrayList<ISketchpadDraw> getM_redoStack() {
            return m_redoStack;
        }

        public ArrayList<ISketchpadDraw> getM_undoStack() {
            return m_undoStack;
        }

        public ArrayList<ISketchpadDraw> getM_removedStack() {
            return m_removedStack;
        }

        public SketchPadUndoStack(SportNormalTacticsView sketchPad, int stackSize) {
            m_sketchPad = sketchPad;
            m_stackSize = stackSize;
        }

        public void push(ISketchpadDraw sketchPadTool) {
            if (null != sketchPadTool) {
                if (m_undoStack.size() == m_stackSize && m_stackSize > 0) {
                    ISketchpadDraw removedTool = m_undoStack.get(0);
                    m_removedStack.add(removedTool);
                    m_undoStack.remove(0);
                }
                m_undoStack.add(sketchPadTool);
                if (null != listener) {
                    listener.addCount();
                }
            }
        }

        //清空栈
        public void clearAll() {
            m_redoStack.clear();
            m_undoStack.clear();
            m_removedStack.clear();
            if (null != listener) {
                listener.clearCount();
            }
        }

        public void undo() {
            if (canUndo() && null != m_sketchPad) {
                Log.i(TAG, "undo点击");
                ISketchpadDraw removedTool = m_undoStack.get(m_undoStack.size() - 1);
                m_redoStack.add(removedTool);
                m_undoStack.remove(m_undoStack.size() - 1);
                if (null != listener) {
                    listener.minusCount();
                }
                if (null != m_tempForeBitmap) {
                    // Set the temporary fore bitmap to canvas.
                    m_sketchPad.setTempForeBitmap(m_sketchPad.m_tempForeBitmap);
                } else {
                    // Create a new bitmap and set to canvas.
                    m_sketchPad.createStrokeBitmap(m_sketchPad.m_canvasWidth, m_sketchPad.m_canvasHeight);
                }
                Canvas canvas = m_sketchPad.m_canvas;
                // First draw the removed tools from undo stack.
                for (ISketchpadDraw sketchPadTool : m_removedStack) {
                    sketchPadTool.draw(canvas);
                }
                for (ISketchpadDraw sketchPadTool : m_undoStack) {
                    sketchPadTool.draw(canvas);
                }
                m_sketchPad.invalidate();
            }
        }

        public void redo() {
            if (canRedo() && null != m_sketchPad) {
                ISketchpadDraw removedTool = m_redoStack.get(m_redoStack.size() - 1);
                m_undoStack.add(removedTool);
                m_redoStack.remove(m_redoStack.size() - 1);

                if (null != listener) {
                    listener.addCount();
                }
                if (null != m_tempForeBitmap) {
                    // Set the temporary fore bitmap to canvas.
                    m_sketchPad.setTempForeBitmap(m_sketchPad.m_tempForeBitmap);
                } else {
                    // Create a new bitmap and set to canvas.
                    m_sketchPad.createStrokeBitmap(m_sketchPad.m_canvasWidth, m_sketchPad.m_canvasHeight);
                }
                Canvas canvas = m_sketchPad.m_canvas;

                // First draw the removed tools from undo stack.
                for (ISketchpadDraw sketchPadTool : m_removedStack) {
                    sketchPadTool.draw(canvas);
                }
                for (ISketchpadDraw sketchPadTool : m_undoStack) {
                    sketchPadTool.draw(canvas);
                }
                m_sketchPad.invalidate();
            }
        }

        public boolean canUndo() {//
            return (m_undoStack.size() > 0);
        }

        public boolean canRedo() {//判断栈的大小
            return (m_redoStack.size() > 0);
        }
    }

    private ICountChangeListener listener;

    public void setListener(ICountChangeListener listener) {
        this.listener = listener;
    }

    /**
     * 用于计算当前有多少条画痕的监听
     */
    public interface ICountChangeListener {
        void addCount();

        void minusCount();

        void clearCount();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        if (!m_isSetForeBmp) {
            setCanvasSize(w, h);
        }
        Log.i(TAG, "Canvas");
        m_canvasWidth = w;
        m_canvasHeight = h;
        m_isSetForeBmp = false;
    }

    /**
     * 设置可画范围的边距padding,x轴
     *
     * @param paintPaddingWidth
     * @return
     */
    public SportNormalTacticsView setPaintPaddingWidth(int paintPaddingWidth) {
        this.paintPaddingWidth = paintPaddingWidth;
        return this;
    }

    /**
     * 设置可画范围的边距padding,y轴
     *
     * @param paintPaddingHeight
     * @return
     */
    public SportNormalTacticsView setPaintPaddingHeight(int paintPaddingHeight) {
        this.paintPaddingHeight = paintPaddingHeight;
        return this;
    }

    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //实例新画布
        //标记是否可以画
        if (m_isEnableDraw)   //判断是否可绘图
        {
            //触摸点的坐标，相对于控件内
            float yx = event.getX();
            float yy = event.getY();

            //防止纵横坐标超出自身view的宽高
            float width = this.getWidth();
            float height = this.getHeight();
            //获取点击事件距离控件左边的距离，即视图坐标,,控制着左边的距离
            float xLeftTop = this.getX() + paintPaddingWidth;
            //获取点击事件距离控件顶边的距离，即视图坐标
            float yLeftTop = this.getY() + paintPaddingHeight;

            //控制着右边
            float xRightBottom = xLeftTop + width - paintPaddingWidth * 2;
            float yRighBottom = yLeftTop + height - paintPaddingHeight * 2;

            if (yx < xLeftTop) {
                //左边边界
                yx = xLeftTop + m_penSize * 0.5f;
            }
            if (yx > xRightBottom) {
                //右边边界
                yx = xRightBottom - m_penSize * 0.5f;
            }
            if (yy < yLeftTop) {
                //上边边界
                yy = yLeftTop + m_penSize * 0.5f;
            }
            if (yy > yRighBottom) {
                //下边边界
                yy = yRighBottom - m_penSize * 0.5f;
            }

            m_isTouchUp = false;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //根据m_strokeType进行重新生成对象且记录下操作对象
                    setStrokeType(m_strokeType);
                    m_curTool.touchDown(yx, yy);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    m_curTool.touchMove(yx, yy);
                    //若果当前操作为橡皮擦则调用绘图操作
                    if (STROKE_ERASER == m_strokeType) {
                        m_curTool.draw(m_canvas);
                    }
                    invalidate();
                    m_isDirty = true;
                    m_canClear = true;
                    break;
                case MotionEvent.ACTION_UP:
                    m_isTouchUp = true;
                    if (m_curTool.hasDraw()) {
                        // Add to undo stack.
                        m_undoStack.push(m_curTool);
                    }
                    m_curTool.touchUp(yx, yy);
                    // Draw strokes on bitmap which is hold by m_canvas.
                    m_curTool.draw(m_canvas);
                    invalidate();
                    m_isDirty = true;
                    m_canClear = true;
                    myLoop = false;

                    break;
            }
            return true;
        } else {
            return false;
        }
    }

    public int getStrokeType() {
        return m_strokeType;
    }

    //喷枪的线程操作  修改7/26
    public void spraygunRun() {// 匿名内部内，鼠标按下不放时的操作，启动一个线程监控
        new Thread(new Runnable() {
            public void run() {
                while (myLoop) {
                    m_curTool.draw(m_canvas);
                    try {
                        Thread.sleep(50);
                        if (antiontemp == MotionEvent.ACTION_UP) {
                            myLoop = false;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate(); //在线程中更新界面
                }
            }
        }).start();
    }

    //队列实现种子递归，用于油漆桶工具   7/28
    public void seed_fill(int x, int y, int t_color, int r_color) {
        Log.i(TAG, "执行！02");
        int MAX_ROW = 400;
        int MAX_COL = 650;
        int row_size = 400;
        int col_size = 650;
        if (x < 0 || x >= col_size || y < 0 || y >= row_size || m_foreBitmap.getPixel(x, y) == r_color) {
            return;
        }
        int queue[][] = new int[MAX_ROW * MAX_COL + 1][2];
        int head = 0, end = 0;
        int tx, ty;
        /* Add node to the end of queue. */
        queue[end][0] = x;
        queue[end][1] = y;
        end++;
        while (head < end) {
            tx = queue[head][0];
            ty = queue[head][1];
            if (m_foreBitmap.getPixel(tx, ty) == t_color) {
                m_foreBitmap.setPixel(tx, ty, r_color);
            }
            /* Remove the first element from queue. */
            head++;

			/* West */
            if (tx - 1 >= 0 && m_foreBitmap.getPixel(tx - 1, ty) == t_color) {
                m_foreBitmap.setPixel(tx - 1, ty, r_color);
                queue[end][0] = tx - 1;
                queue[end][1] = ty;
                end++;
            } else if (tx - 1 >= 0 && m_foreBitmap.getPixel(tx - 1, ty) != t_color) {
                m_foreBitmap.setPixel(tx - 1, ty, r_color);


            }


			/* East */
            if (tx + 1 < col_size && m_foreBitmap.getPixel(tx + 1, ty) == t_color) {
                m_foreBitmap.setPixel(tx + 1, ty, r_color);
                queue[end][0] = tx + 1;
                queue[end][1] = ty;
                end++;
            } else if (tx + 1 < col_size && m_foreBitmap.getPixel(tx + 1, ty) != t_color) {
                m_foreBitmap.setPixel(tx + 1, ty, r_color);


            }
            /* North */
            if (ty - 1 >= 0 && m_foreBitmap.getPixel(tx, ty - 1) == t_color) {
                m_foreBitmap.setPixel(tx, ty - 1, r_color);
                queue[end][0] = tx;
                queue[end][1] = ty - 1;
                end++;
            } else if (ty - 1 >= 0 && m_foreBitmap.getPixel(tx, ty - 1) != t_color) {
                m_foreBitmap.setPixel(tx, ty - 1, r_color);
            }
            /* South */
            if (ty + 1 < row_size && m_foreBitmap.getPixel(tx, ty + 1) == t_color) {
                m_foreBitmap.setPixel(tx, ty + 1, r_color);
                queue[end][0] = tx;
                queue[end][1] = ty + 1;
                end++;
            } else if (ty + 1 < row_size && m_foreBitmap.getPixel(tx, ty + 1) != t_color) {
                m_foreBitmap.setPixel(tx, ty + 1, r_color);
            }
        }
        return;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }
}
