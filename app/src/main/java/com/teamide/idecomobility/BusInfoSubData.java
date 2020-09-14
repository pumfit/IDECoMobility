package com.teamide.idecomobility;

public class BusInfoSubData {
    private String busNum;
    private String arrivalTime;

    public BusInfoSubData(String busNum, String arrivalTime) {
        this.busNum = busNum;
        this.arrivalTime = arrivalTime;
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
}
