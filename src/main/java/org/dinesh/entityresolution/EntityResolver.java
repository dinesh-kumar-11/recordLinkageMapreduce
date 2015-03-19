package org.dinesh.entityresolution;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.dinesh.entityresolution.CleanerJob.CleanerMapper;
import org.dinesh.er.comparators.Comparator;
import org.dinesh.er.config.ErConfiguration;
import org.dinesh.er.config.ErProperty;
import org.dinesh.er.config.ErConfigLoader;

/**
 * An record linkage or entity resolution framework using mapreduce
 * 
 */
public class EntityResolver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new EntityResolver(), args);
		System.exit(exitCode);
	}

	@Override
	public int run(String[] arg0) throws Exception {
		Job cleanerJob = new Job(getConf());
		String jobName = "Cleaner Job";

		GenericOptionsParser optionParser = new GenericOptionsParser(getConf(),
				arg0);
		Configuration conf = optionParser.getConfiguration();
		cleanerJob.setJobName(jobName);
		cleanerJob.setJarByClass(CleanerJob.class);
		
		ErConfiguration erConfig = ErConfigLoader.load(conf.get("config.file.path"));
		List<Comparator>  cp = erConfig.getCustomComparators();
		for( Comparator d: cp) {
			System.out.println(d);
		}
		
		List<ErProperty>  prop = erConfig.getProperties();
		for( ErProperty d: prop) {
			System.out.println(d);
		}
		
		FileInputFormat.addInputPath(cleanerJob, new Path(
				"/er_data/example-data/countries-dbpedia.csv"));
		FileOutputFormat.setOutputPath(cleanerJob,
				new Path(conf.get("output.file.path")));

		cleanerJob.setMapperClass(CleanerMapper.class);

		cleanerJob.setOutputKeyClass(Text.class);
		cleanerJob.setOutputValueClass(NullWritable.class);
		

		int numReducer = cleanerJob.getConfiguration().getInt(
				"sts.num.reducer", -1);
		numReducer = -1 == numReducer ? cleanerJob.getConfiguration().getInt(
				"num.reducer", 1) : numReducer;
		cleanerJob.setNumReduceTasks(numReducer);

		int status = cleanerJob.waitForCompletion(true) ? 0 : 1;
		return status;
	}
}
