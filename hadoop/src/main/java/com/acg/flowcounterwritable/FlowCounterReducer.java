package com.acg.flowcounterwritable;

import com.acg.bean.PhoneWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowCounterReducer extends Reducer<PhoneWritable, Text, Text, PhoneWritable> {

    @Override
    protected void reduce(PhoneWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        for (Text text : values) {
            context.write(text, key);
        }
    }
}
