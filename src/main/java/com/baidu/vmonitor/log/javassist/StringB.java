package com.baidu.vmonitor.log.javassist;

import com.baidu.vmonitor.annotation.DoLog;

@DoLog
public class StringB {
    
    public String buildString(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (char)(i%26 + 'a');
        }
        return result;
    }
    
    public String buildString(int length, String s) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (char)(i%26 + 'a');
        }
        return result;
    }

}
