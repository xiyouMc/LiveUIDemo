package top.codemc.liveappuidemo.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;

/**
 * Created by xiyoumc on 16/8/8.
 */
public class ScaleUtil {

    public static double smallScale = 0.85;
    public static double bigScale = 0;
    private static float scaleWidth = 1, scaleHeight = 1;

    /**
     * 缩放 View
     *
     * @param view  需要缩放的View
     * @param scale 缩放率
     */
    public static synchronized void scale(ImageView view, double scale, Bitmap touchBmp) {
        int bmpWidth = touchBmp.getWidth();
        int bmpHeight = touchBmp.getHeight();

        scaleWidth = (float) (scaleWidth * scale);
        scaleHeight = (float) (scaleHeight * scale);

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizeBmp = Bitmap.createBitmap(touchBmp, 0, 0, bmpWidth, bmpHeight, matrix, true);

        if (scale < 1 && bigScale == 0) {
            bigScale = (double) bmpHeight / (double) resizeBmp.getHeight();
        }
        view.setImageBitmap(resizeBmp);
    }
}
