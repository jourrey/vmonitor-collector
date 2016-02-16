package javassist;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.baidu.vmonitor.annotation.DoLog;
import com.baidu.vmonitor.common.ParseAnnotation;

public class ParseAnnotationInterfaceTest {

    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException,
                    InvocationTargetException, SecurityException, NoSuchMethodException, InstantiationException {
        Map<Method, DoLog> map = new HashMap<Method, DoLog>();
        ParseAnnotation.parseType(ParseAnnotationInterfaceImplements.class, DoLog.class, map);
        ParseAnnotation.parseMethod(ParseAnnotationInterfaceImplements.class, DoLog.class, map);
        for (Iterator<Entry<Method, DoLog>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
            Entry<Method, DoLog> type = iterator.next();
            System.out.println(type.getKey());
            System.out.println(type.getValue());
        }
    }

}
