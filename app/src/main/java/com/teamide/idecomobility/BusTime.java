package com.teamide.idecomobility;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class BusTime extends Activity {
    Element element;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_direction);

        TextView busst = findViewById(R.id.title);
        TextView bustm = findViewById(R.id.num);

        String busstop = getTagValue("stNm",element); // 버스정류장 이름 가져오기
        String time = getTagValue("exps1",element); // 남은시간 블러오기 (초)
        Integer i = Integer.parseInt(time)/60;
        String busTm = Integer.toString(i); // 남은시간 블러오기 (분) //정수 추가 부분 끝
    }

    public void busXml(){
        try {
            String url = "http://ws.bus.go.kr/api/rest/arrive/getLowArrInfoByStId?"
                    + "ServiceKey=ESol2fpbzP%2F%2BPJfShFAU%2FaMbhqzepxntyCIskM2bqyY0dWWU4Sd1w0VH0JpBnWCdBUd79%2BLI34mOXFs0UjrMJA%3D%3D"
                    + "&stId=112000001";
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("itemList");
            Node nNode = nList.item(0);
            element = (Element) nNode;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getTagValue(String tag, Element element){
        NodeList nList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nList.item(0);
        if (nValue == null){
            return null;
        }
        return nValue.getNodeValue();
    }
}

