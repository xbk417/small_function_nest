package com.bonc.functions.aaa.decrypt;

import com.bonc.functions.utils.Base64;

/**
 * 默认的数据解密类
 * @author xiabaike
 * @date 2016年8月29日
 */
public class DefaultDecrypt implements IDecrypt{

	@Override
	public String decrypt(String str) {
		return Base64.decode(str);
	}

}