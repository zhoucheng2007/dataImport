package com.shop.base.check;

import java.util.HashMap;
import java.util.Map;

/**
 * @title:Map转换工具类
 * @description:map转string,string转map
 * @author sunfs
 * @mail:sunfs@inspur.com
 * @date:2013-3-25
 */
public class MapUtils {  
	  
    
    private static final String SEP1 = "#####"; //每段的分隔符  
    private static final String SEP2 = "&&&&&";  //段内分隔符
  
     
     
    public static String MapToString(Map map) {  
        StringBuffer sb = new StringBuffer();  
        // 遍历map  
        for (Object obj : map.keySet()) {  
            if (obj == null) {  
                continue;  
            }  
            Object key = obj;  
            Object value = map.get(key);  
//            if (value instanceof List<?>) {  
//                sb.append(key.toString() + SEP1 + ListToString((List<?>) value));  
//                sb.append(SEP2);  
//            } else if (value instanceof Map<?, ?>) {  
//                sb.append(key.toString() + SEP1  
//                        + MapToString((Map<?, ?>) value));  
//                sb.append(SEP2);  
//            } else {  
                sb.append(key.toString() + SEP1 + value.toString());  
                sb.append(SEP2);  
//            }  
        }  
        return sb.toString();  
    }  
  
     
    public static Map StringToMap(String mapText) {  
  
        if (mapText == null || mapText.equals("")) {  
            return null;  
        }  
        
  
        Map map = new HashMap();  
        String[] text = mapText.split(SEP2); // 转换为数组  
        
        for (String str : text) {  
        	System.out.println(str);
            String[] keyText = str.split(SEP1); // 转换key与value的数组  
            if (keyText.length < 1) {  
                continue;  
            }  
            
            String key = keyText[0]; // key  
            String value = keyText[1]; // value  
//            if (value.charAt(0) == 'M') {  
//                Map<?, ?> map1 = StringToMap(value);  
//                map.put(key, map1);  
//            } else if (value.charAt(0) == 'L') {  
//                List<?> list = StringToList(value);  
//                map.put(key, list);  
//            } else {  
                map.put(key, value);  
//            }  
        }  
        return map;  
    }  
  
    public static void main(String[] args) {
		Map map = new HashMap();
		map.put("aa", "测试aa");
		map.put("测试键","测试值");
		
		String value = MapToString(map);
		System.out.println(value);
		Map map2 = StringToMap(value);
		System.out.println(map2);
	}
     
   
  
}  