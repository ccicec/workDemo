package com.qs.retrofit2demo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by xuyang on 16/6/1.
 */
public interface ServerApi {
    @GET("qqluck/query")
    Call<Qq> getQq1(@Query("appkey")String appkey, @Query("qq") String qq);//第一种查询方法


    @GET("qqluck/query")
    Call<Qq> getQq2(@QueryMap Map<String,String> map);//第二种查询方法
    @GET("qqluck/{q}")
    Call<Qq> getQq3(@Path("q") String q,@QueryMap Map<String,String> map);
}
