package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AvailableNumbersTest {

    private MockRestDriver mockRestDriver;
    private AvailableNumbers availableNumbers;

    @Before
    public void setUp() throws Exception {
        mockRestDriver = new MockRestDriver();

        availableNumbers = new AvailableNumbers(mockRestDriver);
    }

    @Test
    public void shouldGetLocalNumbers() throws ParseException, IOException {
        mockRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[{\"price\":\"0.00\",\"state\":\"CA\",\"number\":\"num1\",\"nationalNumber\":\"nationalNum1\",\"rateCenter\":\"STOCKTON\",\"city\":\"STOCKTON\"},{\"price\":\"0.00\",\"state\":\"CA\",\"number\":\"num2\",\"nationalNumber\":\"nationalNum2\",\"rateCenter\":\"STOCKTON\",\"city\":\"STOCKTON\"}]");

        List<Number> numbers = availableNumbers.queryLocalNumbersBuilder().quantity(5).list();
        assertThat(numbers.size(), equalTo(2));
        assertThat(numbers.get(0).getNumber(), equalTo("num1"));
        assertThat(numbers.get(0).getNationalNumber(), equalTo("nationalNum1"));
        assertThat(numbers.get(1).getNumber(), equalTo("num2"));
        assertThat(numbers.get(1).getNationalNumber(), equalTo("nationalNum2"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("requestLocalAvailableNumbers"));
    }

    @Test
    public void shouldGetTollFreeNumbers() throws ParseException, IOException {
        mockRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[{\"price\":\"0.00\",\"number\":\"n1\",\"nationalNumber\":\"nn1\"},{\"price\":\"0.00\",\"number\":\"n2\",\"nationalNumber\":\"nn2\"}]");

        List<Number> numbers = availableNumbers.queryTollFreeNumbersBuilder().quantity(5).list();
        assertThat(numbers.size(), equalTo(2));
        assertThat(numbers.get(0).getNumber(), equalTo("n1"));
        assertThat(numbers.get(0).getNationalNumber(), equalTo("nn1"));
        assertThat(numbers.get(1).getNumber(), equalTo("n2"));
        assertThat(numbers.get(1).getNationalNumber(), equalTo("nn2"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("requestTollFreeAvailableNumbers"));
    }
}