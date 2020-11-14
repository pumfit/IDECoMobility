package com.teamide.idecomobility;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.common.server.converter.StringToIntConverter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BusArrivalParsingData2 extends AsyncTask<String, Void, Document> {
    String openUrl = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll";
    Context text;
    String localId;

    public BusArrivalParsingData2(Context context, String localId){
        this.text = context;
        this.localId = localId;
    }
    @Override
    protected Document doInBackground(String... strings) {
        URL url;
        Document doc = null;
        try{
            url = new URL(openUrl+"?ServiceKey=idAKQNTIDrnSK5vmheOsFszfGqNfoydTlN08JVMaLchmHaKDSY0lWkjMtjiSfDGSa%2FVm7mVWhVX7WXEfF7OGgA%3D%3D"+"&busRouteId="+localId);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
        } catch (MalformedURLException | ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }
    @Override
    protected void onPostExecute(Document doc){
        super.onPostExecute(doc);
        NodeList itemList = doc.getElementsByTagName("itemList");

        for(int i=0; i<itemList.getLength(); i++){
            Node node = itemList.item(i);
            Element element = (Element) node;

            NodeList busLowList = element.getElementsByTagName("busType1");
            String busLow = busLowList.item(0).getChildNodes().item(0).getNodeValue();
            NodeList busNameList = element.getElementsByTagName("rtNm");
            String busName = busNameList.item(0).getChildNodes().item(0).getNodeValue();
            NodeList busTimeList = element.getElementsByTagName("exps1");
            String busTime = busTimeList.item(0).getChildNodes().item(0).getNodeValue();
            Integer busMin = Integer.valueOf(busTime)/60;
            String busTimeMin = String.valueOf(busMin);
            Log.d("ad","버스번호:"+busName+"버스도착정보"+busTimeMin);
        }
    }
    @Override
    protected  void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }
}
