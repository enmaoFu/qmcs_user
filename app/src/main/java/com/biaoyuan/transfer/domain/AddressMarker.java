package com.biaoyuan.transfer.domain;

/**
 * Title  :
 * Create : 2017/6/1
 * Author ：chen
 */

public class AddressMarker {

    /**
     * lat : 29.5321999
     * lng : 106.6080017
     * name : 南山街道
     */

    private double lat;
    private double lng;
    private String name;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
