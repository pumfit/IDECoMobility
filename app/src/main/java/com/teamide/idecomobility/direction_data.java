package com.teamide.idecomobility;

import android.graphics.drawable.Drawable;

public class direction_data {
    private String circle;
    private String title;
    private String busOrSub;
    private String num;
    private String minLeft;
    private int img;

    public direction_data(int img, String  circle, String title, String busOrSub, String num, String minLeft){
        this.img = img;
        this.circle = circle;
        this.title = title;
        this.busOrSub = busOrSub;
        this.num = num;
        this.minLeft = minLeft;
    }

    public int getImg()
    {
        return this.img;
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

    public  String getnum()
    {
        return this.num;
    }

    public String getminLeft()
    {
        return this.minLeft;
    }

}