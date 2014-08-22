package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Information about one user error
 *
 * @author vpotapenko
 */
public class Error extends BaseModelObject {

    public Error(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
    }

    public String getMessage() {
        return getPropertyAsString("message");
    }

    public String getCategory() {
        return getPropertyAsString("category");
    }

    public String getCode() {
        return getPropertyAsString("code");
    }

    public List<ErrorDetail> getDetails() {
        String uri = getUri();
        List<ErrorDetail> details = new ArrayList<ErrorDetail>();
        for (Object obj : (List) getProperty("details")) {
            details.add(new ErrorDetail(client, uri, (JSONObject) obj));
        }
        return details;
    }

    public Date getTime() {
        return getPropertyAsDate("time");
    }

    @Override
    public String toString() {
        return "Error{" +
                "id='" + getId() + '\'' +
                ", message='" + getMessage() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", code='" + getCode() + '\'' +
                ", time=" + getTime() +
                '}';
    }
}
