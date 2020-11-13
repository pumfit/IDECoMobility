package com.teamide.idecomobility;

public class SubWayInfoData {
    private int icon;
    private String infoImageUrl;
    private String title;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getInfoImage() {
        return infoImageUrl;
    }

    public void setInfoImage(String infoImageUrl) {
        this.infoImageUrl = infoImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SubWayInfoData(int icon, String infoImageUrl, String title) {
        this.icon = icon;
        this.infoImageUrl = infoImageUrl;
        this.title = title;
    }

    public SubWayInfoData(String title) {
        this.title = title;
    }
}
