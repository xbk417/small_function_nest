package com.bonc.functions.statistic.reduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.bonc.functions.statistic.tool.DataStatisticTool;

/**
 * 汇总字段数的数量
 * @author xiabaike
 * @date 2016年8月26日
 */
public class DataStatisticReducer extends Reducer<Text, IntWritable, Text, LongWritable>{

	private static final String DATA_STATISTIC_OUTPUT_SEPARATOR = "data.statistic.output.separator";
	
	public static String flag = null;
	private static String separator = null;
	
	protected void setup(Context context) throws IOException, InterruptedException {
		flag = context.getConfiguration().get(DataStatisticTool.DATA_STATISTIC_FLAG);
		separator = context.getConfiguration().get(DATA_STATISTIC_OUTPUT_SEPARATOR, "\t");
	}
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		String text = new StringBuilder().append(flag).append(separator).append(key.toString()).toString();
		long sum = 0;
		for(IntWritable val : values) {
			sum += val.get();
		}
		context.write(new Text(text), new LongWritable(sum));
	}
	
}
