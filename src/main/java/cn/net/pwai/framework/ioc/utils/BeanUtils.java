package cn.net.pwai.framework.ioc.utils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

public class BeanUtils {
    public static <T> T instanceByCglib(Class<T> clz, Constructor ctr, Object[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clz);
        enhancer.setCallback(NoOp.INSTANCE);
        if (null == ctr) {
            return (T) enhancer.create();
        } else {
            return (T) enhancer.create(ctr.getParameterTypes(), args);
        }
    }
}
