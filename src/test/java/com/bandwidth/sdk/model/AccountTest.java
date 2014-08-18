package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AccountTest {

    private MockRestDriver mockRestDriver;
    private Account account;

    @Before
    public void setUp() {
        mockRestDriver = new MockRestDriver();
        account = new Account(mockRestDriver, "parentUri");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnAccountInfo() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountType", "type");
        jsonObject.put("balance", "1000.68");

        mockRestDriver.result = jsonObject;

        AccountInfo bandwidthAccountInfo = account.getAccountInfo();
        assertThat(bandwidthAccountInfo.getAccountType(), equalTo("type"));
        assertThat(bandwidthAccountInfo.getBalance(), equalTo(1000.68));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/account"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnAccountTransactions() throws IOException, ParseException {
        mockRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[{\"id\":\"id1\",\"time\":\"2014-08-05T22:32:44Z\",\"amount\":\"0.00\",\"type\":\"charge\",\"units\":1,\"productType\":\"call-in\",\"number\":\"+number1\"},{\"id\":\"id2\",\"time\":\"2014-08-05T02:32:59Z\",\"amount\":\"0.00\",\"type\":\"charge\",\"units\":1,\"productType\":\"sms-in\",\"number\":\"+number2\"}]");

        List<AccountTransaction> transactions = account.queryTransactionsBuilder().maxItems(10).list();
        assertThat(transactions.size(), equalTo(2));
        assertThat(transactions.get(0).getId(), equalTo("id1"));
        assertThat(transactions.get(0).getNumber(), equalTo("+number1"));
        assertThat(transactions.get(1).getId(), equalTo("id2"));
        assertThat(transactions.get(1).getNumber(), equalTo("+number2"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/account/transactions"));
        assertThat((Integer) mockRestDriver.requests.get(0).params.get("maxItems"), equalTo(10));
    }

}