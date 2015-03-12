package com.samples.katy.callfromapp;

import java.util.List;

/**
 * Created by Katy on 3/5/2015.
 */
public class Contact {
    private int ID;
    private String name;
    private List<String> phoneNumber;
    private String image_url;

    public Contact() { }

    public Contact(String name, List<String> phoneNumber, String image_url) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image_url = image_url;
    }

    public Contact(int keyID, String name, List<String> phoneNumber, String image_url){
        this.ID = keyID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getID() {
        return ID;
    }

    public void setID(int keyID) {
        this.ID = keyID;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public int hashCode() {
        return ID;
    }
}
