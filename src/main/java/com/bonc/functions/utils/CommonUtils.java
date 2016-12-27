package com.bonc.functions.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 
 * @author xiabaike
 * @date 2016年8月26日
 */
public class CommonUtils {
	
	/**
	 * 判断是否为空
	 */
	public static boolean isNull(String str) {
		if(str != null && !"".equals(str)) {
			return false;
		}
		return true;
	}

	/**
	 * 转码
	 */
	public static String decode(String str, String enc) {
		String result = null;
		try {
			result = URLDecoder.decode(str, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
