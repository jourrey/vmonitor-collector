package com.baidu.vmonitor.utils.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 此类的作用是为了简化你读取properties文件，与PropertyFileReader不同的是，PropertyFileReader获取对应key值时
 * ，不存在该key报错，但为了特殊服务需要，PropertyFileReaderInexistence读取不存在的key时，返回null.
 * 
 * @author liuzheng03
 * 
 */
public class PropertyFileReaderInexistence<K extends Key> {

    private ResourceBundle rb;

    public PropertyFileReaderInexistence(String configFileName) {
        try {
            rb = ResourceBundle.getBundle(configFileName);
        } catch (Throwable e) {
            rb = null;
        }
    }

    public String getString(K key) {
        try {
            return rb.getString(key.key());
        } catch (Throwable e) {
            return null;
        }
    }

    public Integer getInt(K key) {
        try {
            return Integer.parseInt(rb.getString(key.key()));
        } catch (Throwable e) {
            return null;
        }
    }

    public Boolean getBoolean(K key) {
        try {
            return Boolean.parseBoolean(rb.getString(key.key()));
        } catch (Throwable e) {
            return null;
        }
    }

    public Long getLong(K key) {
        try {
            return Long.parseLong(rb.getString(key.key()));
        } catch (Throwable e) {
            return null;
        }
    }

    public Float getFloat(K key) {
        try {
            return Float.parseFloat(rb.getString(key.key()));
        } catch (Throwable e) {
            return null;
        }
    }

    public Double getDouble(K key) {
        try {
            return Double.parseDouble(rb.getString(key.key()));
        } catch (Throwable e) {
            return null;
        }
    }

    public List<String> getStringList(K key) {
        try {
            String[] prop = rb.getString(key.key()).split(",");
            List<String> list = new ArrayList<String>();
            for (String s : prop) {
                list.add(s);
            }
            return list;
        } catch (Throwable e) {
            return null;
        }
    }

    public List<Integer> getIntegerList(K key) {
        try {
            String[] prop = rb.getString(key.key()).split(",");
            List<Integer> list = new ArrayList<Integer>();
            for (String s : prop) {
                list.add(Integer.parseInt(s));
            }
            return list;
        } catch (Throwable e) {
            return null;
        }
    }

    public List<Long> getLongList(K key) {
        try {
            String[] prop = rb.getString(key.key()).split(",");
            List<Long> list = new ArrayList<Long>();
            for (String s : prop) {
                list.add(Long.parseLong(s));
            }
            return list;
        } catch (Throwable e) {
            return null;
        }
    }

    public List<Float> getFloatList(K key) {
        try {
            String[] prop = rb.getString(key.key()).split(",");
            List<Float> list = new ArrayList<Float>();
            for (String s : prop) {
                list.add(Float.parseFloat(s));
            }
            return list;
        } catch (Throwable e) {
            return null;
        }
    }

    public List<Double> getDoubleList(K key) {
        try {
            String[] prop = rb.getString(key.key()).split(",");
            List<Double> list = new ArrayList<Double>();
            for (String s : prop) {
                list.add(Double.parseDouble(s));
            }
            return list;
        } catch (Throwable e) {
            return null;
        }
    }

    public String getString(K key, Object... args) {
        try {
            String value = rb.getString(key.key());
            if (args == null)
                return value;
            for (int i = 0; i < args.length; i++) {
                value = value.replace("{" + i + "}", args[i].toString());
            }
            return value;
        } catch (Throwable e) {
            return null;
        }
    }

}
