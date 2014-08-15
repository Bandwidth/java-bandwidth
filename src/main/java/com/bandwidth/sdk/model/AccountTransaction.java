package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vpotapenko
 */
public class AccountTransaction {

    private String id;
    private String type;
    private Date dateTime;
    private double amount;
    private long units;
    private String productType;
    private String number;

    private AccountTransaction() {
    }

    public static AccountTransaction from(JSONObject jsonObject) {
        AccountTransaction transaction = new AccountTransaction();

        transaction.id = (String) jsonObject.get("id");
        transaction.type = (String) jsonObject.get("type");
        transaction.units = (Long) jsonObject.get("units");
        transaction.productType = (String) jsonObject.get("productType");
        transaction.number = (String) jsonObject.get("number");

        String amount = (String) jsonObject.get("amount");
        transaction.amount = Double.parseDouble(amount);

        String time = (String) jsonObject.get("time");
        SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
        try {
            transaction.dateTime = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return transaction;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public long getUnits() {
        return units;
    }

    public String getProductType() {
        return productType;
    }

    public String getNumber() {
        return number;
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
