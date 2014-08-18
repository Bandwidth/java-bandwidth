package com.bandwidth.sdk;

import com.bandwidth.sdk.driver.HttpRestDriver;
import com.bandwidth.sdk.driver.IRestDriver;
import com.bandwidth.sdk.model.*;

/**
 * @author vpotapenko
 */
public class BandwidthRestClient {

    private final String parentUri;

    private IRestDriver restDriver;

    public BandwidthRestClient(String userId, String token, String secret) {
        parentUri = String.format(BandwidthConstants.MAIN_URI_PATH, userId);
        restDriver = new HttpRestDriver(token, secret);
    }

    public Account getAccount() {
        return new Account(restDriver, parentUri);
    }

    public Applications getApplications() {
        return new Applications(restDriver, parentUri);
    }

    public AvailableNumbers getAvailableNumbers() {
        return new AvailableNumbers(restDriver);
    }

    public Bridges getBridges() {
        return new Bridges(restDriver, parentUri);
    }

    public Calls getCalls() {
        return new Calls(restDriver, parentUri);
    }

    public Conferences getConferences() {
        return new Conferences(restDriver, parentUri);
    }

}
