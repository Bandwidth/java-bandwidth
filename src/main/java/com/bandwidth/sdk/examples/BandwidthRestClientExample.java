package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.model.*;
import com.bandwidth.sdk.model.Number;

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
        Calls calls = client.getCalls();
        List<Call> callList = calls.queryCallsBuilder().size(10).list();
        for (Call call : callList) {
            System.out.println(call);
        }

        if (!callList.isEmpty()) {
            Call call = calls.getCallById(callList.get(0).getId());
            System.out.println("\nCall by Id");
            System.out.println(call);

            System.out.println("\nCall events");
            List<Event> eventsList = call.getEventsList();
            for (Event event : eventsList) {
                System.out.println(event);
            }

            if (!eventsList.isEmpty()) {
                Event event = call.getEventById(eventsList.get(0).getId());
                System.out.println("\nCall event by Id");
                System.out.println(event);
            }

            List<Recording> recordings = call.getRecordings();
            System.out.println("\nCall recordings");
            for (Recording recording : recordings) {
                System.out.println(recording);
            }
        }
    }

    private static void printBridges(BandwidthRestClient client) throws IOException {
        System.out.println("\nBridges:");
        Bridges bridges = client.getBridges();
        List<Bridge> bridgeList = bridges.getBridges();
        for (Bridge bridge : bridgeList) {
            System.out.println(bridge);
        }

        if (bridgeList.size() > 0) {
            Bridge bridge = bridges.getBridgeById(bridgeList.get(0).getId());
            System.out.println("\nBridge by Id");
            System.out.println(bridge);

            System.out.println("\nCalls of Bridge");
            List<Call> bridgeCalls = bridge.getBridgeCalls();
            for (Call call : bridgeCalls) {
                System.out.println(call);
            }
        }
    }

    private static void printAvailableNumbers(BandwidthRestClient client) throws IOException {
        System.out.println("\nAvailableNumbers:");
        AvailableNumbers availableNumbers = client.getAvailableNumbers();

        System.out.println("Local:");
        List<Number> numbers = availableNumbers.queryLocalNumbersBuilder().state("CA").quantity(2).list();
        for (Number number : numbers) {
            System.out.println(number);
        }

        System.out.println("\nTollFree:");
        numbers = availableNumbers.queryTollFreeNumbersBuilder().quantity(2).list();
        for (Number number : numbers) {
            System.out.println(number);
        }
    }

    private static void printApplications(BandwidthRestClient client) throws IOException {
        System.out.println("\nApplications:");
        Applications applications = client.getApplications();
        List<Application> applicationList = applications.queryApplicationsBuilder().list();
        for (Application application : applicationList) {
            System.out.println(application);
        }

        System.out.println("\nApplication by id:");
        String id = applicationList.get(0).getId();
        Application application = applications.getApplicationById(id);
        System.out.println(application);
    }

    private static void printAccount(BandwidthRestClient client) throws IOException {
        Account account = client.getAccount();
        System.out.println(account.getAccountInfo());

        System.out.println("\nTransactions:");
        List<AccountTransaction> accountTransactions = account.queryTransactionsBuilder().maxItems(10).list();
        for (AccountTransaction transaction : accountTransactions) {
            System.out.println(transaction);
        }
    }
}
