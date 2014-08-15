package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AccountTransactionTest {

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"id\": \"81782\",\n" +
                "    \"time\": \"2013-02-21T13:39:09Z\",\n" +
                "    \"amount\": \"0.00750\",\n" +
                "    \"type\": \"charge\",\n" +
                "    \"units\": 1,\n" +
                "    \"productType\": \"sms-out\",\n" +
                "    \"number\": \"1672617-17281\"\n" +
                "  }");
        AccountTransaction transaction = AccountTransaction.from(jsonObject);

        assertThat(transaction.getId(), equalTo("81782"));
        assertThat(transaction.getAmount(), equalTo(0.0075));
        assertThat(transaction.getType(), equalTo("charge"));
        assertThat(transaction.getUnits(), equalTo(1l));
        assertThat(transaction.getProductType(), equalTo("sms-out"));
        assertThat(transaction.getNumber(), equalTo("1672617-17281"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(transaction.getDateTime());
        calendar.set(2013, Calendar.FEBRUARY, 21, 13, 39, 9);
        assertThat(calendar.get(Calendar.YEAR), equalTo(2013));
        assertThat(calendar.get(Calendar.MONTH), equalTo(Calendar.FEBRUARY));
        assertThat(calendar.get(Calendar.DAY_OF_MONTH), equalTo(21));
        assertThat(calendar.get(Calendar.HOUR_OF_DAY), equalTo(13));
        assertThat(calendar.get(Calendar.MINUTE), equalTo(39));
        assertThat(calendar.get(Calendar.SECOND), equalTo(9));
    }
}