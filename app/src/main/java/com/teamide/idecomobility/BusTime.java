package com.teamide.idecomobility;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BusTime extends AsyncTask<String[], Void, String[]>{
    private String url;
    private XmlPullParserFactory xmlFactoryObject;
    private ProgressDialog pDialog;


    public BusTime(String url){
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
                        if (name.equals("stNm")) {
                            result[3] = text;
                            Log.d("ad","2st: "+result[3]);
                        } else if (name.equals("busRouteId")) { //get humidity
                            result[0] = myParser.getAttributeValue(null, "value1");
                            Log.d("ad","3st: "+result[0]);

                        } else if (name.equals("rtNm")) { //get pressure
                            result[1] = myParser.getAttributeValue(null, "value2");
                            Log.d("ad","4st: "+result[1]);

                        } else if (name.equals("exps1")) { //get temperature
                            result[2] = myParser.getAttributeValue(null, "value3");
                            Log.d("ad","5st: "+result[2]);
                        }
                        break;
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

