package com.hack.xapp.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Maid implements Parcelable {


    public long id;
    public String name; //compulsary
    public String gender; //compulsary
    public String phone; //compulsary
    public long salaryFrom; //compulsary
    public long salaryTo; //compulsary
    public List<TimeInterval> times; //compulsary
    public List<String> services; //compulsary
    public boolean isAvailable;
    public Location loc; // compulsary


    public boolean isVerified;
    public Bitmap photo;
    public String idNum;
    public String idType;
    public boolean isPartTime;

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public void setIsPartTime(boolean isPartTime) {
        this.isPartTime = isPartTime;
    }

    public Maid(long id, String name, String gender, String phone, long salFrom, long salTo) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.salaryFrom = salFrom;
        this.salaryTo = salTo;
        times = new ArrayList<TimeInterval>();
        services = new ArrayList<String>();
        isPartTime = false;
    }

    public void addService(String svc) {
        services.add(svc);
    }

    public void addTimeInterval(String frm, String to) {
        times.add(new TimeInterval(frm, to));
    }

    public Maid(String name, String gender, String phone, long salFrom, long salTo) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.salaryFrom = salFrom;
        salaryTo = salTo;
        times = new ArrayList<TimeInterval>();
        services = new ArrayList<String>();
        isPartTime = false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(phone);
        dest.writeLong(salaryFrom);
        dest.writeLong(salaryTo);
        dest.writeInt(isPartTime ? 1 : 0);
        dest.writeDouble(loc.radius);
        dest.writeString(loc.name);
        dest.writeDouble(loc.center.x);
        dest.writeDouble(loc.center.x);
        int timesSize = times.size();
        dest.writeInt(timesSize);

        for (int i = 0; i < timesSize; ++i) {
            dest.writeString(times.get(i).timeFrom);
            dest.writeString(times.get(i).timeTo);
        }
        int servicesSize = services.size();
        dest.writeInt(servicesSize);
        for (int i = 0; i < servicesSize; ++i) {
            dest.writeString(services.get(i));
        }
        //TODO: write bitmap
        dest.writeParcelable(photo, 0);

    }

    public static final Parcelable.Creator<Maid> CREATOR = new Parcelable.Creator<Maid>() {
        @Override
        public Maid createFromParcel(Parcel source) {
            return new Maid(source);
        }

        @Override
        public Maid[] newArray(int size) {
            return new Maid[0];
        }
    };

    private Maid(Parcel in) {
        id = in.readLong();
        name = in.readString();
        gender = in.readString();
        phone = in.readString();
        salaryFrom = in.readLong();
        salaryTo = in.readLong();

        int tmp = in.readInt();

        isPartTime = (tmp == 0 ? false : true);
        double r = in.readDouble();
        String n = in.readString();
        double x = in.readDouble();
        double y = in.readDouble();
        loc = new Location(r, x, y);
        loc.setName(n);

        int timesSize = in.readInt();
        times = new ArrayList<TimeInterval>();

        for (int i = 0; i < timesSize; ++i) {
            times.add(new TimeInterval(in.readString(), in.readString()));
        }

        int servicesSize = in.readInt();
        services = new ArrayList<String>();

        for (int i = 0; i < servicesSize; ++i) {
            services.add(in.readString());
        }

        //TODO: read bitmap
        photo = in.readParcelable(null);
    }
}