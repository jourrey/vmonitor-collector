package javassist;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.baidu.vmonitor.annotation.DoLog;

@DoLog
public class ParseAnnotationCarrier {

    @DoLog(collectorClass = TotemDoLogCollector.class, key = { "PARAMETERS", "RESULT" })
    public static <V, T extends Annotation> Map<String, T> parseMethod(Class<V> clazz, T annotation)
                    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
                    SecurityException, NoSuchMethodException, InstantiationException {
        Map<String, T> map = new HashMap<String, T>();
        for (Method method : clazz.getDeclaredMethods()) {
            Annotation anno = method.getAnnotation(annotation.getClass());
            if (anno != null) {
                map.put(method.getName(), annotation);
            }
        }
        return map;
    }

    @DoLog(key = { "PARAMETERS", "EXCEPTION" })
    public static <V, T extends Annotation> void parseMethod1(Class<V> clazz, T annotation)
                    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
                    SecurityException, NoSuchMethodException, InstantiationException {
    }

}
