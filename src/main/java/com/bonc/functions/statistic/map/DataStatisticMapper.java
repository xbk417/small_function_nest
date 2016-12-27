package com.bonc.functions.statistic.map;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.bonc.functions.utils.String2Hex;

/**
 * 获取每个数据行的字段数
 * @author xiabaike
 * @date 2016年8月26日
 */
public class DataStatisticMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

	private final IntWritable one = new IntWritable(1);
	// separator
	private static final String DATA_SEPARATOR = "statistic.data.separator";
	// 数据分隔符
	private static String separator = null;
	
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		String2Hex hex = new String2Hex(context.getConfiguration().get(DATA_SEPARATOR, "\t"));
		separator = hex.getHexString();
	}
	
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] values = value.toString().split(separator, -1);
		context.write(new Text(String.valueOf(values.length)), one);
	}
	
}
