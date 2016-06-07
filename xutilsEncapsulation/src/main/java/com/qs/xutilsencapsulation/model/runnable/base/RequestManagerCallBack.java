package com.qs.xutilsencapsulation.model.runnable.base;

import android.content.Context;

import com.qs.xutilsencapsulation.view.Dialog_load;

/**
 * Created by xuyang on 16/3/8.
 */
public class RequestManagerCallBack<T> extends JSONRequestCallBack<T> {
    private Dialog_load pdialog = null;
    private boolean enable_dialog =true;
    private Context context=null;
    public RequestManagerCallBack(Context context, Class<T> entityClass) {
        super(entityClass);
        this.context=context;
    }

    @Override
    public void onStarted() {
        if(enable_dialog){showPdialog(context);}
        super.onStarted();
    }


    @Override
    protected void onParseSuccess(T t) {
        if(enable_dialog){hidePdialog();}

        super.onParseSuccess(t);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        if(enable_dialog){hidePdialog();}
        super.onError(ex, isOnCallback);
    }

    public void showPdialog(Context con) {
        if(pdialog==null){
            pdialog = new Dialog_load(con);
        }
        pdialog.show();
        enable_dialog =true;
    }
    public void hidePdialog() {
        try {
            pdialog.dismiss();
        } catch (Exception e) {
        }
    }
    public void setEnable_dialog(boolean enable_dialog) {
        this.enable_dialog = enable_dialog;
    }

    public boolean isEnable_dialog() {
        return enable_dialog;
    }
}
