package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BandwidthNumberTest {

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"price\":\"0.00\",\"state\":\"CA\",\"number\":\"num\",\"nationalNumber\":\"nationalNum\",\"rateCenter\":\"rCenter\",\"city\":\"ci\"}");
        BandwidthNumber number = BandwidthNumber.from(jsonObject);

        assertThat(number.getCity(), equalTo("ci"));
        assertThat(number.getState(), equalTo("CA"));
        assertThat(number.getNumber(), equalTo("num"));
        assertThat(number.getNationalNumber(), equalTo("nationalNum"));
        assertThat(number.getRateCenter(), equalTo("rCenter"));
    }

}