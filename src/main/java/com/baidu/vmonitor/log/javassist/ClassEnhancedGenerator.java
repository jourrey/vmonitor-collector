package com.baidu.vmonitor.log.javassist;

import java.net.URI;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class ClassEnhancedGenerator {
    
    private ClassEnhancedGenerator() {

    }

    /**
     * 类方法增强<BR>
     * 
     * 对指定类的方法进行代码增强(将指定的原方法名改为$enhanced,同时复制原方法名进行代码注入)
     * 
     * @param className
     *            待增强的类名
     * @param methodName
     *            待增强的方法名
     * @param injectType
     *            {@link InjectType}注入类型
     * @param provider
     *            {@link ClassInjectProvider}实现类
     * @throws Exception
     */
    public static void enhancedMethod(String className, String[] methods, InjectType injectType,
                    ClassInjectProvider provider) throws Exception {

        CtClass ctClass = ClassPool.getDefault().get(className);

        for (int i = 0; i < methods.length; i++) {
            injectCodeForMethod(ctClass, methods[i], injectType, provider);
        }

        String resource = className.replace(".", "/") + ".class";
        URI uri = ClassLoader.getSystemClassLoader().getResource(resource).toURI();
        String classFilePath = uri.getRawPath().substring(0, uri.getRawPath().length() - resource.length());
        ctClass.writeFile(classFilePath);
    }

    private static void injectCodeForMethod(CtClass ctClass, String methodName, InjectType injectType,
                    ClassInjectProvider provider) throws Exception {
        CtMethod oldMethod = ctClass.getDeclaredMethod(methodName);

        // 修改原有的方法名称为"方法名$enhanced"，如果已存在该方法则返回
        String originalMethod = methodName + "$enhanced";
        CtMethod[] methods = ctClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            CtMethod method = methods[i];
            if (method.getName().equals(originalMethod)) {
                return;
            }
        }
        oldMethod.setName(originalMethod);

        // 增加代码,复制原来的方法名作为增强的新方法,同时调用原有方法即"方法名$enhanced"
        CtMethod enhancedMethod = CtNewMethod.copy(oldMethod, methodName, ctClass, null);
        // 对复制的方法注入代码
        StringBuffer methodBody = new StringBuffer();
        methodBody.append("{");
        switch (injectType) {
        case AFTER:
            methodBody.append(provider.injectCode(enhancedMethod));
            methodBody.append(originalMethod + "($$); ");
            break;
        case BEFORE:
            methodBody.append(originalMethod + "($$); ");
            methodBody.append(provider.injectCode(enhancedMethod));
            break;
        default:
            String injectCode = provider.injectCode(enhancedMethod);
            methodBody.append(injectCode);

        }
        methodBody.append("}");
        enhancedMethod.setBody(methodBody.toString());

        ctClass.addMethod(enhancedMethod);
    }
    
    public enum InjectType {
        AFTER,BEFORE,NONE
    }

}
