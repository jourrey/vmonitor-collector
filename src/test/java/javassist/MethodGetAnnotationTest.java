package javassist;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.baidu.vmonitor.annotation.DoLog;

public class MethodGetAnnotationTest {

    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException,
                    InvocationTargetException, SecurityException, NoSuchMethodException, InstantiationException {
        Method method = ParseAnnotationExtends.class.getMethods()[0];
        System.out.println(method.getName());
        for (Class<?> string : method.getParameterTypes()) {
            System.out.println(string.getName());
        }
        System.out.println(method.getAnnotation(DoLog.class));
        for (Annotation string : method.getAnnotations()) {
            System.out.println(string.toString());
        }
        System.out.println("======================================================");
        method = ParseAnnotationExtends.class.getMethods()[1];
        System.out.println(method.getName());
        for (Class<?> string : method.getParameterTypes()) {
            System.out.println(string.getName());
        }
        System.out.println(method.getAnnotation(DoLog.class));
        for (Annotation string : method.getAnnotations()) {
            System.out.println(string.toString());
        }
        System.out.println("======================================================");
        method = ParseAnnotationExtends.class.getMethods()[2];
        System.out.println(method.getName());
        for (Class<?> string : method.getParameterTypes()) {
            System.out.println(string.getName());
        }
        System.out.println(method.getAnnotation(DoLog.class));
        for (Annotation string : method.getAnnotations()) {
            System.out.println(string.toString());
        }
    }

}
