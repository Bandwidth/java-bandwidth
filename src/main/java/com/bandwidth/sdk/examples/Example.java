package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.model.*;
import com.bandwidth.sdk.model.Error;

import java.io.IOException;
import java.util.List;

/**
 * @author vpotapenko
 */
public class Example {

    public static void main(String[] args) throws IOException {
        BandwidthRestClient client = new BandwidthRestClient("<userId>", "<token>", "<secret>"); // todo

        printAccount(client);
        printApplications(client);
        printAvailableNumbers(client);
        printBridges(client);
        printCalls(client);
        printErrors(client);
        printMessages(client);
        printPhoneNumbers(client);
        printRecordings(client);
        printMedia(client);
    }

    private static void printMedia(BandwidthRestClient client) throws IOException {
        System.out.println("\nMedia:");
        Media media = client.getMedia();
        List<MediaFile> list = media.getMediaFiles();
        for (MediaFile mediaFile : list) {
            System.out.println(mediaFile);
        }
    }

    private static void printRecordings(BandwidthRestClient client) throws IOException {
        System.out.println("\nRecordings:");
        Recordings recordings = client.getRecordings();
        List<Recording> list = recordings.queryRecordingsBuilder().size(5).list();
        for (Recording recording : list) {
            System.out.println(recording);
        }

        if (!list.isEmpty()) {
            Recording recording = recordings.getRecordingById(list.get(0).getId());
            System.out.println("\nRecording by Id");
            System.out.println(recording);
        }

    }

    private static void printPhoneNumbers(BandwidthRestClient client) throws IOException {
        System.out.println("\nPhoneNumbers:");
        PhoneNumbers phoneNumbers = client.getPhoneNumbers();
        List<PhoneNumber> list = phoneNumbers.queryNumbersBuilder().size(5).list();
        for (PhoneNumber number : list) {
            System.out.println(number);
        }

        if (!list.isEmpty()) {
            PhoneNumber phoneNumber = phoneNumbers.getNumberById(list.get(0).getId());
            System.out.println("\nPhone Number by Id");
            System.out.println(phoneNumber);

            phoneNumber = phoneNumbers.getNumberByNumber(list.get(0).getNumber());
            System.out.println("\nPhone Number by number");
            System.out.println(phoneNumber);
        }
    }

    private static void printMessages(BandwidthRestClient client) throws IOException {
        System.out.println("\nMessages:");
        Messages messages = client.getMessages();
        List<Message> list = messages.queryMessagesBuilder().size(5).list();
        for (Message message : list) {
            System.out.println(message);
        }

        if (!list.isEmpty()) {
            Message message = messages.getMessageById(list.get(0).getId());
            System.out.println("\nMessage by Id");
            System.out.println(message);
        }
    }

    private static void printErrors(BandwidthRestClient client) throws IOException {
        System.out.println("\nErrors:");
        Errors errors = client.getErrors();
        List<Error> errorList = errors.getErrors();
        for (Error error : errorList) {
            System.out.println(error);
        }

        if (!errorList.isEmpty()) {
            Error error = errors.getErrorById(errorList.get(0).getId());
            System.out.println("\nError by Id");
            System.out.println(error);
        }
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
        List<AvailableNumber> numbers = availableNumbers.queryLocalNumbersBuilder().state("CA").quantity(2).list();
        for (AvailableNumber number : numbers) {
            System.out.println(number);
        }

        System.out.println("\nTollFree:");
        numbers = availableNumbers.queryTollFreeNumbersBuilder().quantity(2).list();
        for (AvailableNumber number : numbers) {
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
