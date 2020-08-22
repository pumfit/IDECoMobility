package com.teamide.idecomobility;

import android.os.Parcel;
import android.os.Parcelable;

public class InfoAddress implements Parcelable
{
    private SearchAddress curruntAddress;
    private SearchAddress startAddress;
    private SearchAddress endAddress;

    public InfoAddress(SearchAddress curruntAddress, SearchAddress startAddress, SearchAddress endAddress)

    {
        this.curruntAddress = curruntAddress;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    public InfoAddress() {

    }

    protected InfoAddress(Parcel in) {
        curruntAddress = in.readParcelable(SearchAddress.class.getClassLoader());
        startAddress = in.readParcelable(SearchAddress.class.getClassLoader());
        endAddress = in.readParcelable(SearchAddress.class.getClassLoader());
    }

    public static final Creator<InfoAddress> CREATOR = new Creator<InfoAddress>() {
        @Override
        public InfoAddress createFromParcel(Parcel in) {
            return new InfoAddress(in);
        }

        @Override
        public InfoAddress[] newArray(int size) {
            return new InfoAddress[size];
        }
    };

    public SearchAddress getCurruntAddress() {
        return curruntAddress;
    }

    public void setCurruntAddress(SearchAddress curruntAddress) {
        this.curruntAddress = curruntAddress;
    }

    public SearchAddress getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(SearchAddress startAddress) {
        this.startAddress = startAddress;
    }

    public SearchAddress getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(SearchAddress endAddress) {
        this.endAddress = endAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(curruntAddress, flags);
        dest.writeParcelable(startAddress, flags);
        dest.writeParcelable(endAddress, flags);
    }
}