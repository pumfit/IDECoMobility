package com.teamide.idecomobility;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubwayAPI extends AsyncTask<String[], Void, String[]> {
    String callNmUrl = "http://openapi.seoul.go.kr:8088/47416a4e76696e3538386865597a45/xml/StationAdresTelno/1/5/";
    String liftUsableUrl = "http://openapi.seoul.go.kr:8088/6c456a565a696e353130356e6c577374/xml/SeoulMetroFaciInfo/1/5/";
    String facilityInfoUrl = "http://openapi.seoul.go.kr:8088/5242784d64696e353132307849595a70/xml/subwayTourInfo/1/5/";
    String SeniorfacilityInfoUrl = "http://openapi.seoul.go.kr:8088/4c51584672696e353832514f654470/xml/OdblrDspsnCvntl/1/5/";

    private String url;
    private XmlPullParserFactory xmlFactoryObject;

    public SubwayAPI(String url){
        this.url = url;
    }

    @Override
    protected String[] doInBackground(String[]... params) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            InputStream stream = connection.getInputStream();

            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();

            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myParser.setInput(stream, null);
            String[] result = parseXML(myParser);
            stream.close();

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AsyncTask", "exception");
            return null;
        }
    }

    private String[] parseXML(XmlPullParser myParser) {
        return null;
    }
}
