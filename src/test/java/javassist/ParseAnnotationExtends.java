package javassist;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.baidu.vmonitor.annotation.DoLog;

public class ParseAnnotationExtends extends ParseAnnotationCarrier {
    
    public static <V, T extends Annotation> Map<String, T> parseMethod(Class<V> clazz, T annotation)
                    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
                    SecurityException, NoSuchMethodException, InstantiationException {
                        return null;
    }
    
    @DoLog
    public static <V, T extends Annotation> void parseMethod1(Class<V> clazz, T annotation)
                    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
                    SecurityException, NoSuchMethodException, InstantiationException {
    }

    public static <V, T extends Annotation> void parseMethod2(Class<V> clazz, T annotation)
                    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
                    SecurityException, NoSuchMethodException, InstantiationException {
    }

}
