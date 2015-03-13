package com.samples.katy.callfromapp;

import java.util.List;

/**
 * Created by Katy on 3/5/2015.
 */
public interface ICallsDatabaseHandler {
    public void addCalls(Calls call);
    public Calls getCalls(int id);
    public List<Calls> getAllCalls();
    public int getCallsCount();
    public int updateCalls(Calls call);
    public void deleteCalls(Calls call);
    public void deleteAll();
}
