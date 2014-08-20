package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Point for <code>/v1/availableNumbers</code>
 *
 * @author vpotapenko
 */
public class AvailableNumbers extends BaseModelObject {

    public AvailableNumbers(IRestDriver driver) {
        super(driver, null, null);
    }

    /**
     * Creates builder for searching for available local numbers by location or pattern criteria.
     * <br>Example:<br>
     * <code>List<AvailableNumber> list = numbers.queryLocalNumbersBuilder().city("City").list();</code>
     *
     * @return new builder
     */
    public QueryLocalNumbersBuilder queryLocalNumbersBuilder() {
        return new QueryLocalNumbersBuilder();
    }

    /**
     * Creates builder for searching for available Toll Free numbers.
     * <br>Example:<br>
     * <code>List<AvailableNumber> list = numbers.queryTollFreeNumbersBuilder().pattern("*2%3F9*").list();</code>
     *
     * @return new builder
     */
    public QueryTollFreeNumbersBuilder queryTollFreeNumbersBuilder() {
        return new QueryTollFreeNumbersBuilder();
    }

    private List<AvailableNumber> getLocalNumbers(Map<String, Object> params) throws IOException {
        String localUri = getLocalUri();
        JSONArray array = driver.getArray(localUri, params);

        List<AvailableNumber> numbers = new ArrayList<AvailableNumber>();
        for (Object obj : array) {
            numbers.add(new AvailableNumber(driver, localUri, (JSONObject) obj));
        }
        return numbers;
    }

    private String getLocalUri() {
        return StringUtils.join(new String[]{
                getUri(),
                "local"
        }, '/');
    }

    private String getTollFreeUri() {
        return StringUtils.join(new String[]{
                getUri(),
                "tollFree"
        }, '/');
    }

    @Override
    protected String getUri() {
        return StringUtils.join(new String[]{
                "availableNumbers"
        }, '/');
    }

    private List<AvailableNumber> getTollFreeNumbers(Map<String, Object> params) throws IOException {
        String tollFreeUri = getTollFreeUri();
        JSONArray array = driver.getArray(tollFreeUri, params);

        List<AvailableNumber> numbers = new ArrayList<AvailableNumber>();
        for (Object obj : array) {
            numbers.add(new AvailableNumber(driver, tollFreeUri, (JSONObject) obj));
        }
        return numbers;
    }

    public class QueryTollFreeNumbersBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public List<AvailableNumber> list() throws IOException {
            return getTollFreeNumbers(params);
        }

        public QueryTollFreeNumbersBuilder quantity(int quantity) {
            params.put("quantity", String.valueOf(quantity));
            return this;
        }

        public QueryTollFreeNumbersBuilder pattern(String pattern) {
            params.put("pattern", pattern);
            return this;
        }
    }

    public class QueryLocalNumbersBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public List<AvailableNumber> list() throws IOException {
            return getLocalNumbers(params);
        }

        public QueryLocalNumbersBuilder city(String city) {
            params.put("city", city);
            return this;
        }

        public QueryLocalNumbersBuilder state(String state) {
            params.put("state", state);
            return this;
        }

        public QueryLocalNumbersBuilder zip(String zip) {
            params.put("zip", zip);
            return this;
        }

        public QueryLocalNumbersBuilder areaCode(String areaCode) {
            params.put("areaCode", areaCode);
            return this;
        }

        public QueryLocalNumbersBuilder localNumber(String localNumber) {
            params.put("localNumber", localNumber);
            return this;
        }

        public QueryLocalNumbersBuilder inLocalCallingArea(boolean inLocalCallingArea) {
            params.put("inLocalCallingArea", String.valueOf(inLocalCallingArea));
            return this;
        }

        public QueryLocalNumbersBuilder quantity(int quantity) {
            params.put("quantity", String.valueOf(quantity));
            return this;
        }
    }
}
