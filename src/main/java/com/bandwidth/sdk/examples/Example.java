package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.model.*;
import com.bandwidth.sdk.model.Error;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Example using Bandwidth Java SDK API.
 *
 * @author vpotapenko
 */
public class Example {

    public static void main(String[] args) throws IOException {
    	
        // be sure to set your Application Platform userId, api token and api secret 
    	// as environment variables, e.g. 
    	// BANDWIDTH_APPPLATFORM_USER_ID
    	// BANDWIDTH_APPPLATFORM_API_TOKEN
    	// BANDWIDTH_APPPLATFORM_API_SECRET

        //printAccount();
        
        printApplications();
        
       /* printPhoneNumbers();
        
        printBridges();

        printErrors();
        
        printMedia(); 
        
        printMessages();

        printCalls();
        
        printAvailableNumbers();
        
        printRecordings(); 
        */
    }
    
    private static void printApplications() throws IOException {
        System.out.println("\nApplications:");
        
        ResourceList<Application> applicationList = Application.list(0, 9); 
        for (Application application : applicationList) {
            System.out.println(application);
        }

    }
    
    private static void printPhoneNumbers() throws IOException {
        System.out.println("\nPhoneNumbers:");
        
        ResourceList<PhoneNumber> phoneNumbers = PhoneNumber.getPhoneNumbers(0,4);
        for (PhoneNumber number : phoneNumbers) {
            System.out.println(number);
        }

    }
    
    private static void printBridges() throws IOException {
        System.out.println("\nBridges:");
        ResourceList<Bridge> bridgeList = Bridge.getBridges();
        for (Bridge bridge : bridgeList) {
            System.out.println(bridge);
        }
        
        System.out.println("\nCalls of Bridge");
        Bridge bridge = bridgeList.get(0);
        List<Call> bridgeCalls = bridge.getBridgeCalls();
        for (Call call : bridgeCalls) {
            System.out.println(call);
        }
    }
    
    private static void printErrors() throws IOException {
        System.out.println("\nErrors:");
        ResourceList<Error> errorList = Error.getErrors();
        for (Error error : errorList) {
            System.out.println(error);
        }

     }



    private static void printMedia() throws IOException {
        System.out.println("\nMedia:");
        ResourceList<MediaFile> mediaList = Media.getMediaFiles();
        for (MediaFile mediaFile : mediaList) {
            System.out.println(mediaFile);
        }
    }

    private static void printRecordings() throws IOException {
        System.out.println("\nRecordings:");
        List<Recording> list = Recording.getRecordings(0, 5);
        for (Recording recording : list) {
            System.out.println(recording);
        }

        if (!list.isEmpty()) {
            Recording recording = Recording.getRecording(list.get(0).getId());
            System.out.println("\nRecording by Id");
            System.out.println(recording);
        }

    }


    private static void printMessages() throws IOException {
        System.out.println("\nMessages:");
        List<Message> list = Message.getMessages(0, 20);
        for (Message message : list) {
            System.out.println(message);
        }

        if (!list.isEmpty()) {
            Message message = Message.getMessage(list.get(0).getId());
            System.out.println("\nMessage by Id");
            System.out.println(message);
        }
    }

    private static void printErrors(BandwidthRestClient client) throws IOException {
        System.out.println("\nErrors:");

        List<Error> errorList = Error.getErrors();
        for (Error error : errorList) {
            System.out.println(error);
        }

        if (!errorList.isEmpty()) {
            Error error = Error.getError(errorList.get(0).getId());
            System.out.println("\nError by Id");
            System.out.println(error);
        }
    }

    private static void printCalls() throws IOException {
        System.out.println("\nCalls:");
        List<Call> callList = Call.getCalls(0, 30);
        for (Call call : callList) {
            System.out.println(call);
        }

        if (!callList.isEmpty()) {
            Call call = Call.getCall(callList.get(0).getId());
            System.out.println("\nCall by Id");
            System.out.println(call);

            System.out.println("\nCall events");
            List<EventBase> eventsList = call.getEventsList();
            for (EventBase event : eventsList) {
                System.out.println(event);
            }

            if (!eventsList.isEmpty()) {
                EventBase event = call.getEvent(eventsList.get(0).getId());
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


    private static void printAvailableNumbers() throws IOException {
        System.out.println("\nAvailableNumbers:");

        System.out.println("LocalNumbers:");
        
        HashMap<Object, String> params = new HashMap();
        params.put("state", "NC");
        
        List<AvailableNumber> numbers = AvailableNumber.getAvailableNumbers();
        for (AvailableNumber number : numbers) {
            System.out.println(number);
        }

       /* System.out.println("\nTollFree:");
        numbers = availableNumbers.queryTollFreeNumbersBuilder().quantity(2).list();
        for (AvailableNumber number : numbers) {
            System.out.println(number);
        }*/
    }


    private static void printAccount() throws IOException {
        Account account = Account.getAccount();
        System.out.println(account.getAccountInfo());

        System.out.println("\nTransactions:");
        List<AccountTransaction> accountTransactions = account.queryTransactionsBuilder().maxItems(10).list();
        for (AccountTransaction transaction : accountTransactions) {
            System.out.println(transaction);
        }
    }
}
