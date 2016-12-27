package com.bonc.functions.gbk2utf8.util;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.SplittableCompressionCodec;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import com.google.common.base.Charsets;

/**
 * 
 * @author xiabaike
 * @date 2016年8月16日
 */
public class GBK2UTF8TextInputFormat extends FileInputFormat<LongWritable, GBK2UTF8Text>{

	@Override
	  public RecordReader<LongWritable, GBK2UTF8Text> 
	    createRecordReader(InputSplit split,
	                       TaskAttemptContext context) {
	    String delimiter = context.getConfiguration().get(
	        "textinputformat.record.delimiter");
	    byte[] recordDelimiterBytes = null;
	    if (null != delimiter)
	      recordDelimiterBytes = delimiter.getBytes(Charsets.UTF_8);
	    return new GBK2UTF8LineRecordReader(recordDelimiterBytes);
	  }

	  @Override
	  protected boolean isSplitable(JobContext context, Path file) {
	    final CompressionCodec codec =
	      new CompressionCodecFactory(context.getConfiguration()).getCodec(file);
	    if (null == codec) {
	      return true;
	    }
	    return codec instanceof SplittableCompressionCodec;
	  }
	
}