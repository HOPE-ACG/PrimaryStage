package com.acg.bean;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Self-defined bean object that implements serializable
 *
 * @author ACHENGE
 */
public class Phone implements Writable, Comparable<Phone> {

    /**
     * Up flow of this phone
     */
    private Long upFlow;

    public void setUpFlow(Long upFlow) {
        this.upFlow = upFlow;
    }

    public Long getUpFlow() {
        return upFlow;
    }

    /**
     * Down flow of this phone
     */
    private Long downFlow;

    public void setDownFlow(Long downFlow) {
        this.downFlow = downFlow;
    }

    public Long getDownFlow() {
        return downFlow;
    }

    /**
     * Total flow of this phone
     */
    private Long totalFlow;

    public void setTotalFlow() {
        this.totalFlow = upFlow + downFlow;
    }

    /**
     * Need call this constructor without parameter when deserialize
     */
    public Phone() {
        super();
    }

    public void write(DataOutput dataOutput) throws IOException {

        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(downFlow);
        dataOutput.writeLong(totalFlow);
    }

    public void readFields(DataInput dataInput) throws IOException {

        upFlow = dataInput.readLong();
        downFlow = dataInput.readLong();
        totalFlow = dataInput.readLong();
    }

    /**
     * If transmit in KEY, must implement {@code Comparable} interface.
     * Output data is ordered by ascent.
     *
     * @param phone the same class of being compared
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Phone phone) {
        return (int) (this.totalFlow - phone.totalFlow);
    }

    /**
     * If need to show results in file, must override this method
     *
     * @return outputting format
     */
    @Override
    public String toString() {
        return upFlow + "\t" + downFlow + "\t" + totalFlow;
    }
}
