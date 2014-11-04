package com.bandwidth.sdk.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bandwidth.sdk.model.AvailableNumber;
import com.bandwidth.sdk.model.PhoneNumber;

public class NumberAllocationExample {

    public static void main(String[] args) throws Exception {
        String city = promptCitySelection();
        Integer quantity = promptQuantityInput();
        List<PhoneNumber> numbers = orderNumbers(city, quantity);

        if (numbers.size() > 0) {
            System.out.println("Ordered numbers:");
        }

        for (PhoneNumber number : numbers) {
            System.out.println(number.getNumber());
        }
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

    private static Integer promptQuantityInput() {
        System.out.println("How many phone numbers would you like to order?");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = null;

        try {
            input = br.readLine();
        } catch (IOException ex) {
            System.err.println("There was an error trying to read quantity.");
            System.exit(1);
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid quantity: " + input);
            return promptQuantityInput();
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

        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("city", city);
        searchParams.put("quantity", quantity);
        searchParams.put("state", state);

        List<AvailableNumber> availableNumbers = AvailableNumber.searchLocal(searchParams);
        List<PhoneNumber> orderedNumbers = new ArrayList<PhoneNumber>();

        for (AvailableNumber availableNumber : availableNumbers) {
            Map<String, Object> orderParams = new HashMap<String, Object>();
            orderParams.put("number", availableNumber.getNumber());

            orderedNumbers.add(PhoneNumber.create(orderParams));
        }

        return orderedNumbers;
    }

}
