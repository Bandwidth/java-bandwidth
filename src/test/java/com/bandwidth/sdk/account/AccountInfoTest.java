package com.bandwidth.sdk.account;

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

        AccountInfo accountInfo = AccountInfo.from(jsonObject);
        assertThat(accountInfo.accountType, equalTo("accType"));
        assertThat(accountInfo.balance, equalTo(100d));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = NumberFormatException.class)
    public void shouldThrowExceptionOnInvalidBalanceValue() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountType", "accType");
        jsonObject.put("balance", "not valid double value");

        AccountInfo accountInfo = AccountInfo.from(jsonObject);
    }

}