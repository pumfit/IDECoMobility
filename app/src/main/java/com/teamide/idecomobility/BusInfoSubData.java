package com.teamide.idecomobility;

public class BusInfoSubData {
    private String busNum;
    private String arrivalTime;
    private String busType;

    public BusInfoSubData(String busNum, String arrivalTime, String busType) {
        this.busNum = busNum;
        this.arrivalTime = arrivalTime;
        this.busType = busType;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.arrivalTime = busType;
    }
}
