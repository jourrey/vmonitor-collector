package javassist;

import com.baidu.vmonitor.annotation.DoLog;

@DoLog(collectorClass=TotemDoLogCollector.class)
public interface ParseAnnotationInterfaceCarrier {
    
    @DoLog(false)
    String getValue();
    
    void setValue(String key);

}
