package com.bonc.functions.gbk2utf8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringToFile {

	public static void stringToFile(String str) {
		byte[] buff = new byte[] {};
		try {
			String aa = str;
			buff = aa.getBytes();
			FileOutputStream out = new FileOutputStream("D://out.txt");
			out.write(buff, 0, buff.length);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void stringToFile(String str, String filePath) {
		byte[] buff = new byte[] {};
		try {
			String aa = str;
			buff = aa.getBytes();
			FileOutputStream out = new FileOutputStream(filePath);
			out.write(buff, 0, buff.length);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int getRandom(){
		Random ran = new Random();
		int num = ran.nextInt(100);
		return num;
	}
	
	public static List<String> txtToStringList(String filename) {
		List<String> list = new ArrayList<String>();
		InputStream fis = null;
		BufferedReader bis = null;
		try {
			fis = StringToFile.class.getResourceAsStream(filename);
			bis = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			String line;
			while((line = bis.readLine()) != null){
//				if( !line.startsWith("##") && !list.contains(line)) {
					list.add(line);
//				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) {
		List<String> list = txtToStringList("/PGW_PRE_2016051803_0.TXT");
		for(String str : list) {
			System.out.println(str+"               ============================  ");
		}
		String sa = "adfsdfas";
		System.out.println(sa.toUpperCase());
	}
}
