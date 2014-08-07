package com.bandwidth.sdk.account;

import org.json.simple.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BandwidthAccountInfoTest {

    @SuppressWarnings("unchecked")
    @Test
    public void shouldBeCreatedFromJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountType", "accType");
        jsonObject.put("balance", "100");

        BandwidthAccountInfo bandwidthAccountInfo = BandwidthAccountInfo.from(jsonObject);
        assertThat(bandwidthAccountInfo.getAccountType(), equalTo("accType"));
        assertThat(bandwidthAccountInfo.getBalance(), equalTo(100d));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = NumberFormatException.class)
    public void shouldThrowExceptionOnInvalidBalanceValue() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountType", "accType");
        jsonObject.put("balance", "not valid double value");

        BandwidthAccountInfo.from(jsonObject);
    }

}