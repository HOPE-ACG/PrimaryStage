package com.acg.filtratelog;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * Dividing different text into different output stream
 *
 * @author ACHENGE
 */
public class LogFilterRecordWriter extends RecordWriter<Text, NullWritable> {

    /**
     * The stream outputting text that contains 'atguigu'
     */
    private final FSDataOutputStream atguigu;

    /**
     * The stream outputting text that don't contain 'atguigu'
     */
    private final FSDataOutputStream others;

    /**
     * Creating two output stream via constructor with parameter {@code job}
     *
     * @param job acquiring job's configuration
     * @throws IOException ioexception
     */
    public LogFilterRecordWriter(TaskAttemptContext job) throws IOException {

        try {
            FileSystem fileSystem = FileSystem.get(job.getConfiguration());
            atguigu = fileSystem.create(new Path(
                    "D:\\hadoop\\myproject\\outputresult\\logfilter\\atguigu.log"
            ));
            others = fileSystem.create(new Path(
                    "D:\\hadoop\\myproject\\outputresult\\logfilter\\other.log"
            ));
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException {

        String website = key.toString();
        if (website.contains("atguigu")) {
            atguigu.writeBytes(website + "\n");
        } else {
            others.writeBytes(website + "\n");
        }
    }

    @Override
    public void close(TaskAttemptContext context) {

        IOUtils.closeStreams(atguigu, others);
    }
}
