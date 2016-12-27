package com.bonc.functions.utils.tool;

import java.io.IOException;
import java.text.NumberFormat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileAlreadyExistsException;
import org.apache.hadoop.mapred.InvalidJobConfException;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.security.TokenCache;

/**
 * 
 * @author xiabaike
 * @date 2016年9月5日
 */
public class NoCheckSpecsOutputFormat<K, V> extends TextOutputFormat<K, V>{
	
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();
	static {
		NUMBER_FORMAT.setMinimumIntegerDigits(5);
		NUMBER_FORMAT.setGroupingUsed(false);
	}
	
	public synchronized static String getUniqueFile(TaskAttemptContext context, String name, String extension) {
		JobID jobId = context.getJobID();
		StringBuilder result = new StringBuilder();
		result.append(name);
		result.append('-');
		result.append(jobId.getJtIdentifier());
		result.append('-');
		result.append(NUMBER_FORMAT.format(jobId.getId()));
		result.append(extension);
		return result.toString();
	}
	
	public Path getDefaultWorkFile(TaskAttemptContext context, String extension) throws IOException{
		FileOutputCommitter committer = (FileOutputCommitter) getOutputCommitter(context);
		return new Path(committer.getWorkPath(), getUniqueFile(context, getOutputName(context), extension));
	}

	public void checkOutputSpecs(JobContext job) throws FileAlreadyExistsException, IOException{
		// Ensure that the output directory is set and not already there
		Path outDir = getOutputPath(job);
		if (outDir == null) {
			throw new InvalidJobConfException("Output directory not set.");
		}

		// get delegation token for outDir's file system
		TokenCache.obtainTokensForNamenodes(job.getCredentials(), new Path[] { outDir }, job.getConfiguration());

//		if (outDir.getFileSystem(job.getConfiguration()).exists(outDir)) {
//			throw new FileAlreadyExistsException("Output directory " + outDir + " already exists");

	}
	
}