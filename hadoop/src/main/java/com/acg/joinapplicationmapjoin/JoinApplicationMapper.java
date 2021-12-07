package com.acg.joinapplicationmapjoin;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class JoinApplicationMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    /**
     * saving contents of file of 'pd.txt', finally combining with file of 'order.txt'.
     */
    private final Map<String, String> products = new HashMap<>();

    /**
     * finally outputting data
     */
    private final Text text = new Text();

    @Override
    protected void setup(Context context) throws IOException {

        URI[] cacheFiles = context.getCacheFiles();
        FileSystem fileSystem = FileSystem.get(context.getConfiguration());
        FSDataInputStream fsDataInputStream = fileSystem.open(new Path(cacheFiles[0]));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fsDataInputStream));
        String aline;
        while (StringUtils.isNotEmpty(aline = bufferedReader.readLine())) {
            String[] pds = aline.split("\t");
            products.put(pds[0], pds[1]);
        }
        IOUtils.closeStreams(fileSystem, fsDataInputStream, bufferedReader);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] ors = value.toString().split("\t");
        String name = products.get(ors[1]);
        text.set(ors[0] + "\t" + name + "\t" + ors[2]);
        context.write(text, NullWritable.get());
    }
}
