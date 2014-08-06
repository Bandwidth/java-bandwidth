package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.account.AccountTransaction;
import com.bandwidth.sdk.account.BandwidthAccount;

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

        List<AccountTransaction> accountTransactions = account.getTransactions().maxItems(10).get();
        for (AccountTransaction transaction : accountTransactions) {
            System.out.println(transaction);
        }
    }
}
