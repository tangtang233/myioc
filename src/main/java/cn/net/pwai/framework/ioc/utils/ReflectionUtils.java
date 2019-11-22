package cn.net.pwai.framework.ioc.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static void injectField(Field field, Object obj, Object value) throws IllegalAccessException {
        if (null != field) {
            field.setAccessible(true);
            field.set(obj, value);
        }
    }
}
