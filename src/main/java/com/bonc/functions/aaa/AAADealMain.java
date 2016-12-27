package com.bonc.functions.aaa;

import com.bonc.functions.aaa.tool.AAADealTool;

/**
 * 
 * @author xiabaike
 * @date 2016年8月29日
 */
public class AAADealMain {

	public static void main(String[] args) {
		try {
			// 输入路径，输出路径，配置文件路径，job名
			AAADealTool.exec(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}