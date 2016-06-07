package com.qs.viewdemo.view.dialog;

import android.content.Context;

import com.qs.viewdemo.R;
import com.qs.viewdemo.view.AAlertDialog;


/**
 * 网络错误弹出框
 */
public class ShowLinkErrorDialog {
    AAlertDialog dialog = null;
    public ShowLinkErrorDialog(final Context con) {
        ShowLinkError(con, con.getResources().getString(R.string.link_error),null);
    }
    private void ShowLinkError(final Context con, String info, final AAlertDialog.OnClickListener clickListener){
        try {dialog.dismiss();} catch (Exception e) {}
        show(con, info, clickListener);
    }
    private void show(Context con, String info, final AAlertDialog.OnClickListener clickListener) {
        try {
            if(info==null){info=con.getResources().getString(R.string.link_error);}
            dialog = new AAlertDialog(con).setTitle("提示").setMessage(info).setButton("确定", clickListener);
            if(clickListener!=null)dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
        }
    }
}
