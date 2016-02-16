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

public class ParseAnnotationTest {

    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException,
                    InvocationTargetException, SecurityException, NoSuchMethodException, InstantiationException {
        Map<Method, DoLog> map = new HashMap<Method, DoLog>();
        ParseAnnotation.parseType(ParseAnnotationExtends.class, DoLog.class, map);
        ParseAnnotation.parseMethod(ParseAnnotationExtends.class, DoLog.class, map);
        for (Iterator<Entry<Method, DoLog>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
            Entry<Method, DoLog> type = iterator.next();
            System.out.println(type.getKey());
            System.out.println(type.getValue());
        }
        System.out.println("==============================================");
        System.out.println(ParseAnnotation.parseMethod(ParseAnnotationInterfaceCarrier.class.getMethod("getValue"), DoLog.class, false));
        System.out.println(ParseAnnotation.parseMethod(ParseAnnotationInterfaceCarrier.class.getMethod("getValue"), DoLog.class, true));
        System.out.println(ParseAnnotation.parseMethod(ParseAnnotationInterfaceCarrier.class.getMethod("setValue", String.class), DoLog.class, false));
        System.out.println(ParseAnnotation.parseMethod(ParseAnnotationInterfaceCarrier.class.getMethod("setValue", String.class), DoLog.class, true));
    }

}
