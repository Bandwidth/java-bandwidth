package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;

/**
 * @author vpotapenko
 */
public class BandwidthAccountInfo {

    private String accountType;
    private double balance;

    private BandwidthAccountInfo() {
    }

    public static BandwidthAccountInfo from(JSONObject jsonObject) {
        BandwidthAccountInfo accountInfo = new BandwidthAccountInfo();
        accountInfo.accountType = (String) jsonObject.get("accountType");

        String balance = (String) jsonObject.get("balance");
        accountInfo.balance = Double.parseDouble(balance);

        return accountInfo;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "accountType='" + accountType + '\'' +
                ", balance=" + balance +
                '}';
    }
}
