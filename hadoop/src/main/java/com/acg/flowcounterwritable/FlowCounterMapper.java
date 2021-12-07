package com.acg.flowcounterwritable;

import com.acg.bean.PhoneWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowCounterMapper extends Mapper<LongWritable, Text, PhoneWritable, Text> {

    private final PhoneWritable phoneWritable = new PhoneWritable();
    private final Text text = new Text();

    @Override
    protected void map(LongWritable key, Text text, Context context) throws IOException, InterruptedException {

        String aLine = text.toString();
        String[] contents = aLine.split("\t");
        int length = contents.length;
        Long upflow = Long.parseLong(contents[length - 3]);
        Long downflow = Long.parseLong(contents[length - 2]);
        Long totalflow = Long.parseLong(contents[length - 1]);
        this.phoneWritable.setUpFlow(upflow);
        this.phoneWritable.setDownFlow(downflow);
        this.phoneWritable.setTotalFlow(totalflow);
        String phoneNum = contents[0];
        this.text.set(phoneNum);
        context.write(this.phoneWritable, this.text);
    }
}
