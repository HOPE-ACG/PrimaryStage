package com.acg.bean;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Used to implememt 'Join' application
 *
 * @author ACHENGE
 */
public class Order implements WritableComparable<Order> {

    /**
     * order's number
     */
    private String id;

    /**
     * product's name
     */
    private String name;

    /**
     * purchase count
     */
    private int count;

    /**
     * table symbol
     */
    private String symbol;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Order() {
        super();
    }

    @Override
    public void write(DataOutput out) throws IOException {

        out.writeUTF(this.id);
        out.writeUTF(this.name);
        out.writeInt(this.count);
        out.writeUTF(this.symbol);
    }

    @Override
    public void readFields(DataInput in) throws IOException {

        this.id = in.readUTF();
        this.name = in.readUTF();
        this.count = in.readInt();
        this.symbol = in.readUTF();
    }

    @Override
    public String toString() {

        return this.id + "\t" + this.name + "\t" + this.count;
    }

    @Override
    public int compareTo(Order o) {

        return (Integer.parseInt(this.id) - Integer.parseInt(o.id));
    }
}
