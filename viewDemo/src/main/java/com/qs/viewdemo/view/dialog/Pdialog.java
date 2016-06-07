package com.qs.viewdemo.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qs.viewdemo.R;


public class Pdialog {
    Dialog dialog;
    TextView textv;
    ProgressBar progress;
    View view;
    Context mContext;
    public Pdialog(Context context) {
        this.mContext=context;
        dialog=new Dialog(context, R.style.toumin){
            @Override
            public void dismiss() {
                super.dismiss();
            }
        };
        dialog.setCancelable(false);//再点击ProgressDialog以外的区域就不会dismiss
        LayoutInflater Inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = Inflater.inflate(R.layout.dialog_progress, null);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        textv = (TextView) view.findViewById(R.id.dialog_text);
        dialog.setContentView(view);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {//对话框退出回调
                // TODO Auto-generated method stub
                OnDismiss(dialog);
            }
        });


    }
    /**
     * @paramdismissed
     */
    protected void OnDismiss(DialogInterface dialog2) {
        // TODO Auto-generated method stub

    }
    public Dialog getDialog() {
        return dialog;
    }

    public Pdialog setMessage(String s) {
        if (textv != null) {
            textv.setText(s);
        }
        return this;
    }
    public Pdialog show() {
        try {
            if (dialog != null) {
                dialog.show();
//                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", -50, 0);
//                objectAnimator.setDuration(500).setInterpolator(new EaseOutQuartInterpolator());
//                objectAnimator.start();
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        return this;
    }
    public void dismiss() {
        if (dialog == null || view == null) {
            return;
        }
        if (dialog.isShowing() == false) {
            return;
        }
        try {
            dialog.dismiss();
        } catch (Exception e) {
        }


    }


}
