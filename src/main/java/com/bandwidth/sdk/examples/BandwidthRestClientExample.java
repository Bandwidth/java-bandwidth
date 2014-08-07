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

        printAccount(client);
        printApplications(client);
    }

    private static void printApplications(BandwidthRestClient client) throws IOException {
        System.out.println("\nApplications:");
        BandwidthApplications applications = client.getApplications();
        List<BandwidthApplication> applicationList = applications.getApplications().get();
        for (BandwidthApplication application : applicationList) {
            System.out.println(application);
        }

        System.out.println("\nApplication by id:");
        String id = applicationList.get(0).getId();
        BandwidthApplication application = applications.getApplicationById(id);
        System.out.println(application);
    }

    private static void printAccount(BandwidthRestClient client) throws IOException {
        BandwidthAccount account = client.getAccount();
        System.out.println(account.getAccountInfo());

        System.out.println("\nTransactions:");
        List<BandwidthAccountTransaction> accountTransactions = account.getTransactions().maxItems(10).get();
        for (BandwidthAccountTransaction transaction : accountTransactions) {
            System.out.println(transaction);
        }
    }
}
