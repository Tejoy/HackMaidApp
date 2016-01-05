package com.hack.xapp.model;

/**
 * Created by tejom_000 on 1/5/2016.
 */
public class Location {

    int radius;
    String name;
    String pincode;
    Coordinate center;

    public Location(int r, int x, int y) {
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
