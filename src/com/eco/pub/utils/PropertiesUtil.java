package com.eco.pub.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * 读取properties文件的工具
 *
 * @author sunzhen
 */
public final class PropertiesUtil {
    private static Map loadCache = new HashMap();

    private static Properties loadProperties(String location) {
        InputStream in = null;
        Properties properties = null;
        try {
            properties = new Properties();
            if (location.startsWith("/")) {
                location = location.substring(1);
            }
            ClassLoader ccl = PropertiesUtil.class.getClassLoader();
            in = ccl.getResourceAsStream(location);
            if (in != null) {
                properties.load(in);
                loadCache.put(location, properties);
            } else {
                throw new FileNotFoundException(location);
            }
            in.close();
        } catch (IOException e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return properties;
    }

    public static String getString(String key, String location) {
        Properties properties = (Properties) loadCache.get(location);
        if (properties == null) {
            properties = loadProperties(location);
        }
        return properties.getProperty(key);
    }

    public static Map<String, String> getMap(String key, String location) {
        Properties properties = (Properties) loadCache.get(location);
        if (properties == null) {
            properties = loadProperties(location);
        }
        Map map = new HashMap();
        if ((key == null) || (key.equals("")))
            return map;
        Enumeration enums = properties.propertyNames();
        while (enums.hasMoreElements()) {
            String item = (String) enums.nextElement();
            if (item.indexOf(key) >= 0) {
                map.put(item.substring(key.length() + 1), properties.getProperty(item));
            }
        }

        return map;
    }

    public static List<String> getList(String key, String location) {
        Properties properties = (Properties) loadCache.get(location);
        if (properties == null) {
            properties = loadProperties(location);
        }
        List list = new ArrayList();
        if ((key == null) || (key.equals("")))
            return list;
        Enumeration enums = properties.propertyNames();
        while (enums.hasMoreElements()) {
            String item = (String) enums.nextElement();
            if (item.indexOf(key) >= 0) {
                list.add(properties.getProperty(item));
            }
        }

        return list;
    }
}