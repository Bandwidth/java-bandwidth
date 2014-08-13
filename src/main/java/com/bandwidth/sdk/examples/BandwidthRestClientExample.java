package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.account.BandwidthAccount;
import com.bandwidth.sdk.account.BandwidthAccountTransaction;
import com.bandwidth.sdk.applications.BandwidthApplication;
import com.bandwidth.sdk.applications.BandwidthApplications;
import com.bandwidth.sdk.availableNumbers.BandwidthAvailableNumbers;
import com.bandwidth.sdk.availableNumbers.BandwidthNumber;
import com.bandwidth.sdk.bridges.BandwidthBridge;
import com.bandwidth.sdk.bridges.BandwidthBridges;
import com.bandwidth.sdk.calls.BandwidthCall;
import com.bandwidth.sdk.calls.BandwidthCalls;

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
        printAvailableNumbers(client);
        printBridges(client);
        printCalls(client);
    }

    private static void printCalls(BandwidthRestClient client) throws IOException {
        System.out.println("\nCalls:");
        BandwidthCalls calls = client.getCalls();
        List<BandwidthCall> callList = calls.getCalls().size(10).get();
        for (BandwidthCall call : callList) {
            System.out.println(call);
        }

        if (!callList.isEmpty()) {
            BandwidthCall call = calls.getCallById(callList.get(0).getId());
            System.out.println("\nCall by Id");
            System.out.println(call);
        }
    }

    private static void printBridges(BandwidthRestClient client) throws IOException {
        System.out.println("\nBridges:");
        BandwidthBridges bridges = client.getBridges();
        List<BandwidthBridge> bridgeList = bridges.getBridges();
        for (BandwidthBridge bridge : bridgeList) {
            System.out.println(bridge);
        }

        if (bridgeList.size() > 0) {
            BandwidthBridge bridge = bridges.getBridgeById(bridgeList.get(0).getId());
            System.out.println("\nBridge by Id");
            System.out.println(bridge);

            System.out.println("\nCalls of Bridge");
            List<BandwidthCall> bridgeCalls = bridge.getBridgeCalls();
            for (BandwidthCall call : bridgeCalls) {
                System.out.println(call);
            }
        }
    }

    private static void printAvailableNumbers(BandwidthRestClient client) throws IOException {
        System.out.println("\nAvailableNumbers:");
        BandwidthAvailableNumbers availableNumbers = client.getAvailableNumbers();

        System.out.println("Local:");
        List<BandwidthNumber> numbers = availableNumbers.getLocalNumbers().state("CA").quantity(2).get();
        for (BandwidthNumber number : numbers) {
            System.out.println(number);
        }

        System.out.println("\nTollFree:");
        numbers = availableNumbers.getTollFreeNumbers().quantity(2).get();
        for (BandwidthNumber number : numbers) {
            System.out.println(number);
        }
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
