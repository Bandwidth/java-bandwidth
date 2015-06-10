package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;
import org.json.simple.JSONObject;

/**
 * Account information
 *
 * @author vpotapenko
 */
public class AccountInfo extends ResourceBase {

    public AccountInfo(final BandwidthClient client, final JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
    protected void setUp(final JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }

    protected String getUri() {
        return client.getUserResourceUri(BandwidthConstants.ACCOUNT_URI_PATH);
    }


    public String getAccountType() {
        return getPropertyAsString("accountType");
    }

    public double getBalance() {
        return getPropertyAsDouble("balance");
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "accountType='" + getAccountType() + '\'' +
                ", balance=" + getBalance() +
                '}';
    }
}
