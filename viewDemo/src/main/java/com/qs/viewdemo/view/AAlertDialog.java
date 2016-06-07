package com.qs.viewdemo.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qs.viewdemo.R;


/**
 * Created by xuyang on 16/3/9.
 */
public class AAlertDialog {
    LinearLayout window;//背景
    Dialog dialog;
    View buttom, tow;//

    Button top, left, right;
    TextView textv;
    TextView title;
    ImageView img;
    View view;
    private boolean cancelable = true;




    public AAlertDialog(Context con) {
        Create(con, R.layout.dialog_alertdialog);
    }
    public AAlertDialog(Context con, int layout){
        Create(con,layout);
    }

    private void Create(Context con, int layout){

        try {
            dialog.dismiss();
        } catch (Exception e) {
        }

        dialog = new Dialog(con, R.style.toumin);
        //dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 0xffffffff);
        LayoutInflater Inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = Inflater.inflate(layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (cancelable == true) dialog.dismiss();
            }
        });


        window = (LinearLayout) view.findViewById(R.id.window);

        buttom = (FrameLayout) view.findViewById(R.id.buttom);
        tow = (LinearLayout) buttom.findViewById(R.id.view_two);
        top = (Button) buttom.findViewById(R.id.button_one);
        left = (Button) buttom.findViewById(R.id.button_left);
        right = (Button) buttom.findViewById(R.id.button_right);
        img = (ImageView) view.findViewById(R.id.img);
        img.setVisibility(View.GONE);
        buttom.setVisibility(View.GONE);
        top.setVisibility(View.GONE);

        textv = (TextView) view.findViewById(R.id.text);
        title = (TextView) view.findViewById(R.id.title);
        textv.setText("");
        title.setText("");
        title.setVisibility(View.GONE);
        textv.setVisibility(View.GONE);
        dialog.setContentView(view);
    }

    public AAlertDialog setLeftButton(String str, OnClickListener cli) {
        buttom.setVisibility(View.VISIBLE);
        top.setVisibility(View.GONE);
        initButton(left, str, cli);

        return this;
    }

    public AAlertDialog setRightButton(String str, OnClickListener cli) {
        buttom.setVisibility(View.VISIBLE);
        top.setVisibility(View.GONE);

        initButton(right, str, cli);
        return this;
    }

    public AAlertDialog setButton(String str, OnClickListener cli) {
        buttom.setVisibility(View.VISIBLE);
        top.setVisibility(View.VISIBLE);
        tow.setVisibility(View.GONE);

        initButton(top, str, cli);
        return this;
    }

    private void initButton(Button btn, String str, OnClickListener cli) {
        btn.setText(str);
        if (cli == null) {
            cli = new OnClickListener() {

                @Override
                public void Click(View v, Dialog dia) {
                    // TODO Auto-generated method stub
                    dismiss();
                }
            };
        }
        final OnClickListener c = cli;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                c.Click(v, dialog);
            }
        });

    }

    public AAlertDialog setTitle(String s) {
        title.setVisibility(View.VISIBLE);
        title.setText(s);
        return this;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public TextView getTextView() {
        return textv;
    }

    public TextView getTitleTextView() {
        return title;
    }

    public AAlertDialog setMessage(String s) {
        textv.setVisibility(View.VISIBLE);
        textv.setText(s);
        return this;
    }

    public AAlertDialog setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        dialog.setCancelable(cancelable);
        return this;
    }

    public AAlertDialog setImage(int img) {
        this.img.setVisibility(View.VISIBLE);
        this.img.setImageResource(img);
        return this;
    }

    public ImageView getImg() {
        return img;
    }

    public AAlertDialog show() {
        try {
            dialog.show();
        }catch (Exception e){}
        return this;
    }

    public void dismiss() {

        try {
            dialog.dismiss();
            dialog = null;
        } catch (Exception e) {
        }


    }

    public static interface OnClickListener {
        public void Click(View v, Dialog dialog);
    }
}
