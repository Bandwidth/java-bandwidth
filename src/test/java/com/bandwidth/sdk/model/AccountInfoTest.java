package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AccountInfoTest {

    @SuppressWarnings("unchecked")
    @Test
    public void shouldBeCreatedFromJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountType", "accType");
        jsonObject.put("balance", "100");

        AccountInfo bandwidthAccountInfo = new AccountInfo(null, "parentUri", jsonObject);
        assertThat(bandwidthAccountInfo.getAccountType(), equalTo("accType"));
        assertThat(bandwidthAccountInfo.getBalance(), equalTo(100d));
    }

}