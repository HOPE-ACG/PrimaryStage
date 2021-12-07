package com.acg.flowcounterwritable;

import com.acg.bean.PhoneWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * According to the attribute of phone number of entity {@code phone}, distribute
 * different partition for final output file
 *
 * @author ACHENGE
 */
public class FlowCounterPartition extends Partitioner<PhoneWritable, Text> {

    @Override
    public int getPartition(PhoneWritable phoneWritable, Text text, int numPartitions) {

        String phonePrefix = text.toString().substring(0, 3);

        int partitions = 0;

        switch (phonePrefix) {
            case "135":
                partitions = 1;
                break;
            case "137":
                partitions = 2;
                break;
            case "139":
                partitions = 3;
                break;
        }

        return partitions;
    }
}
