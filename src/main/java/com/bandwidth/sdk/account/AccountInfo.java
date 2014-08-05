package com.bandwidth.sdk.account;

import org.json.simple.JSONObject;

/**
 * @author vpotapenko
 */
public class AccountInfo {

    public final String accountType;
    public final double balance;

    public AccountInfo(String accountType, double balance) {
        this.accountType = accountType;
        this.balance = balance;
    }

    public static AccountInfo from(JSONObject jsonObject) {
        String accountType = (String) jsonObject.get("accountType");
        String balance = (String) jsonObject.get("balance");

        return new AccountInfo(accountType, Double.parseDouble(balance));
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "accountType='" + accountType + '\'' +
                ", balance=" + balance +
                '}';
    }
}
