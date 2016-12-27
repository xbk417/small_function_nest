package com.bonc.functions.fixedoutput.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.bonc.functions.fixedoutput.map.FixedOutputMapper;
import com.bonc.functions.utils.CommonUtils;
import com.bonc.functions.utils.KerberosCommon;
import com.bonc.functions.utils.tool.CombineSmallfileInputFormat;

/**
 * 
 * @author xiabaike
 * @date 2016年8月30日
 */
public class FixedOutputTool extends Configured implements Tool{
			
	public static void exec(String[] txt) throws Exception {
		System.out.println("===========jobstarttime:"+ new SimpleDateFormat("yyyy-MM–dd HH:mm:ss").format(new Date()));
		ToolRunner.run(new Configuration(), new FixedOutputTool(), txt);
		System.out.println("===========jobendtime:"+ new SimpleDateFormat("yyyy-MM–dd HH:mm:ss").format(new Date()));
	}

	@Override
	public int run(String[] arg0) throws Exception {
		String usage = "FixedOutputTool [-inputPath <inputPath> -outputPath <outputPath> -configPath <configPath> -jobName <jobName>]";
		System.out.println(usage);
		String inputPath = null;
		String outputPath = null;
		String configPath = null;
		String jobName = null;
		try{
			for(int i = 0 ; i < arg0.length ; i++){
				if("-inputPath".equals(arg0[i])){
					inputPath = arg0[++i];
				}else if("-outputPath".equals(arg0[i])){
					outputPath = arg0[++i];
				}else if("-configPath".equals(arg0[i])){
					configPath = arg0[++i];
				}else if("-jobName".equals(arg0[i])){
					jobName = arg0[++i];
				}
			}
			
			Configuration conf = getConf();
			conf.addResource(new Path(configPath));
			
			FileSystem dst = KerberosCommon.kerberos(conf);
			Path dstPath = new Path(outputPath);
			dst.delete(dstPath,true);
			
			if("".equals(jobName) || jobName == null) {
				jobName = "Fixed Size Output";
			}
			Job job = Job.getInstance(conf, jobName);
			job.setJarByClass(FixedOutputTool.class);
			job.setMapperClass(FixedOutputMapper.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(NullWritable.class);
			String iscombine = conf.get("iscombine", "0");
			if("1".equals(iscombine)) {
				job.setInputFormatClass(CombineSmallfileInputFormat.class);
			}else{
				job.setInputFormatClass(TextInputFormat.class);
			}
			job.setNumReduceTasks(0);
			FileInputFormat.setInputDirRecursive(job, true);
			FileInputFormat.setInputPaths(job, new Path(inputPath));
			FileOutputFormat.setOutputPath(job, dstPath);
			String isCompress = conf.get("compress.codec");
			if(!CommonUtils.isNull(isCompress)) {
				FileOutputFormat.setCompressOutput(job, true);
				FileOutputFormat.setOutputCompressorClass(job, (Class<? extends CompressionCodec>) conf.getClassByName("compress.codec"));
			}
			
			return job.waitForCompletion(true) ? 0 : 1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 1;
	}
	
}


	