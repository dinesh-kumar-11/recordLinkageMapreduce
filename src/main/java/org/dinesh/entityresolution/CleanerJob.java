package org.dinesh.entityresolution;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.dinesh.er.utils.StringUtils;

public class CleanerJob {
	
	

	public static class CleanerMapper extends
			Mapper<Object, Text, Text, NullWritable> {
		
		Log log = LogFactory.getLog(CleanerMapper.class);

		private Text word = new Text();

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			/*StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				//context.write(word, one);
			}*/
			String[] tokens = StringUtils.splitCsvString(value.toString());
			for(String t : tokens) {
	          System.out.println("> "+t);
	          log.info("Tokens > " + t);
	          
			}
			context.write(value, NullWritable.get());
			log.info("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
			System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
			System.out.println(value);
			log.info(value);
			System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
			log.info("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		}
	}

}
