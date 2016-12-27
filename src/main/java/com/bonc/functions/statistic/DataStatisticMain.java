package com.bonc.functions.statistic;

import com.bonc.functions.statistic.tool.DataStatisticTool;

/**
 * 
 * @author xiabaike
 * @date 2016年8月26日
 */
public class DataStatisticMain {

	public static void main(String[] args) {
		try {
			// 输入路径，输出路径，配置文件路径，job名
			DataStatisticTool.exec(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
