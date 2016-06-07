package com.qs.xutilsencapsulation.model.enums;

/**
 * Created by xuyang on 16/5/23.
 */
public enum MOTHED {
    GET(0),POST(1);
    private int code;
    MOTHED(int code) {
        this.code=code;
    }

    @Override
    public String toString() {
       return String.valueOf(this.code);
    }
}
