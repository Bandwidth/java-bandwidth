package com.bandwidth.sdk;

import com.bandwidth.sdk.account.BandwidthAccount;
import com.bandwidth.sdk.driver.HttpRestDriver;
import com.bandwidth.sdk.driver.IRestDriver;

/**
 * @author vpotapenko
 */
public class BandwidthRestClient {

    private IRestDriver restDriver;

    public BandwidthRestClient(String userId, String token, String secret) {
        restDriver = new HttpRestDriver(userId, token, secret);
    }

    public BandwidthAccount getAccount() {
        return new BandwidthAccount(this);
    }

    public IRestDriver getRestDriver() {
        return restDriver;
    }

    /** For testing */
    public void setRestDriver(IRestDriver restDriver) {
        this.restDriver = restDriver;
    }
}
