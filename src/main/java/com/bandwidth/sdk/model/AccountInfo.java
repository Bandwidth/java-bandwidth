package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;

/**
 * @author vpotapenko
 */
public class AccountInfo {

    private String accountType;
    private double balance;

    private AccountInfo() {
    }

    public static AccountInfo from(JSONObject jsonObject) {
        AccountInfo accountInfo = new AccountInfo();
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
