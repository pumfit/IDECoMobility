package com.teamide.idecomobility;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BusTime extends AsyncTask<String, Void, ArrayList>{
    private XmlPullParserFactory xmlFactoryObject;
    private ProgressDialog pDialog;

    String busName, busTime,busType = null;
    ArrayList<BusInfoData> resultList = new ArrayList<>();

    @Override
    protected ArrayList doInBackground(String... params) {
        try {
            String urltext = (String) params[0];
            URL url = new URL(urltext);
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
            ArrayList resultList = parseXML(myParser);
            stream.close();

            return resultList;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AsyncTask", "exception");
            return null;
        }
    }

    private ArrayList parseXML(XmlPullParser myParser) {
        int event;
        String text = null;
        boolean inBusType = false;
        boolean inBusNm = false;
        boolean inBusName = false;

        try {
            event = myParser.getEventType();
            int tagIdentifier = 0;

            while (event != XmlPullParser.END_DOCUMENT) {

                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if(myParser.getName().equals("busType1"))
                            inBusType = true;
                        if (myParser.getName().equals("exps1"))
                            inBusNm = true;
                        if (myParser.getName().equals("rtNm"))
                            inBusName = true;

                        break;



                    case XmlPullParser.TEXT:
                        if(inBusType){
                            busType = myParser.getText();
                            inBusType = false;
                            Log.d("ad","버스타입1="+busType);
                        }
                        if (inBusNm){
                            busTime = myParser.getText();
                            inBusNm = false;
                            Log.d("ad","버스타임1="+busTime);
                        }
                        if (inBusName){
                            //myParser.next();
                            busName = myParser.getText();
                            inBusName = false;
                            Log.d("ad","버스이름1="+busName);
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        if(myParser.getName().equals("itemList")){
                            if (busName!=null && busTime!=null && busType!=null){
                                Log.d("ad","이름"+busName+"시간"+busTime+"타입"+busType+"!!!!!!");
                                resultList.add(new BusInfoData(busName,busTime,busType));
                            }
                        }
                        break;
                }

                event = myParser.next();
            }
            return resultList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

