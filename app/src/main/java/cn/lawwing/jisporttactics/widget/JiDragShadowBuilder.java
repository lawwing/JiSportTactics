package cn.lawwing.jisporttactics.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by lawwing on 2018/1/20.
 */

public class JiDragShadowBuilder extends View.DragShadowBuilder {
    private static final String TAG = "JiDragShadowBuilder";
    //拖动阴影的区域
    private static Drawable shadow;
    // 储存绘制的拖动阴影图像
    private static Bitmap newBitmap;

    public JiDragShadowBuilder(View arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
        shadow = new ColorDrawable(Color.LTGRAY);// 浅灰色
    }

    // 在该方法中绘制拖动阴影图像 实例化newBitmap变量
    @Override
    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
        // TODO Auto-generated method stub
        super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
        int width, height;
        // 设置拖动阴影的宽度/高度为原宽/高度的1.5倍,控制移动时候手和图标的距离，倍数越大就离手指越远
        width = (int) (getView().getWidth() * 1.5);
        height = (int) (getView().getHeight() * 1.5);
        // 设置拖动图像的绘制 区域
        shadow.setBounds(0, 0, width, height);
        // 设置拖动阴影图像的宽度和高度
        shadowSize.set(width, height);
        // 设置手指在拖动图像的位置 设置为中点
        shadowTouchPoint.set(width / 2, height / 2);

        if (getView() instanceof ImageView) {
            // getView()方法返回的值就是构造方法传入的arg0 参数
            ImageView imageView = (ImageView) getView();
            // 获取drawable对象
            Drawable drawable = imageView.getDrawable();
            // 获取imageview的bitmap
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            // 创建一个新的bitmap
            newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(newBitmap);
            // 将图像绘制在画布上，但现在还没有正式将图像绘制在阴影图像上，目前只是将bitmap放大并绘制在newbitmap上
            canvas.drawBitmap(newBitmap, new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()), new Rect(0, 0, width, height), null);
        }

    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDrawShadow(canvas);
        // 将图像正式绘制在阴影图像上
        Log.i(TAG, "onDrawShadow: ");
        canvas.drawBitmap(newBitmap, 0, 0, new Paint());
    }
}
