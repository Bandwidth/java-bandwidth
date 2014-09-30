package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

/**
 * Account information
 *
 * @author vpotapenko
 */
public class AccountInfo extends BaseModelObject {

    public AccountInfo(BandwidthRestClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
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
