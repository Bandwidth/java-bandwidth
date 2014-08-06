package com.bandwidth.sdk.account;

import com.bandwidth.sdk.BandwidthConstants;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vpotapenko
 */
public class AccountTransaction {

    public final String id;
    public final String type;
    public final Date dateTime;
    public final double amount;
    public final long units;
    public final String productType;
    public final String number;

    private AccountTransaction(String id, String type, Date dateTime, double amount, long units, String productType, String number) {
        this.id = id;
        this.type = type;
        this.dateTime = dateTime;
        this.amount = amount;
        this.units = units;
        this.productType = productType;
        this.number = number;
    }

    public static AccountTransaction from(JSONObject jsonObject) {
        String id = (String) jsonObject.get("id");
        String type = (String) jsonObject.get("type");
        String time = (String) jsonObject.get("time");
        String amount = (String) jsonObject.get("amount");
        Long units = (Long) jsonObject.get("units");
        String productType = (String) jsonObject.get("productType");
        String number = (String) jsonObject.get("number");

        Date dateTime;
        SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
        try {
            dateTime = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new AccountTransaction(id,
                type,
                dateTime,
                Double.parseDouble(amount),
                units,
                productType,
                number);
    }

    @Override
    public String toString() {
        return "AccountTransaction{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                ", units=" + units +
                ", productType='" + productType + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
