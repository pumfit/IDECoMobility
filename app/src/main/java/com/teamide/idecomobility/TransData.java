package com.teamide.idecomobility;

import org.json.simple.JSONObject;

public class TransData {
    String jsonbus;
    String jsonsubway;
    String jsonimg;

    public TransData() {
        jsonBus();
        jsonSubway();
        jsonImg();
    }

    String jsonBus(){
        JSONObject inner = new JSONObject();
        inner.put("stNm","서울 여대 후문 정류소");
        inner.put("rtNm","7738");
        inner.put("exps1_sec","600");

        JSONObject outer = new JSONObject();
        outer.put("inf",inner);

        jsonbus = outer.toJSONString();

        return jsonbus;
    }
    String jsonSubway(){
        JSONObject inner = new JSONObject();
        inner.put("statnNm","태릉입구");
        inner.put("barvlDt_sec","420");

        JSONObject outer = new JSONObject();
        outer.put("inf",inner);

        jsonsubway = outer.toJSONString();

        return jsonsubway;
    }
    String jsonImg(){
        JSONObject inner = new JSONObject();
        inner.put("storedName","http://indoormap.seoul.go.kr/app/openapi/seoulcity/handicapPOIInfo/84/stream");

        JSONObject outer = new JSONObject();
        outer.put("inf",inner);

        jsonimg = outer.toJSONString();

        return jsonimg;
    }
}