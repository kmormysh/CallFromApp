package com.samples.katy.callfromapp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Katy on 3/5/2015.
 */
public class ContactsListAdapter extends BaseAdapter {

    private List<Contact> contacts;
    private List<Contact> originalContacts;
    private LayoutInflater layoutInflater;
    private HashMap<Contact, Integer> colorHashMap;
    private Random rnd = new Random();

    private Integer[] colors = {Color.parseColor("#4B879C"),
            Color.parseColor("#A17099"),
            Color.parseColor("#688F68"),
            Color.parseColor("#A68458"),
            Color.parseColor("#507CA3")};

    public ContactsListAdapter(Context context, List<Contact> contactList) {
        this.contacts = new ArrayList<Contact>();
        this.contacts.addAll(contactList);
        this.originalContacts = new ArrayList<Contact>();
        this.originalContacts.addAll(contactList);
        colorHashMap = new HashMap<>(contacts.size());
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Contact contact = getContact(position);

        final int contactPosition = position;

        if (view == null)
            view = layoutInflater.inflate(R.layout.layout_contact, parent, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.contact_image);
        TextView text_name = (TextView) view.findViewById(R.id.contact_name);
        TextView text_phone = (TextView) view.findViewById(R.id.contact_phone);
        TextView name_short = (TextView) view.findViewById(R.id.contact_shortName);

        if (contact.getImage_url() != null) {
            imageView.setImageURI(Uri.parse(contact.getImage_url()));
            imageView.setAlpha(1f);
            name_short.setVisibility(View.INVISIBLE);
        } else {
            if (!colorHashMap.containsKey(contact)) {
                colorHashMap.put(contact, colors[rnd.nextInt(colors.length)]);
            }

            imageView.setBackgroundColor(colorHashMap.get(contact));

            imageView.setImageURI(null);
            imageView.setAlpha(0.45f);
            name_short.setVisibility(View.VISIBLE);
            name_short.setText(contact.getName().substring(0, 1));
        }

        text_name.setText(contact.getName());

        if (contact.getPhoneNumber().size() > 0)
            text_phone.setText(contact.getPhoneNumber().get(0));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int x = (int) getItemId(contactPosition);

                android.app.FragmentManager manager = MainActivity.mainActivity.getFragmentManager();

                DetailsPopUp dialog = new DetailsPopUp();

                dialog.setContact(getContact(x));

                dialog.show(manager, "Contact info");

            }
        });

        return view;
    }

    private Contact getContact(int position) {
        return (Contact) getItem(position);
    }

    public void filteredContacts(String query) {
        query = query.toLowerCase();
        contacts.clear();

        if (query.isEmpty()) {
            contacts.addAll(originalContacts);
        } else {
            for (Contact contact : originalContacts) {
                if (contact.getName().toLowerCase().contains(query)
                        || filterPhones(contact.getPhoneNumber(), query)) {
                    contacts.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }

    private boolean filterPhones(List<String> phones, String query) {
        query = query.replace(" ", "");
        for (String phone : phones) {
            phone = phone.replace(" ", "");
            if (phone.contains(query)) {
                return true;
            }
        }
        return false;
    }


}
