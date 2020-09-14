package com.teamide.idecomobility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class BusTime {
    Element element;
    String busId;

    BusTime(String s) {
        this.busId = s;
        busXml();
        getData();
    }

    public void busXml() {
        try {
            String url = "http://ws.bus.go.kr/api/rest/arrive/getLowArrInfoByStId?"
                    + "ServiceKey=ESol2fpbzP%2F%2BPJfShFAU%2FaMbhqzepxntyCIskM2bqyY0dWWU4Sd1w0VH0JpBnWCdBUd79%2BLI34mOXFs0UjrMJA%3D%3D"
                    + "&stId=" + busId + "";
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("itemList");
            Node nNode = nList.item(0);
            element = (Element) nNode;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTagValue(String tag, Element element) {
        NodeList nList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nList.item(0);
        if (nValue == null) {
            return null;
        }
        return nValue.getNodeValue();
    }

    public ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<String>();
        String busstop = getTagValue("stNm", element); // 버스정류장 이름 가져오기
        String time = getTagValue("exps1", element); // 남은시간 블러오기 (초)
        Integer i = Integer.parseInt(time) / 60;

        String bustm = Integer.toString(i); // 남은시간 블러오기 (분)
        data.add(busstop);
        data.add(time);
        data.add(bustm);

        return data;

    }

}

