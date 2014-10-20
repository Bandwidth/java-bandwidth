package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.model.*;
import com.bandwidth.sdk.model.Error;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * This example shows how to pull resource lists using Bandwidth Java SDK API and access individual objects 
 *
 * @author vpotapenko
 */
public class ResourceListExample {

    public static void main(String[] args) throws Exception {
    	
        // be sure to set your Application Platform userId, api token and api secret 
    	// as environment variables, e.g. 
    	// BANDWIDTH_APPPLATFORM_USER_ID
    	// BANDWIDTH_APPPLATFORM_API_TOKEN
    	// BANDWIDTH_APPPLATFORM_API_SECRET

        //printAccount();
    	
        printPhoneNumbers();
        
        printAvailableNumbers(); 
    	        
        printApplications();

        printBridges();
        
        printCalls();

        printRecordings(); 
        
        printErrors();
        
        printMessages();
                
        printMedia(); 
               
       
    }
    
    private static void printApplications() throws IOException {
        System.out.println("\nApplications:");
        
        ResourceList<Application> applicationList = Application.list(0, 9); 
        for (Application application : applicationList) {
            System.out.println(application);
        }

    }
    
    private static void printPhoneNumbers() throws Exception {
        System.out.println("\nPhoneNumbers:");
        
        ResourceList<PhoneNumber> phoneNumbers = PhoneNumber.list(0,4);
        for (PhoneNumber number : phoneNumbers) {
            System.out.println(number);
        }

    }
    
    private static void printBridges() throws Exception {
        System.out.println("\nBridges:");
        ResourceList<Bridge> bridgeList = Bridge.list();
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
    
    private static void printErrors() throws Exception {
        System.out.println("\nErrors:");
        ResourceList<Error> errorList = Error.list();
        for (Error error : errorList) {
            System.out.println(error);
        }

     }



    private static void printMedia() throws IOException {
        System.out.println("\nMedia:");
        ResourceList<MediaFile> mediaList = Media.list();
        for (MediaFile mediaFile : mediaList) {
            System.out.println(mediaFile);
        }
    }

    private static void printRecordings() throws Exception {
        System.out.println("\nRecordings:");
        List<Recording> list = Recording.list(0, 5);
        for (Recording recording : list) {
            System.out.println(recording);
        }

        if (!list.isEmpty()) {
            Recording recording = Recording.get(list.get(0).getId());
            System.out.println("\nRecording by Id");
            System.out.println(recording);
        }

    }


    private static void printMessages() throws Exception {
        System.out.println("\nMessages:");
        List<Message> list = Message.list(0, 20);
        for (Message message : list) {
            System.out.println(message);
        }

        if (!list.isEmpty()) {
            Message message = Message.get(list.get(0).getId());
            System.out.println("\nMessage by Id");
            System.out.println(message);
        }
    }

    private static void printErrors(BandwidthRestClient client) throws Exception {
        System.out.println("\nErrors:");

        List<Error> errorList = Error.list();
        for (Error error : errorList) {
            System.out.println(error);
        }

        if (!errorList.isEmpty()) {
            Error error = Error.get(errorList.get(0).getId());
            System.out.println("\nError by Id");
            System.out.println(error);
        }
    }

    private static void printCalls() throws Exception {
        System.out.println("\nCalls:");
        List<Call> callList = Call.list(0, 30);
        for (Call call : callList) {
            System.out.println(call);
        }

        if (!callList.isEmpty()) {
            Call call = Call.get(callList.get(0).getId());
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


    private static void printAvailableNumbers() throws Exception {
        System.out.println("\nAvailableNumbers:");

        System.out.println("LocalNumbers:");
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "NC");
        params.put("quantity", "2");
        
        List<AvailableNumber> numbers = AvailableNumber.searchLocal(params);
        for (AvailableNumber number : numbers) {
            System.out.println(number);
        }

        params.clear();
        params.put("quantity", "2");
        System.out.println("\nTollFree:");
        numbers = AvailableNumber.searchTollFree(params);
        for (AvailableNumber number : numbers) {
            System.out.println(number);
        }
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
