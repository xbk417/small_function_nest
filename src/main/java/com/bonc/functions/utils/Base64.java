package com.bonc.functions.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {

	public static BASE64Encoder be = new BASE64Encoder();
	public static BASE64Decoder bd = new BASE64Decoder();

	public static String encode(String data) {
		return be.encode(data.getBytes());
	}

	public static String decode(String encode) {
		try {
			return new String(bd.decodeBuffer(encode), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) throws IOException {
		String data = "http://www.whylover.com/123.exe";

		System.out.println(encode(data));

		System.out.println(decode(encode(data)));
		System.out
				.println(decode("TW96aWxsYS81LjAgKExpbnV4OyBVOyBBbmRyb2lkIDAuMC4wOyB6aC1DTjsgLi4uIEJ1aWxkL01vY29yRHJvaWQyLjMuNSkgQXBwbGVXZWJLaXQvNTMzLjEgKEtIVE1MLCBsaWtlIEdlY2tvKSBWZXJzaW9uLzQuMCBVQ0Jyb3dzZXIvOS42LjAuMzc4IFUzLzAuOC4wIE1vYmlsZSBTYWZhcmkvNTMzLjE="));
	}

}
