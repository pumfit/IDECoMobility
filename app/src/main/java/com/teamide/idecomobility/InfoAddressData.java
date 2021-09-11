package com.teamide.idecomobility;

import android.os.Parcel;
import android.os.Parcelable;

public class InfoAddressData implements Parcelable
{
    private SearchAddressData curruntAddress;
    private SearchAddressData startAddress;
    private SearchAddressData endAddress;

    public InfoAddressData(SearchAddressData curruntAddress, SearchAddressData startAddress, SearchAddressData endAddress)

    {
        this.curruntAddress = curruntAddress;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    public InfoAddressData() {

    }

    protected InfoAddressData(Parcel in) {
        curruntAddress = in.readParcelable(SearchAddressData.class.getClassLoader());
        startAddress = in.readParcelable(SearchAddressData.class.getClassLoader());
        endAddress = in.readParcelable(SearchAddressData.class.getClassLoader());
    }

    public static final Creator<InfoAddressData> CREATOR = new Creator<InfoAddressData>() {
        @Override
        public InfoAddressData createFromParcel(Parcel in) {
            return new InfoAddressData(in);
        }

        @Override
        public InfoAddressData[] newArray(int size) {
            return new InfoAddressData[size];
        }
    };

    public SearchAddressData getCurruntAddress() {
        return curruntAddress;
    }

    public void setCurruntAddress(SearchAddressData curruntAddress) {
        this.curruntAddress = curruntAddress;
    }

    public SearchAddressData getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(SearchAddressData startAddress) {
        this.startAddress = startAddress;
    }

    public SearchAddressData getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(SearchAddressData endAddress) {
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