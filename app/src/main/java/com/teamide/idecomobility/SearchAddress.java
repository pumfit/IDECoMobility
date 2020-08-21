package com.teamide.idecomobility;

public class SearchAddress {
    private String mainAdress;
    private String fullAdress;
    private String distance;

    public SearchAddress(String mainAdress, String fullAdress, String distance)
    {
        this.mainAdress = mainAdress;
        this.fullAdress = fullAdress;
        this.distance = distance;
    }

    public String getMainAdress() {
        return mainAdress;
    }

    public void setMainAdress(String mainAdress) {
        this.mainAdress = mainAdress;
    }

    public String getFullAdress() {
        return fullAdress;
    }

    public void setFullAdress(String fullAdress) {
        this.fullAdress = fullAdress;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
