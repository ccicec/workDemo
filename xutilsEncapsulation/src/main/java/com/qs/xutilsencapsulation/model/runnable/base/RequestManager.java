package com.qs.xutilsencapsulation.model.runnable.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by xuyang on 16/5/23.
 */
public class RequestManager {
    private Context mContext;

    public RequestManager(Context context) {
        mContext = context;
    }

    public Callback.Cancelable startGet(String url, Callback.CommonCallback commonCallback){
        //在此封装url
        RequestParams requestParams=new RequestParams(url);
        Log.e("tag",url);
        return x.http().get(requestParams,commonCallback);
    }

    public Callback.Cancelable startPost(String url, Callback.CommonCallback commonCallback){
        //在此封装url
        int index=url.indexOf("?");
        String urlLeft=url.substring(0, index + 1);
        String urlRight = url.substring(index+1);
        RequestParams requestParams=new RequestParams(urlLeft);
        if(!TextUtils.isEmpty(urlRight)) {
            String[] params = urlRight.split("&");
            for (int i = 0; i < params.length; i++) {
                String[] p = params[i].split("=");
                if (p.length == 2) {
                    requestParams.addBodyParameter(p[0], p[1]);
                }
            }
        }


        return x.http().post(requestParams,commonCallback);
    }
}
