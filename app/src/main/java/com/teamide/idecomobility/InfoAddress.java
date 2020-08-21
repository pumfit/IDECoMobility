package com.teamide.idecomobility;

public class InfoAddress
{
    private String curruntAddress;
    private String startAddress;
    private String endAddress;

    public String getCurruntAddress() {
        return curruntAddress;
    }

    public void setCurruntAddress(String curruntAddress) {
        this.curruntAddress = curruntAddress;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public InfoAddress(String curruntAddress, String startAddress, String endAddress) {
        this.curruntAddress = curruntAddress;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    public InfoAddress(){}
}