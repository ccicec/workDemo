package com.qs.xutilsencapsulation;

import android.app.Application;
import android.content.Context;

import com.qs.xutilsencapsulation.model.runnable.base.RequestManager;

import org.xutils.x;

/**
 * Created by xuyang on 16/6/2.
 */
public class MyApplication extends Application {
    public static RequestManager manager = null;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false); // 开启debug会影响性能
    }

    public static synchronized RequestManager getManager(Context con) {
        if(manager==null){
            manager=new RequestManager(con);
        }
        return manager;
    }
}
