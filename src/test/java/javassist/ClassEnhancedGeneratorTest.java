package javassist;

import com.baidu.vmonitor.log.javassist.CacheInjectProvider;
import com.baidu.vmonitor.log.javassist.ClassEnhancedGenerator;
import com.baidu.vmonitor.log.javassist.ClassEnhancedGenerator.InjectType;
import com.baidu.vmonitor.log.javassist.StringB;

public class ClassEnhancedGeneratorTest {
    
    public static void main(String[] args) throws Exception {
        ClassEnhancedGenerator.enhancedMethod(StringB.class.getName(), new String[]{"buildString"}, InjectType.NONE, new CacheInjectProvider());
        StringB sb = new StringB();
        sb.buildString(8);
    }

}
