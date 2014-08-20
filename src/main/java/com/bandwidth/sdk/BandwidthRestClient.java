package com.bandwidth.sdk;

import com.bandwidth.sdk.driver.HttpRestDriver;
import com.bandwidth.sdk.driver.IRestDriver;
import com.bandwidth.sdk.model.*;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Main point of using Bandwidth API.
 *
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
    private Media media;

    public BandwidthRestClient(String userId, String token, String secret) {
        usersUri = String.format(BandwidthConstants.USERS_URI_PATH, userId);
        restDriver = new HttpRestDriver(token, secret);
    }

    /**
     * Gets point for <code>/v1/users/{userId}/account</code>
     *
     * @return point for account
     */
    public Account getAccount() {
        if (account == null) {
            account = new Account(restDriver, usersUri);
        }
        return account;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/applications</code>
     *
     * @return point for applications
     */
    public Applications getApplications() {
        if (applications == null) {
            applications = new Applications(restDriver, usersUri);
        }
        return applications;
    }

    /**
     * Gets point for <code>/v1/availableNumbers</code>
     *
     * @return point for available numbers
     */
    public AvailableNumbers getAvailableNumbers() {
        if (availableNumbers == null) {
            availableNumbers = new AvailableNumbers(restDriver);
        }
        return availableNumbers;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/bridges</code>
     *
     * @return point for bridges
     */
    public Bridges getBridges() {
        if (bridges == null) {
            bridges = new Bridges(restDriver, usersUri);
        }
        return bridges;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/calls</code>
     *
     * @return point for calls
     */
    public Calls getCalls() {
        if (calls == null) {
            calls = new Calls(restDriver, usersUri);
        }
        return calls;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/conferences</code>
     *
     * @return point for conferences
     */
    public Conferences getConferences() {
        if (conferences == null) {
            conferences = new Conferences(restDriver, usersUri);
        }
        return conferences;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/errors</code>
     *
     * @return point for errors
     */
    public Errors getErrors() {
        if (errors == null) {
            errors = new Errors(restDriver, usersUri);
        }
        return errors;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/messages</code>
     *
     * @return point for messages
     */
    public Messages getMessages() {
        if (messages == null) {
            messages = new Messages(restDriver, usersUri);
        }
        return messages;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/phoneNumbers</code>
     *
     * @return point for phone numbers
     */
    public PhoneNumbers getPhoneNumbers() {
        if (phoneNumbers == null) {
            phoneNumbers = new PhoneNumbers(restDriver, usersUri);
        }
        return phoneNumbers;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/recordings</code>
     *
     * @return point for recordings
     */
    public Recordings getRecordings() {
        if (recordings == null) {
            recordings = new Recordings(restDriver, usersUri);
        }
        return recordings;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/media</code>
     *
     * @return point for media
     */
    public Media getMedia() {
        if (media == null) {
            media = new Media(restDriver, usersUri);
        }
        return media;
    }

    /**
     * Returns information about this number.
     *
     * @param number searching number
     * @return information about the number
     * @throws IOException
     */
    public NumberInfo getNumberInfoByNumber(String number) throws IOException {
        String uri = StringUtils.join(new String[]{
                "phoneNumbers",
                "numberInfo",
                number
        }, '/');
        JSONObject object = restDriver.getObject(uri);
        return new NumberInfo(restDriver, uri, object);
    }

    /**
     * For testing
     */
    public void setRestDriver(IRestDriver restDriver) {
        this.restDriver = restDriver;
    }
}
