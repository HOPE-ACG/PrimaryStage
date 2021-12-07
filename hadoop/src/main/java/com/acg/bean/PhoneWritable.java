package com.acg.bean;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Self-defined bean object that implements serializable
 *
 * @author ACHENGE
 */
public class PhoneWritable implements WritableComparable<PhoneWritable> {

    /**
     * Up flow of this phone
     */
    private Long upFlow;

    public void setUpFlow(Long upFlow) {
        this.upFlow = upFlow;
    }

    /**
     * Down flow of this phone
     */
    private Long downFlow;

    public void setDownFlow(Long downFlow) {
        this.downFlow = downFlow;
    }

    /**
     * Total flow of this phone
     */
    private Long totalFlow;

    public void setTotalFlow(Long totalFlow) {
        this.totalFlow = totalFlow;
    }

    /**
     * Need call this constructor without parameter when deserialize
     */
    public PhoneWritable() {
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
     * Fist sort according to total flow. If total flow is equal, change to up flow.
     * Output data is ordered by descent.
     *
     * @param phoneWritable the same class of being compared
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    public int compareTo(PhoneWritable phoneWritable) {

        long totaFlowDiff = phoneWritable.totalFlow - this.totalFlow;

        if ((totaFlowDiff) != 0) {
            return (int) totaFlowDiff;
        } else {
            return (int) (phoneWritable.upFlow - this.upFlow);
        }
    }

    /**
     * If need to show results in file, must override this method
     *
     * @return outputting format
     */
    @Override
    public String toString() {
        return "Uplink Traffic ->" + upFlow +
                "\tDownlink Traffic ->" + downFlow +
                "\tTotal Traffic ->" + totalFlow;
    }
}
