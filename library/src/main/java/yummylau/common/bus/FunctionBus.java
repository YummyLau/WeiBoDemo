package yummylau.common.bus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by g8931 on 2017/11/17.
 */

public class FunctionBus {

    public static Map<Class, Object> sFunctionClassMap = new HashMap<>();


    public static void setFunction(Object o) {
        Class[] interfaces = o.getClass().getInterfaces();
        for (Class c : interfaces) {
            if (sFunctionClassMap.containsKey(c)) {
                throw new RuntimeException("duplicate set function: " + c.getName());
            }
            sFunctionClassMap.put(c, o);
        }
    }


    public static <T> T getFunction(Class<T> c) {
        T t = (T) sFunctionClassMap.get(c);
        if (t == null) {
            return null;
        }
        return t;
    }
}
