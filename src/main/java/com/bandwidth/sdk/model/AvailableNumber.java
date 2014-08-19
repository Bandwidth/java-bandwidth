package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.json.simple.JSONObject;

/**
 * @author vpotapenko
 */
public class AvailableNumber extends BaseModelObject {

    public AvailableNumber(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
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
