package com.baidu.vmonitor.utils.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * To simplify reading operation of property file, do <b>NOT</b> use this class
 * in your code directly. You can use {@code UtilsConfig} , {@code DomainConfig}
 * ,{@code ServiceConfig},{@code WebConfig} or {@code TaskConfig} .
 * 
 * @author jinzixiang
 * 
 */
public class PropertyFileReader<K extends Key> {

    private final ResourceBundle rb;

    public PropertyFileReader(String configFileName) {
        rb = ResourceBundle.getBundle(configFileName);
    }


    public String getString(K key) {
        return rb.getString(key.key());
    }

    public int getInt(K key) {
        return Integer.parseInt(rb.getString(key.key()));
    }

    public Boolean getBoolean(K key) {
        return Boolean.parseBoolean(rb.getString(key.key()));
    }

    public long getLong(K key) {
        return Long.parseLong(rb.getString(key.key()));
    }

    public float getFloat(K key) {
        return Float.parseFloat(rb.getString(key.key()));
    }

    public double getDouble(K key) {
        return Double.parseDouble(rb.getString(key.key()));
    }

    public List<String> getStringList(K key) {
        String[] prop = rb.getString(key.key()).split(",");
        List<String> list = new ArrayList<String>();
        for (String s : prop) {
            list.add(s);
        }
        return list;
    }

    public List<Integer> getIntegerList(K key) {
        String[] prop = rb.getString(key.key()).split(",");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : prop) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    public List<Long> getLongList(K key) {
        String[] prop = rb.getString(key.key()).split(",");
        List<Long> list = new ArrayList<Long>();
        for (String s : prop) {
            list.add(Long.parseLong(s));
        }
        return list;
    }

    public List<Float> getFloatList(K key) {
        String[] prop = rb.getString(key.key()).split(",");
        List<Float> list = new ArrayList<Float>();
        for (String s : prop) {
            list.add(Float.parseFloat(s));
        }
        return list;
    }

    public List<Double> getDoubleList(K key) {
        String[] prop = rb.getString(key.key()).split(",");
        List<Double> list = new ArrayList<Double>();
        for (String s : prop) {
            list.add(Double.parseDouble(s));
        }
        return list;
    }

    public String getString(K key, Object... args) {
        String value = rb.getString(key.key());
        if (args == null)
            return value;
        for (int i = 0; i < args.length; i++) {
            value = value.replace("{" + i + "}", args[i].toString());
        }
        return value;
    }
}
