package com.qs.xutilsencapsulation.model.runnable.base;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;

/**
 * Created by xuyang on 16/5/23.
 */
public  class JSONRequestCallBack<T> implements Callback.ProgressCallback<String> {

    private Class<T> entityClass;

    public JSONRequestCallBack(Class<T> entityClass) {
        this.entityClass = entityClass;

    }
    protected void onParseSuccess(T t) {

    }
    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }

    @Override
    public void onSuccess(String result) {
        try {
            Log.e("tag",result+"");
            onParseSuccess(JSON.parseObject(result, entityClass));
        }catch (Exception e){
            onError(new Throwable(),false);
            return;
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
