package com.sinostar.assistant.utils;

public class StringUtil {
    //str 源字符串
//strStart 起始字符串
//strEnd 结束字符串

    public static String  getInsideString(String  str, String strStart, String strEnd ) {
        if ( str.indexOf(strStart) < 0 ){
            return "";
        }
        if ( str.indexOf(strEnd) < 0 ){
            return "";
        }
        return str.substring(str.indexOf(strStart) + strStart.length(), str.indexOf(strEnd));
    }
}
