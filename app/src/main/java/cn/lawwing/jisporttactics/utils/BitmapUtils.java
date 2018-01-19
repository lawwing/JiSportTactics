package cn.lawwing.jisporttactics.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public class BitmapUtils {
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth()
                , drawable.getIntrinsicHeight()
                , drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        //canvas.setBitmap(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;

    }

    /**
     * 对图片生成一备份
     *
     * @param bmpSrc
     * @return
     */
    public static Bitmap duplicateBitmap(Bitmap bmpSrc, int width, int height) {
        if (null == bmpSrc) {
            return null;
        }

        int bmpSrcWidth = bmpSrc.getWidth() + 15;
        int bmpSrcHeight = bmpSrc.getHeight() + 15;

        Bitmap bmpDest = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        if (null != bmpDest) {
            Canvas canvas = new Canvas(bmpDest);
            final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);
            canvas.drawColor(Color.TRANSPARENT);
            canvas.drawBitmap(bmpSrc, rect, rect, null);
        }

        return bmpDest;
    }
}
