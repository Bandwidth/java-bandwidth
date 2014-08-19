package com.bandwidth.sdk;

import com.bandwidth.sdk.driver.HttpRestDriver;
import com.bandwidth.sdk.driver.IRestDriver;
import com.bandwidth.sdk.model.*;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * @author vpotapenko
 */
public class BandwidthRestClient {

    private final String usersUri;

    private IRestDriver restDriver;

    private Account account;
    private Applications applications;
    private AvailableNumbers availableNumbers;
    private Bridges bridges;
    private Calls calls;
    private Conferences conferences;
    private Errors errors;
    private Messages messages;
    private PhoneNumbers phoneNumbers;
    private Recordings recordings;

    public BandwidthRestClient(String userId, String token, String secret) {
        usersUri = String.format(BandwidthConstants.USERS_URI_PATH, userId);
        restDriver = new HttpRestDriver(token, secret);
    }

    /** For testing */
    public void setRestDriver(IRestDriver restDriver) {
        this.restDriver = restDriver;
    }

    public Account getAccount() {
        if (account == null) {
            account = new Account(restDriver, usersUri);
        }
        return account;
    }

    public Applications getApplications() {
        if (applications == null) {
            applications = new Applications(restDriver, usersUri);
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
            bridges = new Bridges(restDriver, usersUri);
        }
        return bridges;
    }

    public Calls getCalls() {
        if (calls == null) {
            calls = new Calls(restDriver, usersUri);
        }
        return calls;
    }

    public Conferences getConferences() {
        if (conferences == null) {
            conferences = new Conferences(restDriver, usersUri);
        }
        return conferences;
    }

    public Errors getErrors() {
        if (errors == null) {
            errors = new Errors(restDriver, usersUri);
        }
        return errors;
    }

    public Messages getMessages() {
        if (messages == null) {
            messages = new Messages(restDriver, usersUri);
        }
        return messages;
    }

    public PhoneNumbers getPhoneNumbers() {
        if (phoneNumbers == null) {
            phoneNumbers = new PhoneNumbers(restDriver, usersUri);
        }
        return phoneNumbers;
    }

    public Recordings getRecordings() {
        if (recordings == null) {
            recordings = new Recordings(restDriver, usersUri);
        }
        return recordings;
    }

    public NumberInfo getNumberInfoByNumber(String number) throws IOException {
        String uri = StringUtils.join(new String[]{
                "phoneNumbers",
                "numberInfo",
                number
        }, '/');
        JSONObject object = restDriver.getObject(uri);
        return new NumberInfo(restDriver, uri, object);
    }
}
