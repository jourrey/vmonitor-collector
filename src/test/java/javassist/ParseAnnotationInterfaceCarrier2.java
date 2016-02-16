package javassist;

import com.baidu.vmonitor.annotation.DoLog;

public interface ParseAnnotationInterfaceCarrier2 {
    
    @DoLog(key={"123"})
    String getValue();
    
    void setValue(String key);

}
