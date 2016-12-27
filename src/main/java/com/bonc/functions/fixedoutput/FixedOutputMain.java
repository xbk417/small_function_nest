package com.bonc.functions.fixedoutput;

import com.bonc.functions.fixedoutput.tool.FixedOutputTool;

/**
 * 
 * @author xiabaike
 * @date 2016年8月30日
 */
public class FixedOutputMain {

	public static void main(String[] args) {
		try {
			// 输入路径，输出路径，配置文件路径，job名
			FixedOutputTool.exec(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
