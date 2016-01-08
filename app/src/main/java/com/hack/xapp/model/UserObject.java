package com.hack.xapp.model;

/**
 * Created by tejom_000 on 1/5/2016.
 */
public class UserObject {

    public long id;
    public String name;
    public String email;
    public String mob;
    public boolean isVerified = false;

    public UserObject(String name, String mob, String email) {
        this.name = name;
        this.mob = mob;
        this.email = email;
    }

    public UserObject(long id, String name, String mob, String email) {
        this.id = id;
        this.name = name;
        this.mob = mob;
        this.email = email;
    }

    public void setMail(String mail) {
        email = mail;
    }

    public void setVerified(boolean res) {
        isVerified = res;
    }

}
