package com.example.android.quakereport;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class WordAdapter extends ArrayAdapter<Word> {

    private static final String STRING_SEPARATOR = "of ";

    public WordAdapter(Context context, ArrayList<Word> list){
        super(context, 0, list);
    }

    private String formatDate(Date dateObj){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObj);
    }

    private String formatTime(Date dateObj){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObj);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word word = getItem(position);

        // Set Magnitude TextView
        TextView mag = (TextView) listItemView.findViewById(R.id.magnitude_text_view);
        mag.setText(String.valueOf(word.getMagnitude()));

        // Set Place TextView
        TextView distance = (TextView) listItemView.findViewById(R.id.distance_text_view);
        TextView place = (TextView) listItemView.findViewById(R.id.place_text_view);
        String placeStr = word.getPlace();
        if(placeStr.contains(STRING_SEPARATOR)){
            String s[] = placeStr.split(STRING_SEPARATOR);
            distance.setText(s[0] + STRING_SEPARATOR);
            place.setText(s[1]);
        }
        else{
            distance.setText("Near the");
            place.setText(placeStr);
        }

        // Set Date TextView
        /**
         * Declare dateObject
         */
        Date dateObject = new Date(word.getMilliSeconds());
        TextView date = (TextView) listItemView.findViewById(R.id.date_text_view);
        date.setText(formatDate(dateObject));

        //Set Time TextView
        TextView time = (TextView) listItemView.findViewById(R.id.time_text_view);
        time.setText(formatTime(dateObject));

        return listItemView;
    }
}























