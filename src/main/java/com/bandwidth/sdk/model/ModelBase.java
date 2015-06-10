package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sbarstow on 9/30/14.
 */
public abstract class ModelBase {

    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);

    protected final Map<String, Object> properties = new HashMap<String, Object>();

    protected void updateProperties(final JSONObject jsonObject) {
        if (jsonObject != null) {
            for (final Object key : jsonObject.keySet()) {
                properties.put(key.toString(), jsonObject.get(key));
            }
        }
    }

    protected String getPropertyAsString(final String key) {
        return (String) properties.get(key);
    }

    protected String[] getPropertyAsStringArray(final String key) {
        if (properties.containsKey(key)) {
            @SuppressWarnings("unchecked")
            final
            List<Object> list = (List<Object>) properties.get(key);

            final String[] arr = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                final Object obj = list.get(i);
                arr[i] = obj.toString();
            }
            return arr;
        } else {
            return null;
        }
    }

    protected Object getProperty(final String key) {
        return properties.get(key);
    }

    protected Boolean getPropertyAsBoolean(final String key) {
        final Object o = properties.get(key);
        if (o == null) return null;

        return o instanceof Boolean ? (Boolean) o : "true".equals(o.toString());
    }

    protected Long getPropertyAsLong(final String key) {
        return (Long) properties.get(key);
    }

    protected Double getPropertyAsDouble(final String key) {
        final Object o = properties.get(key);
        return (o instanceof Double) ? (Double) o : Double.parseDouble(o.toString());
    }

    protected Date getPropertyAsDate(final String key) {
        final Object o = properties.get(key);
        if (o == null) return null;
        if (o instanceof Long) return new Date((Long) o);

        try {
            return dateFormat.parse(o.toString());
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }
    }

    protected void putProperty(final String key, final Object value) {
        properties.put(key, value);
    }

    protected Map<String, Object> toMap() {
        final Map<String, Object> map = new HashMap<String, Object>();

        for (final String key : properties.keySet()) {
            map.put(key, properties.get(key));
        }

        return map;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ModelBase{");
        sb.append("properties=").append(properties);
        sb.append('}');
        return sb.toString();
    }
}
