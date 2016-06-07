package com.qs.xutilsencapsulation.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.qs.xutilsencapsulation.R;


/**
 * Created by xuyang on 16/5/20.
 */
public class Dialog_load {
   private Dialog mDialog;
    private Context mContext;
    public Dialog_load(Context context) {
       this.mContext=context;
        init();
    }

    private void init(){
        if(mDialog==null){
            mDialog=new Dialog(mContext, R.style.customDialog);
        }

        mDialog.setCancelable(true);//再点击ProgressDialog以外的区域就不会dismiss

        View inflate = LinearLayout.inflate(mContext, R.layout.dialog_load, null);
        mDialog.setContentView(inflate);
    }

    public void show(){
        if(mDialog!=null){
            mDialog.show();
        }
    }

    public void dismiss(){
        if(mDialog!=null){
            mDialog.dismiss();
        }

    }
}
