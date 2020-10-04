package com.teamide.idecomobility;

public class BusStaionData {
    private int listNum;
    private String busStationName;
    private String busStationID;

    public BusStaionData(int listNum, String busStationName, String busStationID) {
        this.listNum = listNum;
        this.busStationName = busStationName;
        this.busStationID = busStationID;
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

    public String getBusStationID() {
        return busStationID;
    }

    public void setBusStationID(String busStationID) {
        this.busStationID = busStationID;
    }
}
