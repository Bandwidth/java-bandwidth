package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Point for <code>/v1/users/{userId}/errors</code>
 *
 * @author vpotapenko
 */
public class Errors extends BaseModelObject {

    public Errors(BandwidthRestClient client, String parentUri) {
        super(client, parentUri, null);
    }

    /**
     * Gets the most recent user errors for the user.
     *
     * @return errors
     * @throws IOException
     */
    public List<Error> getErrors() throws IOException {
        String errorsUri = getUri();
        JSONArray array = client.getArray(errorsUri, null);

        List<Error> errors = new ArrayList<Error>();
        for (Object obj : array) {
            errors.add(new Error(client, errorsUri, (JSONObject) obj));
        }
        return errors;
    }

    /**
     * Gets information about one user error.
     * @param id error id
     * @return information about one user error
     * @throws IOException
     */
    public Error getError(String id) throws IOException {
        String errorsUri = getUri();
        String uri = StringUtils.join(new String[]{
                errorsUri,
                id
        }, '/');
        JSONObject jsonObject = client.getObject(uri);
        return new Error(client, errorsUri, jsonObject);
    }

    @Override
    protected String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "errors"
        }, '/');
    }
}
