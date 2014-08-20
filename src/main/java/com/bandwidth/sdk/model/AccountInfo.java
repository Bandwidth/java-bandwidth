package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.json.simple.JSONObject;

/**
 * Account information
 *
 * @author vpotapenko
 */
public class AccountInfo extends BaseModelObject {

    public AccountInfo(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
    }

    public String getAccountType() {
        return getPropertyAsString("accountType");
    }

    public double getBalance() {
        return getPropertyAsDouble("balance");
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "accountType='" + getAccountType() + '\'' +
                ", balance=" + getBalance() +
                '}';
    }
}
