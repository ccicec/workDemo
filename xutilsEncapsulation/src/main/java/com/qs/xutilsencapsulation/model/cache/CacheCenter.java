package com.qs.xutilsencapsulation.model.cache;


import com.qs.xutilsencapsulation.model.cache.adress.Adress;

/**
 * Created by xuyang on 16/5/23.
 */
public class CacheCenter {

    private static Adress adress = null;

    public static Adress getAdress() {
        if (adress == null) {
            adress = new Adress();



        }
        return adress;
    }
}
