package javassist;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class StackTraceTest {
    
    private void show(String hello) throws SecurityException, NoSuchMethodException{
        System.out.println(hello);
    }
    
    private void traceInfo() throws SecurityException, NoSuchMethodException{
        info();
    }
    
    private void info() throws SecurityException, NoSuchMethodException{
        StackTraceElement[] eles = Thread.currentThread().getStackTrace();
        System.out.println(Arrays.toString(eles));
        System.out.println(StackTraceTest.class.getMethods()[0]);
//        Map<Thread, StackTraceElement[]> map = Thread.currentThread().getAllStackTraces();
//        for (Iterator<Entry<Thread, StackTraceElement[]>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
//            Entry<Thread, StackTraceElement[]> type = iterator.next();
//            System.out.println(type.getKey());
//            System.out.println(Arrays.toString(type.getValue()));
//        }
    }
    
    public static void main(String[] args) throws SecurityException, NoSuchMethodException {
        StackTraceTest stackTraceTest = new StackTraceTest();
        stackTraceTest.traceInfo();
    }

}
