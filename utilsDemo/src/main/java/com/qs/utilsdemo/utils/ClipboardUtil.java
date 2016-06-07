package com.qs.utilsdemo.utils;

import android.content.ClipboardManager;
import android.content.Context;

/* 剪切板工具 */
public class ClipboardUtil {
    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    public static void copy(String content, Context con) {
        // 得到剪贴板管理器
        ClipboardManager clip = (ClipboardManager) con.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(content.trim());
    }

    /**
     * 实现粘贴功能
     * add by wangqianzhou
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }
}
