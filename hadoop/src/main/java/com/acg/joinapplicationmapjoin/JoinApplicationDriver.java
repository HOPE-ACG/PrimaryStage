package com.acg.joinapplicationmapjoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class JoinApplicationDriver {

    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {

        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        job.setJarByClass(JoinApplicationDriver.class);
        job.setMapperClass(JoinApplicationMapper.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.addCacheFile(new URI("file:///D:/hadoop/尚硅谷资料/资料/11_input/inputtable/pd.txt"));

        job.setNumReduceTasks(0);

        FileInputFormat.setInputPaths(job, new Path("D:\\hadoop\\尚硅谷资料\\资料\\11_input\\inputtable\\order.txt"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\hadoop\\myproject\\outputresult\\joinapplicationmapjoin"));

        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);
    }
}
