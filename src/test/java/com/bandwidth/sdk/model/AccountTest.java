package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import com.bandwidth.sdk.TestsHelper;
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

public class AccountTest extends BaseModelTest {

    private Account account;

    @Before
    public void setUp() {
        super.setUp();
        account = new Account(mockRestClient);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnAccountInfo() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountType", "type");
        jsonObject.put("balance", "1000.68");

        mockRestClient.result = jsonObject;

        AccountInfo bandwidthAccountInfo = account.getAccountInfo();
        assertThat(bandwidthAccountInfo.getAccountType(), equalTo("type"));
        assertThat(bandwidthAccountInfo.getBalance(), equalTo(1000.68));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/account"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnAccountTransactions() throws IOException, ParseException {
        mockRestClient.arrayResult = (JSONArray) new JSONParser().parse("[{\"id\":\"id1\",\"time\":\"2014-08-05T22:32:44Z\",\"amount\":\"0.00\",\"type\":\"charge\",\"units\":1,\"productType\":\"call-in\",\"number\":\"+number1\"},{\"id\":\"id2\",\"time\":\"2014-08-05T02:32:59Z\",\"amount\":\"0.00\",\"type\":\"charge\",\"units\":1,\"productType\":\"sms-in\",\"number\":\"+number2\"}]");

        List<AccountTransaction> transactions = account.queryTransactionsBuilder().maxItems(10).list();
        assertThat(transactions.size(), equalTo(2));
        assertThat(transactions.get(0).getId(), equalTo("id1"));
        assertThat(transactions.get(0).getNumber(), equalTo("+number1"));
        assertThat(transactions.get(1).getId(), equalTo("id2"));
        assertThat(transactions.get(1).getNumber(), equalTo("+number2"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/account/transactions"));
        assertThat((Integer) mockRestClient.requests.get(0).params.get("maxItems"), equalTo(10));
    }

}