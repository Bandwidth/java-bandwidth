package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.model.Account;

/**
 * This example explains how the API credentials are set and shows an example. These are the API User Id, the
 * API Token and API Secret, which are all found from the Account tab in the App Platform UI.
 *
 * There are 3 ways to set these credentials:
 *
 * 1. Via environment variables
 * 2. Via Java VM System Properties, set as -D arguments on the VM command line.
 * 3. Directly by way of a method call on the BandwidthClient object
 *
 * A quick word on the HttpClient design
 *
 * The BandwidthClient class wraps the Apache HttpClient class. BandwidthClient is a singleton to make it easy for
 * resource classes to simply perform an operation. For instance to make a call you simply use the following:
 *
 * Call.create(toNumber, fromNumber);
 *
 * No need to fuss with what the HttpClient actually needs to make this REST API call.
 *
 * In order to make that work, the credentials need to be set on the client singleton before making API calls. To
 * make this as flexible as possible, you can use any of the three different approaches.
 *
 *
 * Setting credentials via Environment Variables
 *
 * This approach makes it easy to have the credentials separate from the rest of the code base and is ideally
 * suited for a platform like Heroku. Simply set the following environment variables:
 *
 *  BANDWIDTH_USER_ID
 *  BANDWIDTH_API_TOKEN
 *  BANDWIDTH_API_SECRET
 *
 *  Note that you can also set the endpoint and version in the same way by setting the following:
 *
 *  BANDWIDTH_API_ENDPOINT
 *  BANDWIDTH_API_VERSION
 *
 *  By default these are set as follows:
 *
 *  BANDWIDTH_API_ENDPOINT = "https://api.catapult.inetwork.com"
 *  BANDWIDTH_API_VERSION = "v1"
 *
 *  All of these can be set using the shell script, src/main/scripts/setenv.sh
 *
 *
 * Setting credentials via Java VM System Properties
 *
 * This approach works for a container that uses JAVA_OPTS, like Tomcat. If any of the environment variables are not
 * set, the the BandwidthClient will look for a Java VM System argument. These are set with a -D on the Java command
 * line, e.g. -Dcom.bandwidth.userId=myUserId. Simply set the following on the command line (typically as JAVA_OPTS):
 *
 * com.bandwidth.userId
 * com.bandwidth.apiToken
 * com.bandwidth.apiSecret
 *
 * And as with environment variables, you can also set the endpoint and version with the following
 * (which have the defaults explain above):
 *
 * com.bandwidth.apiEndpoint
 * com.bandwidth.apiVersion
 *
 *
 * Setting credentials via method call on the BandwidthClient class.
 *
 * This approach works for those that want to set the credentials in code, maybe from an encryted file. This
 * provides the greatest flexibility; Simply call the setCredentials() method on the BandwidthClient object, e.g.:
 *
 * BandwidthClient.getInstance().setCredentials(userId, apiToken, apiSecret);
 *
 * You can set the endpoint and version the same way:
 *
 * BandwidthClient.getInstance.setEndpointandVersion(apiEndpoint, apiVersion);
 *
 *
 * Created by smitchell on 11/10/14.
 */
public class CredentialsExamples {



    public static void main(String[]args) {

        // First test if the creds are set via environment args
        String userId = System.getenv().get("BANDWIDTH_USER_ID");
        String apiToken = System.getenv().get("BANDWIDTH_API_TOKEN");
        String apiSecret = System.getenv().get("BANDWIDTH_API_SECRET");

        try {
            Account account = Account.get();

            System.out.println(account);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        // Then test if they are set via system props

        userId = System.getProperty("com.bandwidth.userId");
        apiToken = System.getProperty("com.bandwidth.apiToken");
        apiSecret = System.getProperty("com.bandwidth.apiSecret");

        try {
            Account account = Account.get();

            System.out.println(account);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        // Finally just set them directly
        userId = "your User Id here";
        apiToken = "your API Token here";
        apiSecret = "your API Secret here";

        try {
            BandwidthClient.getInstance().setCredentials(userId, apiToken, apiSecret);

            Account account = Account.get();

            System.out.println(account);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
