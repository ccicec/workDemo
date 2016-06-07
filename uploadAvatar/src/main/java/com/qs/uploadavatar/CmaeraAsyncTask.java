package com.qs.uploadavatar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by xuyang on 16/6/2.
 */
public class CmaeraAsyncTask extends AsyncTask<Intent,Integer,String> {

    public Context mContext;

    public CmaeraAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(Intent... params) {
        Bitmap photo=null;
        Uri uri = params[0].getData();
        File file=null;
        if (uri != null) {

            photo = BitmapFactory.decodeFile(uri.getPath());
        }
        if (photo == null) {
            Bundle bundle = params[0].getExtras();
            if (bundle != null) {
                photo = (Bitmap) bundle.get("data");
            } else {

                return null;
            }
        }
        String pictureDir = "";
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream baos = null;

        try {
            baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            String saveDir = mContext.getCacheDir()
                    + "/temple";
            File dir = new File(saveDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";


            file = new File(saveDir, name);
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteArray);
            pictureDir = file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return pictureDir;
    }
}
