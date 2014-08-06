package com.bandwidth.sdk.account;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BandwidthAccountTest {

    private MockRestDriver stubRestDriver;
    private BandwidthAccount account;

    @Before
    public void setUp() {
        stubRestDriver = new MockRestDriver();

        BandwidthRestClient client = new BandwidthRestClient(null, null, null);
        client.setRestDriver(stubRestDriver);

        account = new BandwidthAccount(client);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnAccountInfo() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountType", "type");
        jsonObject.put("balance", "1000.68");

        stubRestDriver.result = jsonObject;

        AccountInfo accountInfo = account.getAccountInfo();
        assertThat(accountInfo.accountType, equalTo("type"));
        assertThat(accountInfo.balance, equalTo(1000.68));

        assertThat(stubRestDriver.requests.get(0).name, equalTo("requestAccountInfo"));
    }

}