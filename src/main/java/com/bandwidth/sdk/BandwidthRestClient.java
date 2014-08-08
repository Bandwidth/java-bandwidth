package com.bandwidth.sdk;

import com.bandwidth.sdk.account.BandwidthAccount;
import com.bandwidth.sdk.applications.BandwidthApplications;
import com.bandwidth.sdk.availableNumbers.BandwidthAvailableNumbers;
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

    public BandwidthApplications getApplications() {
        return new BandwidthApplications(this);
    }

    public BandwidthAvailableNumbers getAvailableNumbers() {
        return new BandwidthAvailableNumbers(this);
    }

    public IRestDriver getRestDriver() {
        return restDriver;
    }

    /** For testing */
    public void setRestDriver(IRestDriver restDriver) {
        this.restDriver = restDriver;
    }
}
