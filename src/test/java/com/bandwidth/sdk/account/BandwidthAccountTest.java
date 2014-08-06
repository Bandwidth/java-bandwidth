package com.bandwidth.sdk.account;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnAccountTransactions() throws IOException, ParseException {
        stubRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[{\"id\":\"id1\",\"time\":\"2014-08-05T22:32:44Z\",\"amount\":\"0.00\",\"type\":\"charge\",\"units\":1,\"productType\":\"call-in\",\"number\":\"+number1\"},{\"id\":\"id2\",\"time\":\"2014-08-05T02:32:59Z\",\"amount\":\"0.00\",\"type\":\"charge\",\"units\":1,\"productType\":\"sms-in\",\"number\":\"+number2\"}]");

        List<AccountTransaction> transactions = account.getTransactions().get();
        assertThat(transactions.size(), equalTo(2));
        assertThat(transactions.get(0).id, equalTo("id1"));
        assertThat(transactions.get(0).number, equalTo("+number1"));
        assertThat(transactions.get(1).id, equalTo("id2"));
        assertThat(transactions.get(1).number, equalTo("+number2"));

        assertThat(stubRestDriver.requests.get(0).name, equalTo("requestAccountTransactions"));
    }

    @Test
    public void shouldPrepareTransactionsParameters() throws ParseException, IOException {
        stubRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[{\"id\":\"id1\",\"time\":\"2014-08-05T22:32:44Z\",\"amount\":\"0.00\",\"type\":\"charge\",\"units\":1,\"productType\":\"call-in\",\"number\":\"+number1\"},{\"id\":\"id2\",\"time\":\"2014-08-05T02:32:59Z\",\"amount\":\"0.00\",\"type\":\"charge\",\"units\":1,\"productType\":\"sms-in\",\"number\":\"+number2\"}]");

        Date fromDate = new Date();
        account.getTransactions().maxItems(1000).type("call-in").fromDate(fromDate).get();

        MockRestDriver.RestRequest restRequest = stubRestDriver.requests.get(0);
        assertThat(restRequest.name, equalTo("requestAccountTransactions"));
        assertThat(restRequest.params.get("maxItems"), equalTo("1000"));
        assertThat(restRequest.params.get("type"), equalTo("call-in"));
        assertThat(restRequest.params.get("fromDate"), equalTo(new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN).format(fromDate)));
    }

}