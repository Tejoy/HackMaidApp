package com.hack.xapp.model;

/**
 * Created by tejom_000 on 1/5/2016.
 */
public class Location {

    public double radius;
    public String name;
    public String pincode;
    public Coordinate center;

    public Location(double r, double x, double y) {
        center = new Coordinate(x, y);
        radius = r;
    }


    public void setPincode(String pin) {
        pincode = pin;
    }

    public void setName(String n) {
        name = n;
    }

}
