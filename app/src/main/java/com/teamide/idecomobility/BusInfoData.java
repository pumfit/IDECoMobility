package com.teamide.idecomobility;

public class BusInfoData {
    private String busName;
    private String busTime;
    private String busType;

    public BusInfoData(String busName, String busTime, String busType){
        this.busName = busName;
        this.busTime = busTime;
        this.busType = busType;
    }
    //public void setBusName(String busName){this.busName=busName;}
    //public void setBusTime(String busTime){this.busTime=busTime;}
    //public void setBusType(String busType){this.busType=busType;}
    public String getBusName(){return this.busName;}
    public String getBusTime(){return this.busTime;}
    public String getBusType(){return this.busType;}
}
