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

    private Account account;
    private Applications applications;
    private AvailableNumbers availableNumbers;
    private Bridges bridges;
    private Calls calls;
    private Conferences conferences;
    private Errors errors;
    private Messages messages;

    public BandwidthRestClient(String userId, String token, String secret) {
        parentUri = String.format(BandwidthConstants.MAIN_URI_PATH, userId);
        restDriver = new HttpRestDriver(token, secret);
    }

    public Account getAccount() {
        if (account == null) {
            account = new Account(restDriver, parentUri);
        }
        return account;
    }

    public Applications getApplications() {
        if (applications == null) {
            applications = new Applications(restDriver, parentUri);
        }
        return applications;
    }

    public AvailableNumbers getAvailableNumbers() {
        if (availableNumbers == null) {
            availableNumbers = new AvailableNumbers(restDriver);
        }
        return availableNumbers;
    }

    public Bridges getBridges() {
        if (bridges == null) {
            bridges = new Bridges(restDriver, parentUri);
        }
        return bridges;
    }

    public Calls getCalls() {
        if (calls == null) {
            calls = new Calls(restDriver, parentUri);
        }
        return calls;
    }

    public Conferences getConferences() {
        if (conferences == null) {
            conferences = new Conferences(restDriver, parentUri);
        }
        return conferences;
    }

    public Errors getErrors() {
        if (errors == null) {
            errors = new Errors(restDriver, parentUri);
        }
        return errors;
    }

    public Messages getMessages() {
        if (messages == null) {
            messages = new Messages(restDriver, parentUri);
        }
        return messages;
    }
}
