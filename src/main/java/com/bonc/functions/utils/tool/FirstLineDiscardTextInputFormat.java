package com.bonc.functions.utils.tool;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.SplittableCompressionCodec;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import com.google.common.base.Charsets;

/**
 * 
 * @author xiabaike
 * @date 2016年8月26日
 */
public class FirstLineDiscardTextInputFormat extends TextInputFormat{

	@Override
	  public RecordReader<LongWritable, Text> 
	    createRecordReader(InputSplit split,
	                       TaskAttemptContext context) {
	    String delimiter = context.getConfiguration().get(
	        "textinputformat.record.delimiter");
	    byte[] recordDelimiterBytes = null;
	    if (null != delimiter)
	      recordDelimiterBytes = delimiter.getBytes(Charsets.UTF_8);
	    return new FirstLineDiscardRecordReader(recordDelimiterBytes);
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