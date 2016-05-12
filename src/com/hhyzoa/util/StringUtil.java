package com.hhyzoa.util;

public class StringUtil {
	
	public static String trim(String str) {
        return str == null ? "" : str.trim();
    }
	
	public static boolean isEmpty(String cs) {
        return cs == "" || cs.length() == 0;
    }

}
