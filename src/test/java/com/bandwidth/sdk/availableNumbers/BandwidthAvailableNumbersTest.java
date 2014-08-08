package com.bandwidth.sdk.availableNumbers;

import com.bandwidth.sdk.BandwidthRestClient;
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

public class BandwidthAvailableNumbersTest {

    private MockRestDriver mockRestDriver;
    private BandwidthAvailableNumbers availableNumbers;

    @Before
    public void setUp() throws Exception {
        mockRestDriver = new MockRestDriver();

        BandwidthRestClient client = new BandwidthRestClient(null, null, null);
        client.setRestDriver(mockRestDriver);

        availableNumbers = new BandwidthAvailableNumbers(client);
    }

    @Test
    public void shouldGetLocalNumbers() throws ParseException, IOException {
        mockRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[{\"price\":\"0.00\",\"state\":\"CA\",\"number\":\"num1\",\"nationalNumber\":\"nationalNum1\",\"rateCenter\":\"STOCKTON\",\"city\":\"STOCKTON\"},{\"price\":\"0.00\",\"state\":\"CA\",\"number\":\"num2\",\"nationalNumber\":\"nationalNum2\",\"rateCenter\":\"STOCKTON\",\"city\":\"STOCKTON\"}]");

        List<BandwidthNumber> numbers = availableNumbers.getLocalNumbers().quantity(5).get();
        assertThat(numbers.size(), equalTo(2));
        assertThat(numbers.get(0).getNumber(), equalTo("num1"));
        assertThat(numbers.get(0).getNationalNumber(), equalTo("nationalNum1"));
        assertThat(numbers.get(1).getNumber(), equalTo("num2"));
        assertThat(numbers.get(1).getNationalNumber(), equalTo("nationalNum2"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("requestLocalAvailableNumbers"));
        assertThat(mockRestDriver.requests.get(0).params.get("quantity"), equalTo("5"));
    }
}