package com.qs.xutils3demo;

import android.app.Application;

import org.xutils.x;

/**
 * Created by xuyang on 16/6/2.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//初始化
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
