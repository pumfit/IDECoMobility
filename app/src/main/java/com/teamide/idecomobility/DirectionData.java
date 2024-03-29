package com.teamide.idecomobility;

public class DirectionData {
    private String circle;
    private String startTraffic;
    private String endTraffic;
    private String moveTime;
    private String stationCount;
    private int img;

    public DirectionData(int img, String  circle, String startTraffic, String endTraffic, String moveTime, String stationCount){
        this.img = img;
        this.circle = circle;
        this.startTraffic = startTraffic;
        this.endTraffic = endTraffic;
        this.moveTime = moveTime;
        this.stationCount = stationCount;
    }

    public int getImg()
    {
        return this.img;
    }

    public String getcircle()
    {
        return this.circle;
    }

    public String getstartTraffic()
    {
        return this.startTraffic;
    }

    public String getendTraffic()
    {
        return this.endTraffic;
    }

    public String getmoveTime()
    {
        return this.moveTime;
    }

    public String getstationCount()
    {
        return this.stationCount;
    }
}