package com.acg.joinapplication;

import com.acg.bean.Order;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class JoinApplicationDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        job.setJarByClass(JoinApplicationDriver.class);
        job.setMapperClass(JoinApplicationMapper.class);
        job.setReducerClass(JoinApplicationReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Order.class);

        /*job.setOutputKeyClass(Order.class);
        job.setOutputValueClass(NullWritable.class);*/

        FileInputFormat.setInputPaths(job, new Path("D:\\hadoop\\尚硅谷资料\\资料\\11_input\\inputtable"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\hadoop\\myproject\\outputresult\\joinapplication"));

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
