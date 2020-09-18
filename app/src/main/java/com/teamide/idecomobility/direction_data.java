package com.teamide.idecomobility;

public class direction_data {
    private String circle;
    private String title;
    private String busOrSub;
    private String num;
    private String minLeft;

    public direction_data(String  circle, String title, String busOrSub, String num, String minLeft){
        this.circle = circle;
        this.title = title;
        this.busOrSub = busOrSub;
        this.num = num;
        this.minLeft = minLeft;
    }

    public String getcircle()
    {
        return this.circle;
    }

    public String gettitle()
    {
        return this.title;
    }

    public String getbusOrSub()
    {
        return this.busOrSub;
    }

    public  String getnum() { return this.num; }

    public String getminLeft()
    {
        return this.minLeft;
    }

}