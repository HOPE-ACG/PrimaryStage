package com.acg.flowcounter;

import com.acg.bean.Phone;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowCounterReducer extends Reducer<Text, Phone, Text, Phone> {

    private final Phone phone = new Phone();

    @Override
    protected void reduce(Text key, Iterable<Phone> values, Context context) throws IOException, InterruptedException {

        Long totalUpflow = 0L;
        Long totalDownflow = 0L;
        for (Phone p : values) {
            totalUpflow += p.getUpFlow();
            totalDownflow += p.getDownFlow();
        }
        this.phone.setUpFlow(totalUpflow);
        this.phone.setDownFlow(totalDownflow);
        this.phone.setTotalFlow();
        context.write(key, this.phone);
    }
}
