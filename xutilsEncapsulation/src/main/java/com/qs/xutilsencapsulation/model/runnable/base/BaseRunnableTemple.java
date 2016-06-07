package com.qs.xutilsencapsulation.model.runnable.base;

import android.content.Context;
import android.util.Log;

import com.qs.xutilsencapsulation.MyApplication;
import com.qs.xutilsencapsulation.model.enums.MOTHED;
import com.qs.xutilsencapsulation.view.ShowLinkErrorDialog;

import org.xutils.common.Callback;


/**
 * Created by xuyang on 16/3/8.
 */
public class BaseRunnableTemple<T>  {
    private Context con;
    private RunnableInteface<T> inteface=null;
    private String url = "";//地址
    boolean enable_LinkError = true;

    private RequestManagerCallBack<T> callBack;
    private MOTHED mothed;
    private Callback.Cancelable cancelable  = null;




    public BaseRunnableTemple( MOTHED mothed,final Context con, Class<T> entityClass, String url, final RunnableInteface<T> inteface){
        this.url = url;
        this.inteface = inteface;
        this.mothed = mothed;
        this.con = con;
        callBack=new RequestManagerCallBack<T>(con,entityClass){

            @Override
            public void onStarted() {
                super.onStarted();
                if (inteface != null) try {
                    inteface.onStart();
                } catch (Exception e) {
                }
            }

            @Override
            protected void onParseSuccess(T t) {
                super.onParseSuccess(t);
                if (inteface != null) try {
                    inteface.getData(t);
                } catch (Exception e) {
                }
                if (inteface != null) try {
                    inteface.end();
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                if (inteface != null) try {
                    inteface.error(ex,isOnCallback);
                } catch (Exception e2) {
                }
                if (inteface != null) try {
                    inteface.end();
                } catch (Exception e2) {
                }
                if (enable_LinkError) new ShowLinkErrorDialog(con);
            }


        };
    }

    public BaseRunnableTemple<T> setEnable_dialog(boolean enable_dialog) {
        this.callBack.setEnable_dialog(enable_dialog);
        return this;
    }
    public BaseRunnableTemple<T> setEnable_LinkError(boolean enable_LinkError) {
        this.enable_LinkError = enable_LinkError;
        return this;
    }


    public void execute() {
        try {
            switch (mothed) {
                case GET:
                   cancelable = MyApplication.getManager(con).startGet(url, callBack);
                    break;
                case POST:
                    cancelable =MyApplication.getManager(con).startPost(url,callBack);
                    break;
                default:
                    cancelable =MyApplication.getManager(con).startGet(url, callBack);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ds", e + "");
        }
    }


    public void undo() {

    }


    public void redo() {

    }
}
