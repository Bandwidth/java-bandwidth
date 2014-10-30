package com.bandwidth.sdk.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.bandwidth.sdk.model.AvailableNumber;
import com.bandwidth.sdk.model.PhoneNumber;

public class CommandLineExample {

    public static void main(String[] args) throws Exception {
    	String city = promptCitySelection();
    	List<PhoneNumber> numbers = orderNumbers(city, 1);
    }
    
    private static String promptCitySelection() {
    	System.out.println("Would you like to search for phone numbers in Denver or Raleigh");
    	System.out.println("Type the city name you would like and press enter.");
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	
    	String city = null;
    	
    	try {
    		city = br.readLine();
    	} catch (IOException ex) {
    		System.err.println("There was an error trying to read city.");
    		System.exit(1);
    	}
    	
    	if (city.equals("Denver") || city.equals("Raleigh")) {
    		return city;
    	} else {
    		return promptCitySelection();
    	}
    }
    
    public static List<PhoneNumber> orderNumbers(String city, Integer quantity) throws Exception {
    	String state = null;
    	if (city.equals("Denver")) {
    		state = "CO";
    	} else if (city.equals("Raleigh")) {
    		state = "NC";
    	} else {
    		System.err.println("Invalid city: " + city);
    		System.exit(1);
    	}

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("city", city);
        params.put("state", state);
        
        List<AvailableNumber> availableNumbers = AvailableNumber.searchLocal(params);
        
        List<PhoneNumber> orderedNumbers = new LinkedList<PhoneNumber>();

        for (AvailableNumber number : availableNumbers) {
        	if (quantity == 0) {
        		break;
        	}

            Map<String, Object> orderParams = new HashMap<String, Object>();
            orderParams.put("number", number.getNumber());

        	PhoneNumber orderedNumber = PhoneNumber.create(orderParams);

    		orderedNumbers.add(orderedNumber);
    		quantity = quantity - 1;
        }
        
        return orderedNumbers;
    }

}
