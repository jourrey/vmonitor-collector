package com.baidu.vmonitor.log.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.Expr;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

public class Dissect
{
    public static void main(String[] args) {
        args = new String[]{"com.baidu.vmonitor.log.javassist.StringB"};
        if (args.length >= 1) {
            try {
                
                // set up class loader with translator
                Translator xlat = new DissectionTranslator();
                ClassPool pool = ClassPool.getDefault();
                Loader loader = new Loader(pool);
                loader.addTranslator(pool, xlat);
                    
                // invoke the "main" method of the application class
                String[] pargs = new String[args.length-1];
                System.arraycopy(args, 1, pargs, 0, pargs.length);
                loader.run(args[0], pargs);
                
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            
        } else {
            System.out.println
                ("Usage: Dissect main-class args...");
        }
    }
    
    public static class DissectionTranslator implements Translator
    {
        public void start(ClassPool pool) {}
        
        public void onLoad(ClassPool pool, String cname)
            throws NotFoundException, CannotCompileException {
            System.out.println("Dissecting class " + cname);
            CtClass clas = pool.get(cname);
            clas.instrument(new VerboseEditor());
        }
    }
    
    public static class VerboseEditor extends ExprEditor
    {
        private String from(Expr expr) {
            CtBehavior source = expr.where();
            return " in " + source.getName() + "(" + expr.getFileName() + ":" +
                expr.getLineNumber() + ")";
        }
        public void edit(FieldAccess arg) {
            String dir = arg.isReader() ? "read" : "write";
            System.out.println(" " + dir + " of " + arg.getClassName() +
                "." + arg.getFieldName() + from(arg));
        }
        public void edit(MethodCall arg) {
            System.out.println(" call to " + arg.getClassName() + "." +
                arg.getMethodName() + from(arg));
        }
        public void edit(NewExpr arg) {
            System.out.println(" new " + arg.getClassName() + from(arg));
        }
    }
}
