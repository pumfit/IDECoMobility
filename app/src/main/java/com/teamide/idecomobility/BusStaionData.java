package com.teamide.idecomobility;

public class BusStaionData {
    private int listNum;
    private String busStationName;

    public BusStaionData(int listNum, String busStationName) {
        this.listNum = listNum;
        this.busStationName = busStationName;
    }

    public int getListNum() {
        return listNum;
    }

    public void setListNum(int listNum) {
        this.listNum = listNum;
    }

    public String getBusStationName() {
        return busStationName;
    }

    public void setBusStationName(String busStationName) {
        this.busStationName = busStationName;
    }
}
