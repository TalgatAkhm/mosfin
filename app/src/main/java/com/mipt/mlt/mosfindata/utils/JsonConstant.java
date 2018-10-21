package com.mipt.mlt.mosfindata.utils;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.mipt.mlt.mosfindata.model.PointData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonConstant {

    static final String[] FILE_NAMES = {"shoes", "clocks", "cosmetics", "cleaning", "photo"};
    static final String[] TRANSPORT_FILE_NAMES = {"calc_metro", "calc_tpu"};
    static final String[] ALL_METRICS_FILE = {"intensity_cloks"};

    static public List<List<LatLng>> jsonData = new ArrayList<>();
    static public List<List<LatLng>> jsonDataTransport = new ArrayList<>();
    static public List<List<PointData>> allMetricsData = new ArrayList<>();

    public static void fillFromAssets(Context context) {
        for (String category : FILE_NAMES) {
            List<LatLng> currentPoints = new ArrayList<>();
            String json = readJSONFromAsset(context, category+".json");
            try {

//                JSONArray points = new JSONObject(json).getJSONArray("dotes");
                JSONArray geoDatas = new JSONArray(json);
                for (int i = 0; i < geoDatas.length(); i++) {
                    JSONObject objectJson = geoDatas.getJSONObject(i).getJSONObject("geoData");
                    double lat = (double) objectJson.getJSONArray("coordinates").get(1);
                    double lon = (double) objectJson.getJSONArray("coordinates").get(0);
                    currentPoints.add(new LatLng(lat, lon));
                }
            } catch (JSONException e) {
                Log.e("JsonConstant", "Failed to load json object");
                e.printStackTrace();
            }
            jsonData.add(currentPoints);
        }

        for (String transport : TRANSPORT_FILE_NAMES) {
            List<LatLng> currentPoints = new ArrayList<>();
            String json = readJSONFromAsset(context, transport+".json");
            try {

                JSONArray geoDatas = new JSONArray(json);
                for (int i = 0; i < geoDatas.length(); i++) {
                    JSONObject objectJson = geoDatas.getJSONObject(i).getJSONObject("geoData");
                    double lat = (double) objectJson.getJSONArray("coordinates").get(1);
                    double lon = (double) objectJson.getJSONArray("coordinates").get(0);
                    currentPoints.add(new LatLng(lat, lon));
                }
            } catch (JSONException e) {
                Log.e("JsonConstant", "Failed to load json object");
                e.printStackTrace();
            }
            jsonDataTransport.add(currentPoints);
        }

        for (String category : ALL_METRICS_FILE) {
            List<PointData> currentPoints = new ArrayList<>();
            String json = readJSONFromAsset(context, category+".json");
            try {

                JSONArray geoDatas = new JSONArray(json);
                for (int i = 0; i < geoDatas.length(); i++) {
                    JSONObject objectJson = geoDatas.getJSONObject(i);
                    double lat = (double) objectJson.getJSONArray("coordinates").get(0);
                    double lon = (double) objectJson.getJSONArray("coordinates").get(1);
                    int intensity = (int) objectJson.get("intensity");
                    currentPoints.add(new PointData(new LatLng(lat, lon), intensity));
                }
            } catch (JSONException e) {
                Log.e("JsonConstant", "Failed to load json object");
                e.printStackTrace();
            }
            allMetricsData.add(currentPoints);
        }
    }

    private static String readJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
