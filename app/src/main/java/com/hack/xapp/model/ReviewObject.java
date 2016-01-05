package com.hack.xapp.model;

/**
 * Created by tejom_000 on 1/5/2016.
 */
public class ReviewObject {

    public long id;
    public String reviewer;
    public String comment;
    public int rating;

    public ReviewObject(String rwr, String com, String r) {
        reviewer = rwr;
        comment = com;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
