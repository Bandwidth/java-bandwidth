package com.bandwidth.sdk.account;

/**
 * @author vpotapenko
 */
public class AccountInfo {

    public final double balance;
    public final String accountType;

    public AccountInfo(double balance, String accountType) {
        this.balance = balance;
        this.accountType = accountType;
    }
}
