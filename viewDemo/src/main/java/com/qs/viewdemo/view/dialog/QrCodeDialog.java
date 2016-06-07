package com.qs.viewdemo.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.qs.viewdemo.QrcodeBuildUtil;
import com.qs.viewdemo.R;
import com.qs.viewdemo.view.CircleImageView;


public class QrCodeDialog extends Dialog {
    ImageView img;
    Context con;
    ImageButton close;
    CircleImageView imageView_head;

    Bitmap bitmap=null;

    public QrCodeDialog(Context con, String info, String number) {
        super(con, R.style.toumin);
        View base = LayoutInflater.from(con).inflate(R.layout.dialog_qrcode, null);
        setContentView(base);
        this.con = con;
        TextView num = (TextView) base.findViewById(R.id.number);
        img = (ImageView) base.findViewById(R.id.qrcode);
        close = (ImageButton) base.findViewById(R.id.btn_close);
        imageView_head=(CircleImageView)base.findViewById(R.id.imageView_head);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                QrCodeDialog.this.dismiss();
            }
        });
        num.setText(number);


//        try {
//            info = URLDecoder.decode(info, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        try {
            QrcodeBuildUtil create = new QrcodeBuildUtil();
            bitmap = create.CreateQrcode(con, info);
            img.setImageBitmap(bitmap);
        }catch (OutOfMemoryError error){
            Log.e("ds",error.toString());
        }
//        HeadImageUtil.setHeadImage(con,imageView_head);
//        con.SetImg_Url(imageView_head);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        try{
            img.setImageBitmap(null);
            bitmap.recycle();
            bitmap=null;
        }catch (Exception e){
        }
    }
}
