package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * @author vpotapenko
 */
public class NumberInfo extends BaseModelObject {

    public NumberInfo(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
    }

    public String getName() {
        return getPropertyAsString("name");
    }

    public String getNumber() {
        return getPropertyAsString("number");
    }

    public Date getCreated() {
        return getPropertyAsDate("created");
    }

    public Date getUpdated() {
        return getPropertyAsDate("updated");
    }

    @Override
    public String toString() {
        return "NumberInfo{" +
                "name='" + getName() + '\'' +
                ", number='" + getNumber() + '\'' +
                ", created=" + getCreated() +
                ", updated=" + getUpdated() +
                '}';
    }
}
