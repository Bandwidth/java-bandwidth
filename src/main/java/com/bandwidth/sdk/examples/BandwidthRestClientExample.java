package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.account.BandwidthAccountTransaction;
import com.bandwidth.sdk.account.BandwidthAccount;
import com.bandwidth.sdk.applications.BandwidthApplication;
import com.bandwidth.sdk.applications.BandwidthApplications;

import java.io.IOException;
import java.util.List;

/**
 * @author vpotapenko
 */
public class BandwidthRestClientExample {

    public static void main(String[] args) throws IOException {
        BandwidthRestClient client = new BandwidthRestClient("<userId>", "<token>", "<secret>"); // todo

        BandwidthAccount account = client.getAccount();
        System.out.println(account.getAccountInfo());

        System.out.println("\nTransactions:");
        List<BandwidthAccountTransaction> accountTransactions = account.getTransactions().maxItems(10).get();
        for (BandwidthAccountTransaction transaction : accountTransactions) {
            System.out.println(transaction);
        }

        System.out.println("\nApplications:");
        BandwidthApplications applications = client.getApplications();
        List<BandwidthApplication> applicationList = applications.getApplications().get();
        for (BandwidthApplication application : applicationList) {
            System.out.println(application);
        }
    }
}
