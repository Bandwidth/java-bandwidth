package com.bandwidth.sdk;

/**
 * @author vpotapenko
 */
public abstract class BandwidthConstants {

    private BandwidthConstants() {
    }

    public static final String TRANSACTION_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    // REST constants
    public static final String API_ENDPOINT = "https://api.catapult.inetwork.com";
    public static final String API_VERSION = "v1";

    public static final String ACCOUNT_PATH = "users/%s/account"; // userId as a parameter
    public static final String ACCOUNT_TRANSACTIONS_PATH = "users/%s/account/transactions"; // userId as a parameter
    public static final String APPLICATIONS_PATH = "users/%s/applications"; // userId as a parameter
    public static final String LOCAL_AVAILABLE_NUMBERS_PATH = "availableNumbers/local";
    public static final String TOLL_FREE_AVAILABLE_NUMBERS_PATH = "availableNumbers/tollFree";
    public static final String BRIDGES_PATH = "users/%s/bridges"; // userId as a parameter
    public static final String BRIDGE_AUDIO_PATH = "users/%s/bridges/%s/audio"; // userId and bridgeId as parameters
    public static final String BRIDGE_CALLS_PATH = "users/%s/bridges/%s/calls"; // userId and bridgeId as parameters
    public static final String CALLS_PATH = "users/%s/calls"; // userId as a parameter
    public static final String CALL_AUDIO_PATH = "users/%s/calls/%s/audio"; // userId and callId as parameters
    public static final String CALL_DTMF_PATH = "users/%s/calls/%s/dtmf"; // userId and callId as parameters
    public static final String CALL_EVENTS_PATH = "users/%s/calls/%s/events"; // userId and callId as parameters
    public static final String CALL_RECORDINGS_PATH = "users/%s/calls/%s/recordings"; // userId and callId as parameters
    public static final String CALL_GATHER_PATH = "users/%s/calls/%s/gather"; // userId and callId as parameters
    public static final String CONFERENCES_PATH = "users/%s/conferences"; // userId as a parameter
    public static final String CONFERENCE_AUDIO_PATH = "users/%s/conferences/%s/audio"; // userId and conferenceId as parameters
    public static final String CONFERENCE_MEMBERS_PATH = "users/%s/conferences/%s/members"; // userId and conferenceId as parameters
}
