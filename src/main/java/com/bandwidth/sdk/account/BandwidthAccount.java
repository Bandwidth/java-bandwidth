package com.bandwidth.sdk.account;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * @author vpotapenko
 */
public class BandwidthAccount {

    private final BandwidthRestClient client;

    public BandwidthAccount(BandwidthRestClient client) {
        this.client = client;
    }

    public AccountInfo getAccountInfo() throws IOException {
        JSONObject jsonObject = client.getRestDriver().requestAccountInfo();
        return AccountInfo.from(jsonObject);
    }
}
