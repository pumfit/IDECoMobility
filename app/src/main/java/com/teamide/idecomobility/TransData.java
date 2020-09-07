package com.teamide.idecomobility;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TransData {
    String jsonbus;
    String jsonsubway;
    String jsonimg;

    void jsonBus(){
        JSONObject inner = new JSONObject();
        inner.put("stNm","서울 여대 후문 정류소");
        inner.put("rtNm","7738");
        inner.put("exps1_sec","600");

        JSONObject outer = new JSONObject();
        outer.put("inf",inner);

        jsonbus = outer.toJSONString();

        System.out.println(jsonbus);
    }
    void jsonSubway(){
        JSONObject inner = new JSONObject();
        inner.put("statnNm","태릉입구");
        inner.put("barvlDt_sec","420");

        JSONObject outer = new JSONObject();
        outer.put("inf",inner);

        jsonsubway = outer.toJSONString();

        System.out.println(jsonsubway);
    }
    void jsonImg(){
        JSONObject inner = new JSONObject();
        inner.put("storedName","http://indoormap.seoul.go.kr/app/openapi/seoulcity/handicapPOIInfo/84/stream");

        JSONObject outer = new JSONObject();
        outer.put("inf",inner);

        jsonimg = outer.toJSONString();

        System.out.println(jsonsubway);
    }
}