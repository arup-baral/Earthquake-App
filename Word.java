package com.example.android.quakereport;

public class Word {

    //magnitude of EarthQuake
    private double mMagnitude;

    //place of EarthQuake
    private String mPlace;

    //date of EarthQuake
    private long mMilliSeconds;

    //URL of current EarthQuake
    private String mURL;

    /**
     * Constructor of the class
     * @param mag
     * @param place
     * @param sec
     */
    public Word(double mag, String place, long sec, String url){
        this.mMagnitude = mag;
        this.mPlace = place;
        this.mMilliSeconds = sec;
        this.mURL = url;
    }

    /**
     * method to get magnitude
     */
    public double getMagnitude(){
        return mMagnitude;
    }

    /**
     * method to get place
     */
    public String getPlace(){
        return mPlace;
    }

    /**
     * method to get date
     */
    public long getMilliSeconds(){
        return mMilliSeconds;
    }

    /**
     * method to get URL
     */
    public String getURL(){
        return mURL;
    }
}















