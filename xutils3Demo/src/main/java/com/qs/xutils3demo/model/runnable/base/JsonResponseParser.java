package com.qs.xutils3demo.model.runnable.base;

import com.alibaba.fastjson.JSON;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * Created by xuyang on 16/5/20.
 *
 */
public class JsonResponseParser implements ResponseParser {
    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        // TODO: 16/5/23  在此可做其他操作
        //        if(resultClass==xxx.class){
        //            return result;
        //        }

        return JSON.parseObject(result,resultClass);
    }
}
