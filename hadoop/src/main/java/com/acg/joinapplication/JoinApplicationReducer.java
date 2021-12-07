package com.acg.joinapplication;

import com.acg.bean.Order;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class JoinApplicationReducer extends Reducer<Text, Order, Order, NullWritable> {

    /**
     * saving final order objects
     */
    private final List<Order> orders = new ArrayList<>();

    @Override
    protected void reduce(Text key, Iterable<Order> values, Context context) throws IOException, InterruptedException {

        this.orders.clear();
        String name = "";
        for (Order o : values) {
            //can't use order object in iterator, because rear will cover before
            if ("order".equals(o.getSymbol())) {
                Order temp = new Order();
                try {
                    //only all attributes have 'getter' and 'setter' method in entity,
                    //this 'copyProperties' can work
                    BeanUtils.copyProperties(temp, o);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                this.orders.add(temp);
            } else {
                name = o.getName();
            }
        }
        for (Order order : this.orders) {
            order.setName(name);
            context.write(order, NullWritable.get());
        }
    }
}
