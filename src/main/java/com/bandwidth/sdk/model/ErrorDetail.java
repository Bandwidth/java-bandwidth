package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.json.simple.JSONObject;

/**
 * @author vpotapenko
 */
public class ErrorDetail extends BaseModelObject {

    public ErrorDetail(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
    }

    public String getName() {
        return getPropertyAsString("name");
    }

    public String getValue() {
        return getPropertyAsString("value");
    }

    @Override
    public String toString() {
        return "ErrorDetail{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", value='" + getValue() + '\'' +
                '}';
    }
}
