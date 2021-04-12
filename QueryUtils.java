package com.example.android.quakereport;


import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public class QueryUtils {

    public static final String LOG_CAT = QueryUtils.class.getSimpleName();

    public static ArrayList<Word> fetchDataFromUrl(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = "";
        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch(IOException e){
            //TODO: Exception Handling
        }
        return (extractJsonResponse(jsonResponse));
    }

    /**
     * Crete URL object
     * @param url URL
     * @return Url
     */

    private static URL createUrl(String url){
        URL Url = null;
        try{
            Url = new URL(url);
        }
        catch(MalformedURLException e){
            Log.v(LOG_CAT, "Error with creating URL!", e);
        }
        return Url;
    }

    /**
     * Make Request for the URL
     * @param url URl
     * @return jsonResponse
     * @throws IOException e
     */

    private static String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        String jsonResponse = "";
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        }
        catch(IOException e){
            Log.e(LOG_CAT, "Error!", e);
        }
        finally{
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Read from Stream and return response
     * @param inputStream is
     * @return JsonResponse
     * @throws IOException e
     */

    private static String readFromStream(InputStream inputStream){
        StringBuilder sb = new StringBuilder();
        if(inputStream == null){
            return null;
        }
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = br.readLine();
            while(line != null){
                sb.append(line);
                line = br.readLine();
            }
        }
        catch(IOException e){
            Log.v(LOG_CAT, "Error readFromStream!", e);
        }
        return sb.toString();
    }

    /**
     *
     * @param response response
     * @return earthquake arraylist
     */

    private static ArrayList<Word> extractJsonResponse(String response){
        ArrayList<Word> earthquake = new ArrayList<>();
        try{
            JSONObject root = new JSONObject(response);
            JSONArray features = root.getJSONArray("features");
            for(int i=0;i<features.length();i++){
                JSONObject element = features.getJSONObject(i);
                JSONObject properties = element.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                earthquake.add(new Word(mag, place, time, url));
            }
        }
        catch(JSONException e){
            Log.v(LOG_CAT, "Error!", e);
        }
        return earthquake;
    }

}

















