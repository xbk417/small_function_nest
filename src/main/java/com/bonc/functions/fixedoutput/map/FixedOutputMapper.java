package com.bonc.functions.fixedoutput.map;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 
 * @author xiabaike
 * @date 2016年8月30日
 */
public class FixedOutputMapper extends Mapper<LongWritable, Text, Text, NullWritable>{

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		
		context.write(value, NullWritable.get());
	}
	
}
