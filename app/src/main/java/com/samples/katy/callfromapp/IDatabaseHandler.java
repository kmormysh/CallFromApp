package com.samples.katy.callfromapp;

import java.util.List;

/**
 * Created by Katy on 3/5/2015.
 */
public interface IDatabaseHandler {
    public void addContact(Contact contact);
    public Contact getContact(int id);
    public List<Contact> getAllContacts();
    public int getContactsCount();
    public int updateContact(Contact contact);
    public void deleteContact(Contact contact);
    public void deleteAll();
}
