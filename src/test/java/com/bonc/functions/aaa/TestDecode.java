package com.bonc.functions.aaa;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 
 * @author xiabaike
 * @date 2016年8月29日
 */
public class TestDecode {

	
	public static void main(String[] args) {
		String str = "\u007c";
		System.out.println(str);
		try {
			str = URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		System.out.println(sb.append(str).append(" a"));
	}
}