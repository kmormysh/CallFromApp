package com.samples.katy.callfromapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Katy on 3/5/2015.
 */
public class RecentCallsListAdapter extends BaseAdapter {

    private List<Calls> calls;
    private List<Calls> originalCalls;
    private LayoutInflater layoutInflater;

    public RecentCallsListAdapter(Context context, List<Calls> callsList) {
        this.calls = new ArrayList<>();
        this.calls.addAll(callsList);
        this.originalCalls = new ArrayList<>();
        this.originalCalls.addAll(callsList);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return calls.size();
    }

    @Override
    public Object getItem(int position) {
        return calls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Calls call = getCall(position);

        if (view == null)
            view = layoutInflater.inflate(R.layout.layout_recentcontact, parent, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.recent_image);
        TextView text_name = (TextView) view.findViewById(R.id.recent_name);
        TextView text_phone = (TextView) view.findViewById(R.id.recent_phone);
        TextView recent_time = (TextView) view.findViewById(R.id.recent_time);

        switch (call.getCallType()) {
            case 1:
                imageView.setImageResource(android.R.drawable.sym_call_incoming);
                break;
            case 2:
                imageView.setImageResource(android.R.drawable.sym_call_outgoing);
                break;
            case 3:
                imageView.setImageResource(android.R.drawable.sym_call_missed);
                break;
        }

//        imageView.setAlpha(0.45f);

        if (call.getName() == null) {
            text_name.setText(call.getPhoneNumber());
        } else {
            text_name.setText(call.getName());
        }

        text_phone.setText(call.getPhoneNumber());

        recent_time.setText(call.getTime());

        return view;
    }

    private Calls getCall(int position) {
        return (Calls) getItem(position);
    }

}
