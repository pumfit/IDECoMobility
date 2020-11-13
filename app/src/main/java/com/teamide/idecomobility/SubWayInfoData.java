package com.teamide.idecomobility;

public class SubWayInfoData {
    private int icon;
    private int infoImage;
    private String title;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getInfoImage() {
        return infoImage;
    }

    public void setInfoImage(int infoImage) {
        this.infoImage = infoImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SubWayInfoData(int icon, int infoImage, String title) {
        this.icon = icon;
        this.infoImage = infoImage;
        this.title = title;
    }

    public SubWayInfoData(String title) {
        this.title = title;
    }
}
