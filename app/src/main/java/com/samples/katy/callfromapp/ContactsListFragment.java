package com.samples.katy.callfromapp;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Katy on 3/5/2015.
 */
public class ContactsListFragment extends Fragment{
    private ContactDatabaseHandler db;
    private ListView listView;
    ContactsListAdapter contactsListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactslist, container, false);

        db = new ContactDatabaseHandler(getActivity());

        if (!db.recordsExist())
            readContactsFromPhone(db);
        List<Contact> contacts = db.getAllContacts();
        contactsListAdapter = new ContactsListAdapter(getActivity(), contacts);
        listView = (ListView) view.findViewById(R.id.all_contacts);
        listView.setAdapter(contactsListAdapter);

        return view;
//        db.deleteAll();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void readContactsFromPhone(ContactDatabaseHandler databaseHandler) {

        List<String> phoneNumbers = new ArrayList<>();
        String name, phoneNumber, image_url;
        Contact contact = new Contact();

        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");

        while (phones.moveToNext()) {

            name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneNumbers.add(phoneNumber);

            if (name.equals(contact.getName()))
            {
                databaseHandler.addPhones(phoneNumber, name);
                phoneNumbers.clear();
                continue;
            }

            image_url = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            contact = new Contact(name, phoneNumbers, image_url);
            databaseHandler.addContact(contact);
            databaseHandler.addPhones(phoneNumber, name);

            phoneNumbers.clear();
        }
        phones.close();
    }

//   private void readContactsFromSIM(ContactDatabaseHandler databaseHandler) {
//        try {
//            List<String> phoneNumbers = new ArrayList<>();
//            String simName = null;
//            String simNumber = null;
//
//            Uri simUri = Uri.parse("content://icc/adn");
//            Cursor cursorSim = this.getContentResolver().query(simUri, null, null, null, null);
//
//            while (cursorSim.moveToNext()) {
//                phoneNumbers.clear();
//                simName = cursorSim.getString(cursorSim.getColumnIndex("name"));
//                simNumber = cursorSim.getString(cursorSim.getColumnIndex("number"));
//                simNumber.replaceAll("\\D", "");
//                simNumber.replaceAll("&", "");
//                simName = simName.replace("|", "");
//
//                phoneNumbers.add(simNumber);
//
//                databaseHandler.addContact(new Contact(simName, phoneNumbers, "")); //.getBytes(BitmapFactory.decodeStream(input))));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void filterContacts(String query){
        contactsListAdapter.filteredContacts(query);
    }
}
