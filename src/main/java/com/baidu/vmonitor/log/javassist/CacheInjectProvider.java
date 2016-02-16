package com.baidu.vmonitor.log.javassist;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import javassist.CtClass;
import javassist.CtMethod;

public class CacheInjectProvider implements ClassInjectProvider {

    private static final Logger LOG = LoggerFactory.getLogger(CacheInjectProvider.class);

    public CacheInjectProvider() {

    }

    /**
     * 注入缓存，缓存键值以类名+#+方法名(参数值1...参数值n)为key
     * 
     * @param method
     *            CtMethod
     */
    public String injectCode(final CtMethod method) throws Exception {

        StringBuffer cacheCode = new StringBuffer();

        try {
            if (method.getReturnType() == CtClass.voidType) {
                cacheCode.append(method.getName() + "$enhanced($$); ");
            } else {
                cacheCode.append("StringBuilder cacheKeyBuilder=new StringBuilder(); ");
                cacheCode.append("com.baidu.vmonitor.log.javassist.MethodCacheKey cacheKey=new com.baidu.vmonitor.log.javassist.MethodCacheKey(); ");
                cacheCode.append("cacheKey.setClassName(\"\").append($1.getDeclaringClass().getName()).append(\"\"); ");
                cacheCode.append("cacheKey.setMethodName(\"\").append($1.getName()).append(\"\"); ");

                CtClass[] ctClass = method.getParameterTypes();
                cacheCode.append("Object[] parameters=new Object[").append(ctClass.length).append("]; ");
                for (int i = 0; i < ctClass.length; i++) {
                    cacheCode.append("parameters[" + i + "]=").append("($w)$" + (i + 1)).append("; ");
                }
                cacheCode.append("cacheKey.setParameters(parameters); ");

                cacheCode.append("Cache cache=CacheFactory.getCache();").append(" ");
                cacheCode.append("if(cache.get(cacheKey)==null)").append(" ");
                cacheCode.append("{").append(" ");
                if (method.getReturnType().isPrimitive()) {
                    cacheCode.append("Object result=($w)").append(method.getName() + "$enhanced($$);").append(" ");
                } else {
                    cacheCode.append("Object result=").append(method.getName() + "$enhanced($$);").append(" ");
                }
                cacheCode.append("cache.put(cacheKey,result);").append(" ");
                cacheCode.append("}").append(" ");
                cacheCode.append("return ").append("($r)cache.get(cacheKey);");
            }

        } catch (Exception e) {
        }
        LOG.info("inject sourcecode>>>: {}", cacheCode.toString());
        return cacheCode.toString();
    }

}
