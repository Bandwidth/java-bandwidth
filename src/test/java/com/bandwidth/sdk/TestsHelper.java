package com.bandwidth.sdk;

/**
 * Created by sbarstow on 9/30/14.
 */
public class TestsHelper {

    public static String TEST_USER_ID = "userId";

    public static MockRestClient getClient(){
        return new MockRestClient(TEST_USER_ID, "", "", "", "");
    }
}
