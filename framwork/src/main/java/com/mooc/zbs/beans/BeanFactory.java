package com.mooc.zbs.beans;

import com.mooc.zbs.web.server.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//bean工厂，初始化和保存bean
    public class BeanFactory {
        //用线程安全的ConcurrentHashMap设置bean容器
        private static Map<Class<?>,Object> classToBean = new ConcurrentHashMap<>();

        //根据class对象回去bean
        public static Object getBean(Class<?> cls){
            return classToBean.get(cls);
        }

        //初始化bean容器
        public static void intiBean(List<Class<?>> classList) throws Exception {
            //定义一个容器
            List<Class<?>> toCreate = new ArrayList<>(classList);
            //当容器内，还有类定义时遍历容器
            while (toCreate.size()!=0){
                //记录一下传进来类的数量
                int remainSize = toCreate.size();
                //遍历容器，这里的i<toCreate.size应该是有问题的，后期改
                for(int i=0;i<toCreate.size();i++){
                    //如果这个类已经创建成功，就从creat容器中删除类对象
                    if (finishCreate(toCreate.get(i))){
                        toCreate.remove(i);
                    }
                }
                //遍历完了，再判断create容器中的类对象是否减少了，没减少的话就是陷入了死循环中
                if (toCreate.size()==remainSize){
                    throw new Exception("cycle dependency!");
                }
            }
        }

        public static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
            //如果这个类对象不是bean或controller注解，就不用创建了，直接返回true
            //controller本身是一个特殊的bean
            if (!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)){
                return true;
            }
            //创建当前类对象的bean
            Object bean = cls.newInstance();
            //对象创建好了，但是里面的依赖还没有解决，底下的for循环都是解决这个bean中依赖的bean问题
            for (Field filed:cls.getDeclaredFields()){
                //如果属性中有Autowired注解，就需要用依赖注入来解决这个依赖
                if (filed.isAnnotationPresent(AutoWired.class)){
                    //获取当前属性的类型
                    Class<?> fieldType = filed.getType();
                    //根据类型从工厂中获取bean
                    Object reliantBean = BeanFactory.getBean(fieldType);
                    //如果这个被依赖bean不存在，这个创建bean肯定会创建失败，直接返回false，结束。
                    if (reliantBean==null){
                        return false;
                    }
                    //如果这个被依赖的bean存在，就把这个被依赖bean注入到创建的bean中
                    //修改这个字段的可计数性
                    filed.setAccessible(true);
                    //设置值，注入被依赖的bean
                    filed.set(bean,reliantBean);
                }
            }
            //把创建的bean加载到BeanFactory里面去，返回成功
            classToBean.put(cls,bean);
            return true;
        }
}
