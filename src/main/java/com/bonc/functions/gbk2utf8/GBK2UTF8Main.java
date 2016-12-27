package com.bonc.functions.gbk2utf8;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.bonc.functions.gbk2utf8.map.GBK2UTF8Mapper;
import com.bonc.functions.gbk2utf8.util.GBK2UTF8Text;
import com.bonc.functions.gbk2utf8.util.GBK2UTF8TextInputFormat;
import com.bonc.functions.gbk2utf8.util.GBK2UTF8TextOutputFormat;
import com.bonc.functions.utils.KerberosCommon;

import org.apache.hadoop.io.compress.CompressionCodec;

public class GBK2UTF8Main extends Configured implements Tool {

	public static void main(String[] args){
		try {
			ToolRunner.run(new Configuration(), new GBK2UTF8Main(), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		FileSystem fs = KerberosCommon.kerberos(conf);
		Path outPath = new Path(args[1]);
		if (fs.exists(outPath)) {
			fs.delete(outPath, true);
		}
		String jobName = null;
		if(args.length > 2) {
			jobName = args[2];
		}else{
			jobName = "gbk2utf8";
		}
		conf.set("decode.name", "gbk");

		Job job = Job.getInstance(conf, jobName);
		job.setJarByClass(GBK2UTF8Main.class);
		job.setMapperClass(GBK2UTF8Mapper.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(GBK2UTF8Text.class);
		job.setInputFormatClass(GBK2UTF8TextInputFormat.class);
		job.setOutputFormatClass(GBK2UTF8TextOutputFormat.class);
		job.setNumReduceTasks(0);

		FileInputFormat.addInputPath(job, new Path(args[0])); // 设置输入路径
		FileOutputFormat.setOutputPath(job, outPath);
		String isCompress = conf.get("compress.codec");
		if(isCompress != null && !"".equals(isCompress)) {
			FileOutputFormat.setCompressOutput(job, true);
			FileOutputFormat.setOutputCompressorClass(job, (Class<? extends CompressionCodec>) conf.getClassByName(isCompress));
		}
		
		return job.waitForCompletion(true) ? 0 : 1;
	}

}
