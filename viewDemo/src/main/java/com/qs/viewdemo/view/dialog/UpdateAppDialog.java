package com.qs.viewdemo.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qs.viewdemo.R;
import com.qs.viewdemo.view.AnimationProgressBar;


/**
 * Created by xuyang on 16/5/4.
 */
public class UpdateAppDialog {

    Dialog mDialog;
    Context mContext;
    AnimationProgressBar mAnimationProgressBar;
    LinearLayout progress_layout,text_content;
    TextView text_Progress;
    Button button_cancel,button_determine;
    public UpdateAppDialog(Context context) {
        mContext = context;
        mDialog=new Dialog(context, R.style.toumin);
        mDialog.setCancelable(false);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_updateapp, null);
        mAnimationProgressBar= (AnimationProgressBar) inflate.findViewById(R.id.progressbar);
        progress_layout= (LinearLayout) inflate.findViewById(R.id.progress_layout);
        text_content= (LinearLayout) inflate.findViewById(R.id.text_content);
        button_cancel= (Button) inflate.findViewById(R.id.button_cancel);
        button_determine= (Button) inflate.findViewById(R.id.button_determine);
        text_Progress= (TextView) inflate.findViewById(R.id.text_Progress);
        mDialog.setContentView(inflate);
    }


    public AnimationProgressBar getProgeressBar(){
        if(mAnimationProgressBar!=null){
            return mAnimationProgressBar;
        }

        return null;
    }

    public Button getButton_cancel(){
        if(button_cancel!=null){
            return button_cancel;
        }

        return null;
    }

    public Button getButton_determine(){
        if(button_determine!=null)
        {
            return button_determine;
        }
        return null;
    }

    public LinearLayout getProgress_layout(){
        if(progress_layout!=null){
            return progress_layout;
        }
        return null;
    }

    public LinearLayout getText_content(){
        if(text_content!=null){
            return text_content;
        }
        return null;
    }

    public TextView getText_Progress(){
        if(text_Progress!=null){
            return text_Progress;
        }

        return null;
    }
    public void show(){
        if(mDialog!=null){
            mDialog.show();
        }
    }
    public void dismiss(){
        if(mDialog!=null){
            mDialog.dismiss();
            mDialog=null;
        }
    }
}
