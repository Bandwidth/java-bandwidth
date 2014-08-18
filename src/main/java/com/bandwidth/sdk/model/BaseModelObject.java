package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vpotapenko
 */
public abstract class BaseModelObject {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);

    protected final IRestDriver driver;
    protected final String parentUri;

    private final Map<String, Object> properties = new HashMap<String, Object>();

    protected BaseModelObject(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        this.driver = driver;
        this.parentUri = parentUri;

        if (jsonObject != null) {
            for (Object key : jsonObject.keySet()) {
                properties.put(key.toString(), jsonObject.get(key));
            }
        }
    }

    public String getUri() {
        // default implementation of uri
        return StringUtils.join(new String[]{
                parentUri,
                getId()
        }, '/');
    }

    public String getId() {
        return getPropertyAsString("id");
    }

    protected String getPropertyAsString(String key) {
        return (String) properties.get(key);
    }

    protected Long getPropertyAsLong(String key) {
        return (Long) properties.get(key);
    }

    protected Double getPropertyAsDouble(String key) {
        Object o = properties.get(key);
        return (o instanceof Double) ? (Double) o : Double.parseDouble(o.toString());
    }

    protected Date getPropertyAsDate(String key) {
        String time = (String) properties.get(key);
        if (time == null) return null;

        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
