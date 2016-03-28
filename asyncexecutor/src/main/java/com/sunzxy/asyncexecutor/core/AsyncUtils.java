package com.sunzxy.asyncexecutor.core;

import android.text.TextUtils;

import java.lang.reflect.Field;

/**
 * Created by zhengxiaoyong on 16/3/27.
 */
public class AsyncUtils {
    public static boolean isDigit(CharSequence str) {
        if (str == null)
            return false;
        int length = str.length();
        return length > 0 && TextUtils.isDigitsOnly(str);
    }

    public static boolean hasInnerRef(String fieldName) {
        return fieldName != null && fieldName.startsWith("this$");
    }

    public static void cleanInnerRef(Object obj) {
        if (obj == null)
            return;
        Field[] fields = obj.getClass().getDeclaredFields();
        int length = fields.length;
        for (int i = 0; i < length; i++) {
            fields[i].setAccessible(true);
            String fieldName = fields[i].getName();
            String resultStr = fieldName.substring(5);
            if (AsyncUtils.hasInnerRef(fieldName) && AsyncUtils.isDigit(resultStr)) {
                try {
                    fields[i].set(obj, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> void paramCheck(T t) {
        if (t == null) {
            throw new NullPointerException("param is null");
        }
    }
}
