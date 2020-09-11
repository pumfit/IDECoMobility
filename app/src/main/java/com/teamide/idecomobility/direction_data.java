
package com.teamide.idecomobility;

public class direction_data {

    private String title;
    private String button;

    public direction_data(String  title, String button){
        this.title = title;
        this.button = button;

    }

    public String gettitle()
    {
        return this.title;
    }

    public String getbutton()
    {
        return this.button;
    }

}
