package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.model.*;
import com.bandwidth.sdk.model.Error;
import com.bandwidth.sdk.model.events.EventBase;

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

    /**
     * 
     * @param args the args.
     * @throws Exception error.
     */
    public static void main(final String[] args) throws Exception {
        // Be sure to set your credentials (see CredentialsExample for the different ways to do this)

        BandwidthClient.getInstance().setCredentials("your User Id here", "your API Token here", "your API Secret here");

        printAccount();
    	
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
        
        final ResourceList<Application> applicationList = Application.list(0, 9); 
        for (final Application application : applicationList) {
            System.out.println(application);
        }

    }
    
    private static void printPhoneNumbers() throws Exception {
        System.out.println("\nPhoneNumbers:");
        
        final ResourceList<PhoneNumber> phoneNumbers = PhoneNumber.list(0,4);
        for (final PhoneNumber number : phoneNumbers) {
            System.out.println(number);
        }

    }
    
    private static void printBridges() throws Exception {
        System.out.println("\nBridges:");
        final ResourceList<Bridge> bridgeList = Bridge.list();
        for (final Bridge bridge : bridgeList) {
            System.out.println(bridge);
        }
        
        System.out.println("\nCalls of Bridge");
        final Bridge bridge = bridgeList.get(0);
        final List<Call> bridgeCalls = bridge.getBridgeCalls();
        for (final Call call : bridgeCalls) {
            System.out.println(call);
        }
    }
    
    private static void printErrors() throws Exception {
        System.out.println("\nErrors:");
        final ResourceList<Error> errorList = Error.list();
        for (final Error error : errorList) {
            System.out.println(error);
        }

     }



    private static void printMedia() throws IOException {
        System.out.println("\nMedia:");
        final ResourceList<MediaFile> mediaList = Media.list();
        for (final MediaFile mediaFile : mediaList) {
            System.out.println(mediaFile);
        }
    }

    private static void printRecordings() throws Exception {
        System.out.println("\nRecordings:");
        final List<Recording> list = Recording.list(0, 5);
        for (final Recording recording : list) {
            System.out.println(recording);
        }

        if (!list.isEmpty()) {
            final Recording recording = Recording.get(list.get(0).getId());
            System.out.println("\nRecording by Id");
            System.out.println(recording);
        }

    }


    private static void printMessages() throws Exception {
        System.out.println("\nMessages:");
        final List<Message> list = Message.list(0, 20);
        for (final Message message : list) {
            System.out.println(message);
        }

        if (!list.isEmpty()) {
            final Message message = Message.get(list.get(0).getId());
            System.out.println("\nMessage by Id");
            System.out.println(message);
        }
    }

    private static void printCalls() throws Exception {
        System.out.println("\nCalls:");
        final List<Call> callList = Call.list(0, 30);
        for (final Call call : callList) {
            System.out.println(call);
        }

        if (!callList.isEmpty()) {
            final Call call = Call.get(callList.get(0).getId());
            System.out.println("\nCall by Id");
            System.out.println(call);

            System.out.println("\nCall events");
            final List<EventBase> eventsList = call.getEventsList();
            for (final EventBase event : eventsList) {
                System.out.println(event);
            }

            if (!eventsList.isEmpty()) {
                final EventBase event = call.getEvent(eventsList.get(0).getId());
                System.out.println("\nCall event by Id");
                System.out.println(event);
            }

            final List<Recording> recordings = call.getRecordings();
            System.out.println("\nCall recordings");
            for (final Recording recording : recordings) {
                System.out.println(recording);
            }
        }
    }


    private static void printAvailableNumbers() throws Exception {
        System.out.println("\nAvailableNumbers:");

        System.out.println("LocalNumbers:");
        
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "NC");
        params.put("quantity", "2");
        
        List<AvailableNumber> numbers = AvailableNumber.searchLocal(params);
        for (final AvailableNumber number : numbers) {
            System.out.println(number);
        }

        params.clear();
        params.put("quantity", "2");
        System.out.println("\nTollFree:");
        numbers = AvailableNumber.searchTollFree(params);
        for (final AvailableNumber number : numbers) {
            System.out.println(number);
        }
    }


    private static void printAccount() throws Exception {
        final Account account = Account.get();
        System.out.println(account.getAccountInfo());

        System.out.println("\nTransactions:");
        final List<AccountTransaction> accountTransactions = account.queryTransactionsBuilder().maxItems(10).list();
        for (final AccountTransaction transaction : accountTransactions) {
            System.out.println(transaction);
        }
    }
}
