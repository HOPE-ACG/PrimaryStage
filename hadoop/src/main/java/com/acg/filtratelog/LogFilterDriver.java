package com.acg.filtratelog;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class LogFilterDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        job.setJarByClass(LogFilterDriver.class);
        job.setMapperClass(LogFilterMapper.class);
        job.setReducerClass(LogFilterReducer.class);

        job.setOutputFormatClass(LogFilterOutputFormat.class);

        //must be set, otherwise report type mismatch exception
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path("D:\\hadoop\\尚硅谷资料\\资料\\11_input\\inputoutputformat\\log.txt"));
        //set this path for outputting other necessary, such as '_SUCCESS' file
        FileOutputFormat.setOutputPath(job, new Path("D:\\hadoop\\myproject\\outputresult\\logfilter"));

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
