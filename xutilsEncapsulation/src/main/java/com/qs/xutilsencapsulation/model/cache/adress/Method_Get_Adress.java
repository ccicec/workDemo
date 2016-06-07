package com.qs.xutilsencapsulation.model.cache.adress;

/**
 * Created by xuyang on 16/5/23.
 */
public class Method_Get_Adress extends BaseAdress {

    public String getIp(String qq){
     return    getHost()+"query?appkey="+getKey()+"&qq="+qq;
    }
}
