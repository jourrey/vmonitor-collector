package com.baidu.vmonitor.log.javassist;

import javassist.CtMethod;

public interface ClassInjectProvider {

    /**
     * 对指定的方法注入代码
     * 
     * @param ctMethod
     *            CtMethod
     * @return
     */
    public String injectCode(final CtMethod ctMethod) throws Exception;

}
