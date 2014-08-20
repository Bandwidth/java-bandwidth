package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Account transaction
 *
 * @author vpotapenko
 */
public class AccountTransaction extends BaseModelObject {

    public AccountTransaction(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
    }

    public String getType() {
        return getPropertyAsString("type");
    }

    public Date getDateTime() {
        return getPropertyAsDate("time");
    }

    public double getAmount() {
        return getPropertyAsDouble("amount");
    }

    public long getUnits() {
        return getPropertyAsLong("units");
    }

    public String getProductType() {
        return getPropertyAsString("productType");
    }

    public String getNumber() {
        return getPropertyAsString("number");
    }

    @Override
    public String toString() {
        return "AccountTransaction{" +
                "id='" + getId() + '\'' +
                ", type='" + getType() + '\'' +
                ", dateTime=" + getDateTime() +
                ", amount=" + getAmount() +
                ", units=" + getUnits() +
                ", productType='" + getProductType() + '\'' +
                ", number='" + getNumber() + '\'' +
                '}';
    }
}
