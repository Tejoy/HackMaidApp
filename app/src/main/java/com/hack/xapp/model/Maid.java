package com.hack.xapp.model;

import java.util.ArrayList;
import java.util.List;

public class Maid {


    public long id;
    public String name;
    public String phone;
    public int salaryFrom;
    public int salaryTo;
    List<TimeInterval> times;
    List<String> services;


    public Maid(long id, String name, String phone, int salFrom, int salTo) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.salaryFrom = salFrom;
        salaryTo = salTo;
        times = new ArrayList<TimeInterval>();
        services = new ArrayList<String>();
    }

    public void addService(String svc) {
        services.add(svc);
    }

    public void addTimeInterval(String frm, String to) {
        times.add(new TimeInterval(frm, to));
    }

    public Maid(String name, String phone, int salFrom, int salTo) {
        this.name = name;
        this.phone = phone;
        this.salaryFrom = salFrom;
        salaryTo = salTo;
        times = new ArrayList<TimeInterval>();
        services = new ArrayList<String>();
    }

}