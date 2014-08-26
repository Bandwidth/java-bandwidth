package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author vpotapenko
 */
public abstract class BaseModelObject {

    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);

    protected final BandwidthRestClient client;
    protected final String parentUri;

    protected final Map<String, Object> properties = new HashMap<String, Object>();

    protected BaseModelObject(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        this.client = client;
        this.parentUri = parentUri;

        updateProperties(jsonObject);
    }

    protected void updateProperties(JSONObject jsonObject) {
        if (jsonObject != null) {
            for (Object key : jsonObject.keySet()) {
                properties.put(key.toString(), jsonObject.get(key));
            }
        }
    }

    public String getId() {
        return getPropertyAsString("id");
    }

    protected String getUri() {
        // default implementation of uri
        return StringUtils.join(new String[]{
                parentUri,
                getId()
        }, '/');
    }

    protected String getPropertyAsString(String key) {
        return (String) properties.get(key);
    }

    protected String[] getPropertyAsStringArray(String key) {
        if (properties.containsKey(key)) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) properties.get(key);

            String[] arr = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                arr[i] = obj.toString();
            }
            return arr;
        } else {
            return null;
        }
    }

    protected Object getProperty(String key) {
        return properties.get(key);
    }

    protected Boolean getPropertyAsBoolean(String key) {
        Object o = properties.get(key);
        if (o == null) return null;

        return o instanceof Boolean ? (Boolean) o : "true".equals(o.toString());
    }

    protected Long getPropertyAsLong(String key) {
        return (Long) properties.get(key);
    }

    protected Double getPropertyAsDouble(String key) {
        Object o = properties.get(key);
        return (o instanceof Double) ? (Double) o : Double.parseDouble(o.toString());
    }

    protected Date getPropertyAsDate(String key) {
        Object o = properties.get(key);
        if (o == null) return null;
        if (o instanceof Long) return new Date((Long) o);

        try {
            return dateFormat.parse(o.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    protected void putProperty(String key, Object value) {
        properties.put(key, value);
    }

    protected Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();

        for (String key : properties.keySet()) {
            map.put(key, properties.get(key));
        }

        return map;
    }
}
