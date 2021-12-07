package com.acg.joinapplication;

import com.acg.bean.Order;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class JoinApplicationMapper extends Mapper<LongWritable, Text, Text, Order> {

    /**
     * current name of input's file
     */
    private String fileName;

    /**
     * current order object
     */
    private final Order order = new Order();

    /**
     * accepting product's id as value and key that used to transmit
     */
    private final Text text = new Text();

    @Override
    protected void setup(Context context) {

        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        this.fileName = fileSplit.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] values = value.toString().split("\t");
        if ("order.txt".equals(fileName)) {
            this.order.setId(values[0]);
            this.order.setCount(Integer.parseInt(values[2]));
            this.order.setSymbol("order");
            this.order.setName("");
            this.text.set(values[1]);
        } else {
            this.order.setName(values[1]);
            this.order.setSymbol("product");
            this.order.setId("");
            this.order.setCount(-1);
            this.text.set(values[0]);
        }
        context.write(this.text, this.order);
    }
}
