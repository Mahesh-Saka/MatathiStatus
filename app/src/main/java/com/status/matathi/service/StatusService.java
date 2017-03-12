package com.status.matathi.service;

import android.content.Context;

import com.status.matathi.handler.DataBaseManager;

import java.util.ArrayList;

/**
 * Created by Mahesh on 2/17/2017.
 */

public class StatusService {
    Context context;

    public StatusService(Context context) {
        this.context = context;
    }

    public ArrayList<String> getAllStatusByCatagories(int catId) {
        return DataBaseManager.getDatabase(context).getMarathiSmsDBHandler().getAllStatusByCatagories(catId);
    }

}
