package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Account transaction
 *
 * @author vpotapenko
 */
public class AccountTransaction extends ResourceBase {

    public AccountTransaction(BandwidthClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
    protected void setUp(JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }

    protected String getUri() {
        return client.getUserResourceInstanceUri(BandwidthConstants.ACCOUNT_TRANSACTIONS_URI_PATH, getId());
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
