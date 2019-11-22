package cn.net.pwai.framework.ioc.core;

import cn.net.pwai.framework.ioc.bean.BeanDefinition;
import cn.net.pwai.framework.ioc.bean.ConstructorArg;
import cn.net.pwai.framework.ioc.utils.BeanUtils;
import cn.net.pwai.framework.ioc.utils.ClassUtils;
import cn.net.pwai.framework.ioc.utils.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BeanFactoryImpl implements BeanFactory {
    private static final ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, BeanDefinition> beanDefineMap = new ConcurrentHashMap<>();
    private static final Set<String> beanNameSet = Collections.synchronizedSet(new HashSet<>());

    @Override
    public Object getBean(String name) throws Exception {
        //查找对象是否实例化
        Object bean = beanMap.get(name);
        if (null != bean) {
            return bean;
        }

        //如果对象没有实例化，就需要调用createBean来创建对象
        bean = createBean(beanDefineMap.get(name));

        if (null != bean) {
            //对象创建成功后，注入对象所需要的参数
            populateBean(bean);
            //再把对象存入Map中方便下次使用
            beanMap.put(name, bean);
        }

        //结束返回
        return bean;
    }

    protected void registerBean(String name, BeanDefinition beanDefinition) {
        beanDefineMap.put(name, beanDefinition);
        beanNameSet.add(name);
    }

    private void populateBean(Object bean) throws Exception {
        Field[] fields = bean.getClass().getSuperclass().getDeclaredFields();
        if (fields.length > 0) {
            for (Field field : fields) {
                String beanName = field.getName();
                beanName = StringUtils.uncapitalize(beanName);
                if (beanNameSet.contains(field.getName())) {
                    Object fieldBean = getBean(beanName);
                    if (null != fieldBean) {
                        ReflectionUtils.injectField(field, bean, fieldBean);
                    }
                }
            }
        }
    }

    private Object createBean(BeanDefinition beanDefinition) throws Exception {
        String beanName = beanDefinition.getClassName();
        Class clz = ClassUtils.loadClass(beanName);
        if (null == clz) {
            throw new Exception("Can not find bean by beanName");
        }
        List<ConstructorArg> constructorArgs = beanDefinition.getConstructorArgs();
        if (null != constructorArgs && !constructorArgs.isEmpty()) {
            List<Object> objects = new ArrayList<>();
            for (ConstructorArg constructorArg : constructorArgs) {
                if (null != constructorArg.getValue()) {
                    objects.add(constructorArg.getValue());
                } else {
                    objects.add(getBean(constructorArg.getRef()));
                }
            }
            Class[] constructorArgTypes = objects.stream().map(Object::getClass).collect(Collectors.toList()).toArray(new Class[]{});
            Constructor constructor = clz.getConstructor(constructorArgTypes);
            return BeanUtils.instanceByCglib(clz, constructor, objects.toArray());
        } else {
            return BeanUtils.instanceByCglib(clz, null, null);
        }
    }
}
