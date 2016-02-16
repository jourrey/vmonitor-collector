package com.baidu.vmonitor.utils;

import com.google.gson.Gson;

public class GsonUtils {
    
    private static Gson instance = null;

    private GsonUtils() {
        super();
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new Gson();
        }
    }

    public static Gson getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

}
