package com.qs.viewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.UnsupportedEncodingException;

/**
 * 本demo是仿微信的二维码名片 本身google的二维码是一个开源的项目我们要制作一个二维码很简单 本例的作用是将图片与二维码结合，当然图片不能太大
 * ，要不然二维码读不出来。
 * 需要导入  zxing core  jar
 */
public class QrcodeBuildUtil {
    // 图片宽度的一般
    private static final int IMAGE_HALFWIDTH = 30;
    // 需要插图图片的大小 这里设定为40*40
    int[] pixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];
    // 插入到二维码里面的图片对象
    private Bitmap mBitmap;

    public Bitmap CreateQrcode(Context con, String info) {
        // 构造需要插入的图片对象
        mBitmap = ((BitmapDrawable) con.getResources().getDrawable(
                R.mipmap.ic_launcher)).getBitmap();
        // 缩放图片
        Matrix m = new Matrix();
        float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
        float sy = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getHeight();
        m.setScale(sx, sy);
        // 重新构造一个40*40的图片
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
                mBitmap.getHeight(), m, true);

        try {
            Bitmap b = cretaeBitmap(new String(info.getBytes(),
                    "ISO-8859-1"));
            mBitmap.recycle();
            mBitmap=null;
            return b;
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mBitmap.recycle();
        mBitmap=null;
        return null;
    }




    public static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    /**
     * 生成二维码
     *
     * @param
     * @return Bitmap
     * @throws WriterException
     */
    public Bitmap cretaeBitmap(String str) throws WriterException {
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败

        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, 400, 400);
        matrix = deleteWhite(matrix);

        int width = matrix.getWidth();
        int height = matrix.getHeight();


        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int halfW = width / 2;
        int halfH = height / 2;
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH
                        && y < halfH + IMAGE_HALFWIDTH) {
                    pixels[y * width + x] = mBitmap.getPixel(x - halfW + IMAGE_HALFWIDTH, y
                            - halfH + IMAGE_HALFWIDTH);
                } else {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    }
                }

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;
    }
}
