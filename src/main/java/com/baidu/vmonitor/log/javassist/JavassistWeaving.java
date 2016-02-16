package com.baidu.vmonitor.log.javassist;

import java.io.IOException;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class JavassistWeaving {
    
    public static void doWeaving(String classAllName, String methodName) {
        try {
            //  加载class文件
            CtClass clas = ClassPool.getDefault().get(classAllName);
            if (clas == null) {
                System.err.println("Class " + classAllName + " not found");
            } else {

                // 添加拦截方法
                addWeaving(clas, methodName);
                clas.writeFile();
                
                Class<?> clazz = clas.toClass();
                System.out.println(clazz.getDeclaredMethods()[0].getName()
                                + "==========" +
                                clazz.getDeclaredMethods()[1].getName());
                Class<?> demo=null;
                try{
                    demo=Class.forName("com.baidu.vmonitor.log.javassist.StringB");
                    System.out.println(demo.getDeclaredMethods().length);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                
                Method method = null;
                try {
                    method = demo.getDeclaredMethod("buildString$impl", Integer.class);
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                System.out.println("Added timing to method " + classAllName + "." + methodName);

            }

        } catch (CannotCompileException ex) {
            ex.printStackTrace();
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void addWeaving(CtClass clas, String mname) throws NotFoundException, CannotCompileException {

        // 获取加载类中的该方法
        CtMethod mold = clas.getDeclaredMethod(mname);

        mold.insertBefore("{System.out.println(\"在HelloTest.sayhello之前執行\");}");
        mold.insertAfter("{System.out.println(\"在HelloTest.sayhello之後執行\");}");

        // rename old method to synthetic name, then duplicate the
        // method with original name for use as interceptor
        String nname = mname + "$impl";
        mold.setName(nname);
        CtMethod mnew = CtNewMethod.copy(mold, mname, clas, null);

        // start the body text generation by saving the start time
        // to a local variable, then call the timed method; the
        // actual code generated needs to depend on whether the
        // timed method returns a value
        String type = mold.getReturnType().getName();
        StringBuffer body = new StringBuffer();
        body.append("{\nlong start = System.currentTimeMillis();\n");
        if (!"void".equals(type)) {
            body.append(type + " result = ");
        }
        body.append(nname + "($$);\n");

        // finish body text generation with call to print the timing
        // information, and return saved value (if not void)
        body.append("System.out.println(\"Call to method " + mname
                        + " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");
        if (!"void".equals(type)) {
            body.append("return result;\n");
        }
        body.append("}");

        // replace the body of the interceptor method with generated
        // code block and add it to class
        mnew.setBody(body.toString());
        clas.addMethod(mnew);

        // print the generated code block just to show what was done
        System.out.println("Interceptor method body:");
        System.out.println(body.toString());
    }

}
