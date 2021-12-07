package com.acg.flowcounter;

import com.acg.bean.Phone;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowCounterMapper extends Mapper<LongWritable, Text, Text, Phone> {

    private final Phone phone = new Phone();
    private final Text text = new Text();

    @Override
    protected void map(LongWritable key, Text text, Context context) throws IOException, InterruptedException {

        String aLine = text.toString();
        String[] contents = aLine.split("\t");
        int length = contents.length;
        Long upflow = Long.parseLong(contents[length - 3]);
        Long downflow = Long.parseLong(contents[length - 2]);
        this.phone.setUpFlow(upflow);
        this.phone.setDownFlow(downflow);
        this.phone.setTotalFlow();
        String phoneNum = contents[1];
        this.text.set(phoneNum);
        context.write(this.text, phone);
    }
}
