package com.bonc.functions.aaa.map;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.LoggerFactory;

import com.bonc.functions.aaa.decrypt.IDecrypt;
import com.bonc.functions.utils.CommonUtils;

/**
 * AAA数据处理
 * @author xiabaike
 * @date 2016年8月29日
 */
public class AAADealMapper extends Mapper<LongWritable, Text, NullWritable, Text>{

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AAADealMapper.class);
	// separator
	private static final String DATA_SEPARATOR = "aaa.data.separator";
	private static final String OUT_SEPARATOR = "aaa.output.separator";
	private static final String DECRYPT_FIELDS = "aaa.data.decrypt.fields";
	// 数据解密类
	private static final String DECRYPT_CLASS = "aaa.data.decrypt.class";
	// 数据分隔符
	private static String separator = null;
	// 输出分隔符
	private static String outputSepatator = null;
	// 需要解密的字段
	private static int[] decryptFields = null;
	private IDecrypt decrypt = null;
	
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		separator = context.getConfiguration().get(DATA_SEPARATOR, "\\|");
		outputSepatator = context.getConfiguration().get(OUT_SEPARATOR, "|");
		String fieldStr = context.getConfiguration().get(DECRYPT_FIELDS);
		if(!CommonUtils.isNull(fieldStr)) {
			String[] fields = fieldStr.split(",", -1);
			decryptFields = new int[fields.length];
			for(int i = 0; i < fields.length; i++) {
				decryptFields[i] = Integer.parseInt(fields[i]);
			}
		}
		
		// 数据解密对象
		decrypt = (IDecrypt)createObject(context.getConfiguration().get(DECRYPT_CLASS, "com.bonc.functions.aaa.decrypt.DefaultDecrypt"));
	}
	
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		try{
			String[] values = value.toString().split(separator, -1);
			if(decryptFields != null) {
				int length = values.length;
				for(int field : decryptFields) {
					if(field < length) {
						values[field] = decrypt.decrypt(values[field]);
					}
				}
			}

			StringBuilder sb = new StringBuilder();
			for(String val : values) {
				sb.append(val).append(outputSepatator);
			}
			sb.deleteCharAt(sb.length() - 1);
			context.write(NullWritable.get(), new Text(sb.toString()));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static Object createObject(String className) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
			return clazz.getConstructor().newInstance();
		} catch (Exception e) {
			LOG.error("加载数据解密类 {} 时出错......", className);
			e.printStackTrace();
		}
		return clazz;
	}
}
