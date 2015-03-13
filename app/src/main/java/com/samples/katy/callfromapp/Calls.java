package com.samples.katy.callfromapp;

import java.util.List;

/**
 * Created by Katy on 3/5/2015.
 */
public class Calls {
    private int ID;
    private String name;
    private String phoneNumber;
    private String time;
    private int callType;

    public Calls() { }

    public Calls(String name, String phoneNumber,String time, int callType) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.time = time;
        this.callType = callType;
    }

    public Calls(int keyID, String name, String phoneNumber, String time, int callType){
        this.ID = keyID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.time = time;
        this.callType = callType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getID() {
        return ID;
    }

    public void setID(int keyID) {
        this.ID = keyID;
    }

    @Override
    public int hashCode() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        return (((Calls)o).getID() == this.getID());
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }
}
