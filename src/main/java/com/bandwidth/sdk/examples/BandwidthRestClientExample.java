package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.account.AccountInfo;

import java.io.IOException;

/**
 * @author vpotapenko
 */
public class BandwidthRestClientExample {

    public static void main(String[] args) throws IOException {
        BandwidthRestClient client = new BandwidthRestClient("<userId>", "<token>", "<secret>");
        AccountInfo accountInfo = client.getAccount().getAccountInfo();
        System.out.println(accountInfo); // todo
    }
}
