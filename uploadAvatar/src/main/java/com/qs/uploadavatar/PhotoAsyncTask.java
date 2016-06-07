package com.qs.uploadavatar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by xuyang on 16/6/2.
 */
public class PhotoAsyncTask extends AsyncTask<Intent,Integer,String> {
    Activity mActivity;

    public PhotoAsyncTask(Activity activity) {
        mActivity = activity;
    }

    @Override
    protected String doInBackground(Intent... params) {
        try {
           Intent mIntent=params[0];
            Uri originalUri = mIntent.getData();        //获得图片的uri

            // bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片

            // 这里开始的第二部分，获取图片的路径：

            String[] proj = {MediaStore.Images.Media.DATA};

            //好像是android多媒体数据库的封装接口，具体的看Android文档

            Cursor cursor = mActivity.managedQuery(originalUri, proj, null, null, null);

            //按我个人理解 这个是获得用户选择的图片的索引值

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            //将光标移至开头 ，这个很重要，不小心很容易引起越界

            cursor.moveToFirst();

            //最后根据索引值获取图片路径

            String path = cursor.getString(column_index);
            if(path==null&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                path=uri2filePath(mIntent.getData(),mActivity);//api  19以上  需要这种方法获取路径
            }

            if(path==null){

                return null;

            }else {
                Bitmap bitmap = setImage(path);
                String file = saveBitmapFile(bitmap);
                Log.e("tag", file + "");
                bitmap.recycle();

                return file;
            }

        }catch (Exception e) {

        }

        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public  String uri2filePath(Uri uri, Activity activity) {
        String path = "";
        if (DocumentsContract.isDocumentUri(activity, uri)) {
            String wholeID = DocumentsContract.getDocumentId(uri);
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = activity.getContentResolver().query(

                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,

                    new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {

                path = cursor.getString(columnIndex);

            }

            cursor.close();

        } else {

            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = activity.getContentResolver().query(uri,

                    projection, null, null, null);

            int column_index = cursor

                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();

            path = cursor.getString(column_index);
            cursor.close();
        }


        return path;


    }
    private Bitmap setImage(String filePath) {

        // 不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值
        // 所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看


        if (filePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);//根据Path读取资源图片
            int angle = readPictureDegree(filePath);

            if (angle != 0) {
                // 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
                Matrix m = new Matrix();
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                m.setRotate(angle); // 旋转angle度
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片

            }
            return comp(bitmap);

        }

        return null;
    }

    private Bitmap comp(Bitmap image) {//图片压缩

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        if(baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private Bitmap compressImage(Bitmap image) { //质量压缩

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public  int readPictureDegree(String path) {//部分图片会出现旋转状况，因此需要获取图片的旋转度数以复原
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation =
                    exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public String saveBitmapFile(Bitmap bitmap){//压缩的图片保存到本地
        String saveDir = mActivity.getCacheDir()
                + "/temple";
        File file = new File(saveDir);
        if (!file.exists()) {
            file.mkdir();
        }

        String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";


        file = new File(saveDir, name);

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getPath();
    }

}
