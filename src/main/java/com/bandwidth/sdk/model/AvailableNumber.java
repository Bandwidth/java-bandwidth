package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

/**
 * Available number information.
 *
 * @author vpotapenko
 */
public class AvailableNumber extends BaseModelObject {

    public AvailableNumber(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
    }

    @Override
    protected String getUri() {
        return null;
    }

    public String getNumber() {
        return getPropertyAsString("number");
    }

    public String getNationalNumber() {
        return getPropertyAsString("nationalNumber");
    }

    public String getPatternMatch() {
        return getPropertyAsString("patternMatch");
    }

    public String getCity() {
        return getPropertyAsString("city");
    }

    public String getLata() {
        return getPropertyAsString("lata");
    }

    public String getRateCenter() {
        return getPropertyAsString("rateCenter");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public Double getPrice() {
        return getPropertyAsDouble("price");
    }

    @Override
    public String toString() {
        return "Number{" +
                "number='" + getNumber() + '\'' +
                ", nationalNumber='" + getNationalNumber() + '\'' +
                ", patternMatch='" + getPatternMatch() + '\'' +
                ", city='" + getCity() + '\'' +
                ", lata='" + getLata() + '\'' +
                ", rateCenter='" + getRateCenter() + '\'' +
                ", state='" + getState() + '\'' +
                ", price=" + getPrice() +
                '}';
    }
}
