package com.bonc.functions.gbk2utf8.map;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.bonc.functions.gbk2utf8.util.GBK2UTF8Text;

public class GBK2UTF8Mapper extends Mapper<LongWritable, GBK2UTF8Text, NullWritable, GBK2UTF8Text> {

	@Override
	protected void map(LongWritable key, GBK2UTF8Text value, Mapper<LongWritable, GBK2UTF8Text, NullWritable, GBK2UTF8Text>.Context context) throws IOException, InterruptedException {
//		String line = value.toString();
//		System.out.println(value.toString());
		context.write(NullWritable.get(), value);
	}

}
