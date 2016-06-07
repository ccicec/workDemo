package com.qs.retrofit2demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * 只是网络请求   下载和上传还需查阅资料
 * 导入两个jar包
 * compile 'org.ligboy.retrofit2:converter-fastjson-android:2.0.2'    进行json自动解析
 * compile 'com.squareup.retrofit2:retrofit:2.0.2'
 * 添加网络权限
 */

public class MainActivity extends AppCompatActivity {
    String TAG="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//;;
        getData();
    }

    public void getData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.jisuapi.com/")//注意 baseurl必须以/结尾
        .addConverterFactory(FastJsonConverterFactory.create()).build();//导入相应jar

        ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<Qq> qq1 = serverApi.getQq1("34865d1e2ff7170f", "1053302578");
        Log.e(TAG,qq1.request().url()+"");
        qq1.enqueue(new Callback<Qq>() {

            @Override
            public void onResponse(Call<Qq> call, Response<Qq> response) {
                Log.e(TAG, JSON.toJSONString(response.body())+"");
            }

            @Override
            public void onFailure(Call<Qq> call, Throwable t) {
                Log.e(TAG,t.getMessage()+"");
            }
        });

    }

}
