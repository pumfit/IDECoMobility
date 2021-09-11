package com.teamide.idecomobility;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchAddressData implements Parcelable { //검색주소의 상세 데이터 클래스
    private String mainAdress;//검색한 내용
    private String fullAdress;//검색 풀 주소
    private String distance;//현재 위치에서로 부터의 거리
    private double latitude = 0;//위도
    private double longitude = 0;//경도

    public SearchAddressData()
    {
    }

    public SearchAddressData(String mainAdress, String fullAdress, String distance)
    {
        this.mainAdress = mainAdress;
        this.fullAdress = fullAdress;
        this.distance = distance;
    }

    public SearchAddressData(String mainAdress, String fullAdress, double latitude, double longitude)
    {
        this.mainAdress = mainAdress;
        this.fullAdress = fullAdress;
        this.distance = "0km";
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public SearchAddressData(String mainAdress, String fullAdress, String distance, double latitude, double longitude) {
        this.mainAdress = mainAdress;
        this.fullAdress = fullAdress;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected SearchAddressData(Parcel in) {
        mainAdress = in.readString();
        fullAdress = in.readString();
        distance = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<SearchAddressData> CREATOR = new Creator<SearchAddressData>() {
        @Override
        public SearchAddressData createFromParcel(Parcel in) {
            return new SearchAddressData(in);
        }

        @Override
        public SearchAddressData[] newArray(int size) {
            return new SearchAddressData[size];
        }
    };


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mainAdress);
        dest.writeString(fullAdress);
        dest.writeString(distance);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
