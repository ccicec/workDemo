package com.qs.xutilsencapsulation.model.runnable.templete;

import android.content.Context;

import com.qs.xutilsencapsulation.model.cache.CacheCenter;
import com.qs.xutilsencapsulation.model.entity.Qq;
import com.qs.xutilsencapsulation.model.enums.MOTHED;
import com.qs.xutilsencapsulation.model.runnable.base.BaseRunnableTemple;
import com.qs.xutilsencapsulation.model.runnable.base.RunnableInteface;

/**
 * Created by xuyang on 16/5/23.
 *
 */
public class GetIp {

    public BaseRunnableTemple<Qq> getTemplete(String ip, Context context, RunnableInteface<Qq> runnableInteface) {
        String url= CacheCenter.getAdress().getIp(ip);
        BaseRunnableTemple<Qq> baseRunnableTemple= new BaseRunnableTemple<>(MOTHED.GET,context,Qq.class , url, runnableInteface);

            return baseRunnableTemple;
        }
}
