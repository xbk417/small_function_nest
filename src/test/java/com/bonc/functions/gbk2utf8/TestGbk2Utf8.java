package com.bonc.functions.gbk2utf8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author xiabaike
 * @date 2016年9月7日
 */
public class TestGbk2Utf8 {

	public static List<String> txtToStringList(String filename) {
		List<String> list = new ArrayList<String>();
		InputStream fis = null;
		BufferedReader bis = null;
		try {
			fis = new FileInputStream(filename);
			bis = new BufferedReader(new InputStreamReader(fis, "gbk"));
			String line;
			while((line = bis.readLine()) != null){
//				if( !line.startsWith("##") && !list.contains(line)) {
					list.add(line);
					System.out.println(line);
//				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) {
		txtToStringList("F:\\cygwin\\home\\xiabaike\\hadoop\\RESULT\\gbk2utf8\\part-m-00000");
	}
	
}