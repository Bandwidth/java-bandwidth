package com.bandwidth.sdk;

import com.bandwidth.sdk.driver.HttpRestDriver;
import com.bandwidth.sdk.driver.IRestDriver;
import com.bandwidth.sdk.model.*;

/**
 * @author vpotapenko
 */
public class BandwidthRestClient {

    private IRestDriver restDriver;

    public BandwidthRestClient(String userId, String token, String secret) {
        restDriver = new HttpRestDriver(userId, token, secret);
    }

    public Account getAccount() {
        return new Account(this);
    }

    public Applications getApplications() {
        return new Applications(this);
    }

    public AvailableNumbers getAvailableNumbers() {
        return new AvailableNumbers(this);
    }

    public Bridges getBridges() {
        return new Bridges(this);
    }

    public Calls getCalls() {
        return new Calls(this);
    }

    public Conferences getConferences() {
        return new Conferences(this);
    }

    public IRestDriver getRestDriver() {
        return restDriver;
    }

    /** For testing */
    public void setRestDriver(IRestDriver restDriver) {
        this.restDriver = restDriver;
    }
}
