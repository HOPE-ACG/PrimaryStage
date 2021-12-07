package com.acg.wordcountcombiner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        //获取job对象
        Configuration con = new Configuration();
        Job job = Job.getInstance(con, "word count");

        //设置jar包路径
        job.setJarByClass(WordCountDriver.class);

        //关联mapper和reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //关联Combiner, 在Reducer之前聚合相同的word, 自定义的Combiner与WordCountReducer是一样的
        job.setCombinerClass(WordCountReducer.class);

        //设置mapper的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //设置最终输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(WordCountCombiner.class);

        //设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //提交job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
