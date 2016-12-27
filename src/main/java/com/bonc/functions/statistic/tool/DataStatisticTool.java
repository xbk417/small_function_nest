package com.bonc.functions.statistic.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.bonc.functions.statistic.map.DataStatisticMapper;
import com.bonc.functions.statistic.reduce.DataStatisticReducer;
import com.bonc.functions.utils.CommonUtils;
import com.bonc.functions.utils.KerberosCommon;
import com.bonc.functions.utils.tool.CombineSmallfileInputFormat;
import com.bonc.functions.utils.tool.FirstLineDiscardTextInputFormat;
import com.bonc.functions.utils.tool.NoCheckSpecsOutputFormat;

/**
 * 
 * @author xiabaike
 * @date 2016年8月26日
 */
public class DataStatisticTool extends Configured implements Tool{
	
	public static final String DATA_STATISTIC_FLAG = "data.statistic.flag";
	
	public static void exec(String[] txt) throws Exception {
		System.out.println("===========jobstarttime:"+ new SimpleDateFormat("yyyy-MM–dd HH:mm:ss").format(new Date()));
		ToolRunner.run(new Configuration(), new DataStatisticTool(), txt);
		System.out.println("===========jobendtime:"+ new SimpleDateFormat("yyyy-MM–dd HH:mm:ss").format(new Date()));
	}

	@Override                                          
	public int run(String[] arg0) throws Exception {
		String usage = "StatisticTool [-inputPath <inputPath> -outputPath <outputPath> -configPath <configPath> -jobName <jobName> -reduceNum <reduceNum> -flag <flag>]";
		System.out.println(usage);
		String inputPath = null;
		String outputPath = null;
		String configPath = null;
		String jobName = null;
		String flag = null;
		int reduceNum = 1;
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
				}else if("-reduceNum".equals(arg0[i])){
					String reduceNumStr = arg0[++i];
					if(!CommonUtils.isNull(reduceNumStr)) {
						reduceNum = Integer.parseInt(reduceNumStr);
					}
				}else if("-flag".equals(arg0[i])){
					flag = arg0[++i];
				}
			}
			
			Configuration conf = getConf();
			conf.addResource(new Path(configPath));
			conf.set(DATA_STATISTIC_FLAG, flag);
			
			FileSystem dst = KerberosCommon.kerberos(conf);
			Path dstPath = new Path(outputPath);
//			dst.delete(dstPath,true);
			
			if("".equals(jobName) || jobName == null) {
				jobName = "Data Statistic";
			}
			Job job = Job.getInstance(conf, jobName);
			job.setJarByClass(DataStatisticTool.class);
			job.setMapperClass(DataStatisticMapper.class);
//			job.setCombinerClass(DataStatisticReducer.class);
			job.setReducerClass(DataStatisticReducer.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(LongWritable.class);
			String iscombine = conf.get("iscombine", "0");
			if("1".equals(iscombine)) {
				job.setInputFormatClass(CombineSmallfileInputFormat.class);
			}else{
				job.setInputFormatClass(FirstLineDiscardTextInputFormat.class);
			}
			job.setOutputFormatClass(NoCheckSpecsOutputFormat.class);
			job.setNumReduceTasks(reduceNum);
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
