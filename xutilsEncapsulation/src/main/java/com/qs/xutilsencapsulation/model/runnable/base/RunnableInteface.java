package com.qs.xutilsencapsulation.model.runnable.base;

/**
 * Created by xuyang on 16/3/8.
 */
public abstract class RunnableInteface<T> {
    public void onStart(){};/*开始*/

    public abstract void getData(T t);/*解析得到数据*/

    public abstract  void error(Throwable ex, boolean isOnCallback);/*连接失败时*/

    protected   void end(){};/*结束*/
}
