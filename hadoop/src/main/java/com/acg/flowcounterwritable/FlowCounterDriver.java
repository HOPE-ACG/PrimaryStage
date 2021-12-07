package com.acg.flowcounterwritable;

import com.acg.bean.PhoneWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowCounterDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(FlowCounterDriver.class);

        job.setMapperClass(FlowCounterMapper.class);
        job.setReducerClass(FlowCounterReducer.class);

        //set key and value of mapper's output
        job.setMapOutputKeyClass(PhoneWritable.class);
        job.setMapOutputValueClass(Text.class);

        //set key and value of final output
        //it is ok that don't set this
        /*job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(PhoneWritable.class);*/

        job.setPartitionerClass(FlowCounterPartition.class);
        job.setNumReduceTasks(4);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
