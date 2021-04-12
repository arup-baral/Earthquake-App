package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Word>> {

    private final String mUrl;

    public EarthquakeLoader(Context context, String url){
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public ArrayList<Word> loadInBackground(){
        if(mUrl == null){
            return null;
        }
        return QueryUtils.fetchDataFromUrl(mUrl);
    }
}
