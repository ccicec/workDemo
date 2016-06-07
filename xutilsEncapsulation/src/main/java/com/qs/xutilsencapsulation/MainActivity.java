package com.qs.xutilsencapsulation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.qs.xutilsencapsulation.model.entity.Qq;
import com.qs.xutilsencapsulation.model.runnable.base.RunnableInteface;
import com.qs.xutilsencapsulation.model.runnable.templete.GetIp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetIp().getTemplete("1053302578", this, new RunnableInteface<Qq>() {
            @Override
            public void getData(Qq qq) {
                Log.e("TAG", JSON.toJSONString(qq));
                Toast.makeText(MainActivity.this,JSON.toJSONString(qq),Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MainActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();

            }
        }).execute();

    }


}
