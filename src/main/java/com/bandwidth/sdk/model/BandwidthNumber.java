package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;

/**
 * @author vpotapenko
 */
public class BandwidthNumber {

    private String number;
    private String nationalNumber;
    private String patternMatch;
    private String city;
    private String lata;
    private String rateCenter;
    private String state;
    private Double price;

    private BandwidthNumber() {
    }

    public static BandwidthNumber from(JSONObject jsonObject) {
        BandwidthNumber bandwidthNumber = new BandwidthNumber();

        bandwidthNumber.number = (String) jsonObject.get("number");
        bandwidthNumber.nationalNumber = (String) jsonObject.get("nationalNumber");
        bandwidthNumber.patternMatch = (String) jsonObject.get("patternMatch");
        bandwidthNumber.city = (String) jsonObject.get("city");
        bandwidthNumber.lata = (String) jsonObject.get("lata");
        bandwidthNumber.rateCenter = (String) jsonObject.get("rateCenter");
        bandwidthNumber.state = (String) jsonObject.get("state");
        bandwidthNumber.price = Double.parseDouble((String) jsonObject.get("price"));

        return bandwidthNumber;
    }

    public String getNumber() {
        return number;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public String getPatternMatch() {
        return patternMatch;
    }

    public String getCity() {
        return city;
    }

    public String getLata() {
        return lata;
    }

    public String getRateCenter() {
        return rateCenter;
    }

    public String getState() {
        return state;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "BandwidthNumber{" +
                "number='" + number + '\'' +
                ", nationalNumber='" + nationalNumber + '\'' +
                ", patternMatch='" + patternMatch + '\'' +
                ", city='" + city + '\'' +
                ", lata='" + lata + '\'' +
                ", rateCenter='" + rateCenter + '\'' +
                ", state='" + state + '\'' +
                ", price=" + price +
                '}';
    }
}
