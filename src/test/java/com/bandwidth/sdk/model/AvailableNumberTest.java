package com.bandwidth.sdk.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.bandwidth.sdk.MockRestClient;
import com.bandwidth.sdk.TestsHelper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AvailableNumberTest {

    private MockRestClient mockRestClient;

    @Before
    public void setUp() {
        mockRestClient = TestsHelper.getClient();
    }

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"price\":\"0.00\",\"state\":\"CA\",\"number\":\"num\",\"nationalNumber\":\"nationalNum\",\"rateCenter\":\"rCenter\",\"city\":\"ci\"}");
        AvailableNumber number = new AvailableNumber(mockRestClient, jsonObject);

        assertThat(number.getCity(), equalTo("ci"));
        assertThat(number.getState(), equalTo("CA"));
        assertThat(number.getNumber(), equalTo("num"));
        assertThat(number.getNationalNumber(), equalTo("nationalNum"));
        assertThat(number.getRateCenter(), equalTo("rCenter"));
    }
    
    @Test
    public void shouldGetLocalNumbers() throws ParseException, IOException {
        mockRestClient.arrayResult = (JSONArray) new JSONParser().parse
        		("[{\"price\":\"0.00\",\"state\":\"CA\",\"number\":\"num1\",\"nationalNumber\":\"nationalNum1\",\"rateCenter\":\"STOCKTON\",\"city\":\"STOCKTON\"},{\"price\":\"0.00\",\"state\":\"CA\",\"number\":\"num2\",\"nationalNumber\":\"nationalNum2\",\"rateCenter\":\"STOCKTON\",\"city\":\"STOCKTON\"}]");

        //List<AvailableNumber> numbers = availableNumbers.queryLocalNumbersBuilder().quantity(5).list();
        
        Map<String, Object> params = new HashMap <String, Object>();
        params.put("state", "CA");
        params.put("quantity", "2");

        
        List<AvailableNumber>numbers = AvailableNumber.searchLocal(mockRestClient, params);
        assertThat(numbers.size(), equalTo(2));
        assertThat(numbers.get(0).getNumber(), equalTo("num1"));
        assertThat(numbers.get(0).getNationalNumber(), equalTo("nationalNum1"));
        assertThat(numbers.get(1).getNumber(), equalTo("num2"));
        assertThat(numbers.get(1).getNationalNumber(), equalTo("nationalNum2"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/availableNumbers/local"));
        assertThat(mockRestClient.requests.get(0).params.get("quantity").toString(), equalTo("2"));
    }

    @Test
    public void shouldGetTollFreeNumbers() throws ParseException, IOException {
        mockRestClient.arrayResult = (JSONArray) new JSONParser().parse("[{\"price\":\"0.00\",\"number\":\"n1\",\"nationalNumber\":\"nn1\"},{\"price\":\"0.00\",\"number\":\"n2\",\"nationalNumber\":\"nn2\"}]");

        Map<String, Object> params = new HashMap <String, Object>();
        params.put("state", "CA");
        params.put("quantity", "2");
        
        //List<AvailableNumber> numbers = availableNumbers.queryTollFreeNumbersBuilder().quantity(5).list();
        
        List<AvailableNumber> numbers = AvailableNumber.searchTollFree(mockRestClient, params);
        assertThat(numbers.size(), equalTo(2));
        assertThat(numbers.get(0).getNumber(), equalTo("n1"));
        assertThat(numbers.get(0).getNationalNumber(), equalTo("nn1"));
        assertThat(numbers.get(1).getNumber(), equalTo("n2"));
        assertThat(numbers.get(1).getNationalNumber(), equalTo("nn2"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/availableNumbers/tollFree"));
        assertThat(mockRestClient.requests.get(0).params.get("quantity").toString(), equalTo("2"));
    }

}