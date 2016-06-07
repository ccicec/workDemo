package com.qs.utilsdemo.utils;

import android.content.Context;
import android.graphics.Typeface;

/*字体，单例读取*/
public class Font {
    static Typeface fontFace = null;
    public static Typeface getFont(Context con) {
        if (fontFace == null) {
            fontFace = Typeface.createFromAsset(con.getAssets(), "zt.otf");
        }
        return fontFace;
    }
}
