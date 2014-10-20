package com.bandwidth.sdk.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.RestResponse;

import com.bandwidth.sdk.TestsHelper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AvailableNumberTest extends BaseModelTest {

    private MockClient mockClient;

    @Before
    public void setUp(){
    	super.setUp();
        mockClient = new MockClient();
    }

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"price\":\"0.00\",\"state\":\"CA\",\"number\":\"num\",\"nationalNumber\":\"nationalNum\",\"rateCenter\":\"rCenter\",\"city\":\"ci\"}");
        AvailableNumber number = new AvailableNumber(mockClient, jsonObject);

        assertThat(number.getCity(), equalTo("ci"));
        assertThat(number.getState(), equalTo("CA"));
        assertThat(number.getNumber(), equalTo("num"));
        assertThat(number.getNationalNumber(), equalTo("nationalNum"));
        assertThat(number.getRateCenter(), equalTo("rCenter"));
    }
    
    @Test
    public void shouldGetLocalNumbers() throws ParseException, Exception {
        JSONArray jsonArray = (JSONArray) new JSONParser().parse
        		("[{\"price\":\"0.00\",\"state\":\"CA\",\"number\":\"num1\",\"nationalNumber\":\"nationalNum1\",\"rateCenter\":\"STOCKTON\",\"city\":\"STOCKTON\"},{\"price\":\"0.00\",\"state\":\"CA\",\"number\":\"num2\",\"nationalNumber\":\"nationalNum2\",\"rateCenter\":\"STOCKTON\",\"city\":\"STOCKTON\"}]");

        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText(jsonArray.toString());

        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        Map<String, Object> params = new HashMap <String, Object>();
        params.put("state", "NC");
        params.put("quantity", "2");

        
        List<AvailableNumber>numbers = AvailableNumber.searchLocal(mockClient, params);
        assertThat(numbers.size(), equalTo(2));
        assertThat(numbers.get(0).getNumber(), equalTo("num1"));
        assertThat(numbers.get(0).getNationalNumber(), equalTo("nationalNum1"));
        assertThat(numbers.get(1).getNumber(), equalTo("num2"));
        assertThat(numbers.get(1).getNationalNumber(), equalTo("nationalNum2"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("availableNumbers/local"));
        assertThat(mockClient.requests.get(0).params.get("quantity").toString(), equalTo("2"));
    }

    @Test
    public void shouldGetTollFreeNumbers() throws ParseException, Exception {
        JSONArray jsonArray = (JSONArray) new JSONParser().parse("[{\"price\":\"0.00\",\"number\":\"n1\",\"nationalNumber\":\"nn1\"},{\"price\":\"0.00\",\"number\":\"n2\",\"nationalNumber\":\"nn2\"}]");

        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText(jsonArray.toString());

        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);

        Map<String, Object> params = new HashMap <String, Object>();
        params.put("state", "CA");
        params.put("quantity", "2");
        
        //List<AvailableNumber> numbers = availableNumbers.queryTollFreeNumbersBuilder().quantity(5).list();
        
        List<AvailableNumber> numbers = AvailableNumber.searchTollFree(mockClient, params);
        assertThat(numbers.size(), equalTo(2));
        assertThat(numbers.get(0).getNumber(), equalTo("n1"));
        assertThat(numbers.get(0).getNationalNumber(), equalTo("nn1"));
        assertThat(numbers.get(1).getNumber(), equalTo("n2"));
        assertThat(numbers.get(1).getNationalNumber(), equalTo("nn2"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("availableNumbers/tollFree"));
        assertThat(mockClient.requests.get(0).params.get("quantity").toString(), equalTo("2"));
    }

}