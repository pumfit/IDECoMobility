package com.teamide.idecomobility;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubwayTourParsingData extends AsyncTask<String[], Void, String[]> {

    private String url;
    private String stName;
    private XmlPullParserFactory xmlFactoryObject;
    private ProgressDialog pDialog;


    public SubwayTourParsingData(String url, String stName) {
        this.url = url;
        this.stName = stName;
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

            xmlFactoryObject = XmlPullParserFactory.newInstance();
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
        int event;
        String text = null;
        String[] result = new String[4];

        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("STATION")) {
                            result[3] = text;
                            if (text == stName) {
                                Log.d("ad", "STATION22: " + result[3]);
                                if (name.equals("LINE")) { //get humidity
                                    result[0] = text;
                                    Log.d("ad", "LINE: " + result[0]);

                                } else if (name.equals("STATION")) { //get pressure
                                    result[1] = text;
                                    Log.d("ad", "STATION: " + result[1]);

                                } else if (name.equals("LINE_NAME")) { //get temperature
                                    result[2] = text;
                                    Log.d("ad", "LINE_NAME: " + result[2]);
                                }
                                break;
                            }
                        }

                }
                event = myParser.next();
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
