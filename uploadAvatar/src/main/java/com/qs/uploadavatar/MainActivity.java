package com.qs.uploadavatar;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;

    @Bind(R.id.camera)
    Button mCamera;
    @Bind(R.id.photo)
    Button mPhoto;
    @Bind(R.id.test_image)
    ImageView mTestImage;
    public final int CAMERA_CODE=1;//拍照
    public final int  PHOTO_CODE = 2;//选择照片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getPermission();
    }

    @OnClick({R.id.camera, R.id.photo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_CODE);

                break;
            case R.id.photo:
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType("image/*");
                startActivityForResult(getAlbum, PHOTO_CODE);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case CAMERA_CODE:
                    new CmaeraAsyncTask(this){
                        @Override
                        protected void onPostExecute(String s) {
                            if(TextUtils.isEmpty(s)){
                                Toast.makeText(MainActivity.this,
                                        "获取失败",
                                        Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(MainActivity.this,"图片地址"+s,Toast.LENGTH_LONG).show();
                                Bitmap bitmap = getBitmap(s);
                                if(bitmap!=null){
                                    mTestImage.setImageBitmap(bitmap);
                                }
                            }

                        }
                    }.execute(data);


                    break;

                case PHOTO_CODE:
                    new PhotoAsyncTask(this){
                        @Override
                        protected void onPostExecute(String s) {
                            if(TextUtils.isEmpty(s)){
                                Toast.makeText(MainActivity.this,
                                        "获取失败",
                                        Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(MainActivity.this,
                                        "图片地址"+s,
                                        Toast.LENGTH_LONG).show();
                                Bitmap bitmap = getBitmap(s);
                                if(bitmap!=null){
                                    mTestImage.setImageBitmap(bitmap);
                                }

                            }
                        }
                    }.execute(data);
                    break;
            }
        }
    }
    public Bitmap getBitmap(String url){
        FileInputStream fis = null;
        Bitmap bitmap=null;
        try {
            fis = new FileInputStream(url);
            bitmap= BitmapFactory.decodeStream(fis);

        } catch (FileNotFoundException e) {
           return null;
        }
        return bitmap;
    }

    public void getPermission(){
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {//检查权限

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
//这个API主要用于给用户一个申请权限的解释，该方法只有在用户在上一次已经拒绝过你的这个权限申请。也就是说，用户已经拒绝一次了，你又弹个授权框，你需要给用户一个解释，为什么要授权，则使用该方法。
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                //申请授权
            }
        } else {
            // TODO: 16/6/2  已经拥有权限
        }
    }
    //处理权限申请回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"获取到权限",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MainActivity.this,"没有权限",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
