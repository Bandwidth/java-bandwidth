package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import com.bandwidth.sdk.TestsHelper;
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

}