package com.sinostar.assistant.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapUtil {
    //根据value值获取到对应的一个key值
    public static String getKeyFromValue(HashMap<String, String> map, String value) {
        String key = null;
        //Map,HashMap并没有实现Iteratable接口.不能用于增强for循环.
        for (String getKey : map.keySet()) {
            if (map.get(getKey).equals(value)) {
                key = getKey;
            }
        }
        return key;
        //这个key肯定是最后一个满足该条件的key.
    }

    //根据value值获取到对应的所有的key值
    public static List<String> getKeyListFromValue(Map<String, Object> map) {
        List<String> keyList = new ArrayList();
        for (String getKey : map.keySet()) {
                keyList.add(getKey);
        }
        return keyList;
    }

    //获取map的key
    public static String getkey(Map<String,Object> map){
        String key = null;
        Iterator i = map.entrySet().iterator();
        while (i.hasNext()) {
            Object obj = i.next();
             key = obj.toString();
        }
        return key;
    }

    public static List<Object> getValueList(Map<String,Object>map){
        Object value;
        List<Object>list=new ArrayList<>();
        for (Object key : map.values()) {
            value = key;
            list.add(value);
        }
        return list;

    }
    public static List<Object> getKeyList(Map<String,Object>map){
        List<Object>list=new ArrayList<>();
        for (String key : map.keySet()) {
            list.add(key);
        }
        return list;

    }


}
