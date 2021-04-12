/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Word>> {

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
    private WordAdapter adapter;

    private static final int EARTHQUAKE_LOADER_ID = 1;

    public static final String Log_Cat = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
         ListView listView = (ListView) findViewById(R.id.list);
         adapter = new WordAdapter(this, new ArrayList<Word>());
         listView.setAdapter(adapter);

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Word word = adapter.getItem(i);
                 Uri uri = Uri.parse(word.getURL());
                 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                 startActivity(intent);
             }
         });

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        if(netInfo!= null && netInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this).forceLoad();
        }
        else{
            Log.v(Log_Cat, "Error connection");
            View progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            TextView noList = (TextView) findViewById(R.id.no_result_text_view);
            noList.setText("No network connection");
        }
    }

    @Override
    public Loader<ArrayList<Word>> onCreateLoader(int i, Bundle bundle){
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Word>> loader, ArrayList<Word> words) {
        View progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        adapter.clear();
        if(words!= null && !words.isEmpty()){
            adapter.addAll(words);
        }
        else{
            TextView noList = (TextView) findViewById(R.id.no_result_text_view);
            noList.setText("No result found");
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Word>> loader) {
        adapter.clear();
    }
}
















