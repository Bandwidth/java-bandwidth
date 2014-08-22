package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

/**
 * Account information
 *
 * @author vpotapenko
 */
public class AccountInfo extends BaseModelObject {

    public AccountInfo(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
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
