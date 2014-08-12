package com.bandwidth.sdk.availableNumbers;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class BandwidthAvailableNumbers {

    private final BandwidthRestClient client;

    public BandwidthAvailableNumbers(BandwidthRestClient client) {
        this.client = client;
    }

    public LocalRequestBuilder getLocalNumbers() {
        return new LocalRequestBuilder();
    }

    public TollFreeRequestBuilder getTollFreeNumbers() {
        return new TollFreeRequestBuilder();
    }

    private List<BandwidthNumber> getLocalNumbers(Map<String, Object> params) throws IOException {
        JSONArray array = client.getRestDriver().requestLocalAvailableNumbers(params);

        List<BandwidthNumber> numbers = new ArrayList<BandwidthNumber>();
        for (Object obj : array) {
            numbers.add(BandwidthNumber.from((JSONObject) obj));
        }
        return numbers;
    }

    private List<BandwidthNumber> getTollFreeNumbers(Map<String, Object> params) throws IOException {
        JSONArray array = client.getRestDriver().requestTollFreeAvailableNumbers(params);

        List<BandwidthNumber> numbers = new ArrayList<BandwidthNumber>();
        for (Object obj : array) {
            numbers.add(BandwidthNumber.from((JSONObject) obj));
        }
        return numbers;
    }

    public class TollFreeRequestBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public List<BandwidthNumber> get() throws IOException {
            return getTollFreeNumbers(params);
        }

        public TollFreeRequestBuilder quantity(int quantity) {
            params.put("quantity", String.valueOf(quantity));
            return this;
        }

        public TollFreeRequestBuilder pattern(String pattern) {
            params.put("pattern", pattern);
            return this;
        }
    }

    public class LocalRequestBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public List<BandwidthNumber> get() throws IOException {
            return getLocalNumbers(params);
        }

        public LocalRequestBuilder city(String city) {
            params.put("city", city);
            return this;
        }

        public LocalRequestBuilder state(String state) {
            params.put("state", state);
            return this;
        }

        public LocalRequestBuilder zip(String zip) {
            params.put("zip", zip);
            return this;
        }

        public LocalRequestBuilder areaCode(String areaCode) {
            params.put("areaCode", areaCode);
            return this;
        }

        public LocalRequestBuilder localNumber(String localNumber) {
            params.put("localNumber", localNumber);
            return this;
        }

        public LocalRequestBuilder inLocalCallingArea(boolean inLocalCallingArea) {
            params.put("inLocalCallingArea", String.valueOf(inLocalCallingArea));
            return this;
        }

        public LocalRequestBuilder quantity(int quantity) {
            params.put("quantity", String.valueOf(quantity));
            return this;
        }
    }
}
