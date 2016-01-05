package com.hack.xapp.model;

/**
 * Created by tejom_000 on 1/6/2016.
 */
public class BookedItem {

    public long id;
    public Maid bookedMaid;
    public Location loc;
    public TimeInterval mTimeInterval;
    public int salary;
    public String startMonth;
    public String endMonth;
    public int numberOfDays;

    public BookedItem(Maid bookedMaid, Location loc, TimeInterval mTimeInterval, int salary, String startMonth, int numberOfDays) {
        this.bookedMaid = bookedMaid;
        this.loc = loc;
        this.mTimeInterval = mTimeInterval;
        this.salary = salary;
        this.startMonth = startMonth;
        this.numberOfDays = numberOfDays;

    }

    public void setId(long id) {
        this.id = id;
    }


}
