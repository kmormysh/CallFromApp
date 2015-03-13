package com.samples.katy.callfromapp;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Katy on 3/5/2015.
 */
public class RecentCallsListFragment extends Fragment{
    private CallDatabaseHandler db;
    private ListView listView;
    List<Calls> recentCalls;
    RecentCallsListAdapter recentCallsListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_callslist, container, false);

        db = new CallDatabaseHandler(getActivity());

        readRecentCalls(db);

        recentCalls = db.getAllCalls();

        recentCallsListAdapter = new RecentCallsListAdapter(getActivity(), recentCalls);
        listView = (ListView) view.findViewById(R.id.all_calls);
        listView.setAdapter(recentCallsListAdapter);

        db.deleteAll();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void readRecentCalls(CallDatabaseHandler callDatabaseHandler) {

        String name, phoneNumber, time;
        int callType;
        Calls call;

        Cursor phones = getActivity().getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");

        while (phones.moveToNext()) {

            name = phones.getString(phones.getColumnIndex(CallLog.Calls.CACHED_NAME));
            phoneNumber = phones.getString(phones.getColumnIndex(CallLog.Calls.NUMBER));
            time = phones.getString(phones.getColumnIndex(CallLog.Calls.DATE));
            callType = phones.getInt(phones.getColumnIndex(CallLog.Calls.TYPE));

            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd-MMM-yyyy HH:mm");
            String dateString = formatter.format(new Date(Long
                    .parseLong(time)));

            call = new Calls(name, phoneNumber, dateString, callType);
            callDatabaseHandler.addCalls(call);

        }
        phones.close();
    }
}
