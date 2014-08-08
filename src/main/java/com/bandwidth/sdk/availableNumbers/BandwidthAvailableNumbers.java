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
        return new LocalRequestBuilder(this);
    }

    public TollFreeRequestBuilder getTollFreeNumbers() {
        return new TollFreeRequestBuilder(this);
    }

    private List<BandwidthNumber> getLocalNumbers(Map<String, String> params) throws IOException {
        JSONArray array = client.getRestDriver().requestLocalAvailableNumbers(params);

        List<BandwidthNumber> numbers = new ArrayList<BandwidthNumber>();
        for (Object obj : array) {
            numbers.add(BandwidthNumber.from((JSONObject) obj));
        }
        return numbers;
    }

    private List<BandwidthNumber> getTollFreeNumbers(Map<String, String> params) throws IOException {
        JSONArray array = client.getRestDriver().requestTollFreeAvailableNumbers(params);

        List<BandwidthNumber> numbers = new ArrayList<BandwidthNumber>();
        for (Object obj : array) {
            numbers.add(BandwidthNumber.from((JSONObject) obj));
        }
        return numbers;
    }

    public static class TollFreeRequestBuilder {

        private final BandwidthAvailableNumbers availableNumbers;
        private final Map<String, String> params = new HashMap<String, String>();

        public TollFreeRequestBuilder(BandwidthAvailableNumbers availableNumbers) {
            this.availableNumbers = availableNumbers;
        }

        public List<BandwidthNumber> get() throws IOException {
            return availableNumbers.getTollFreeNumbers(params);
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

    public static class LocalRequestBuilder {

        private final BandwidthAvailableNumbers availableNumbers;
        private final Map<String, String> params = new HashMap<String, String>();

        public LocalRequestBuilder(BandwidthAvailableNumbers availableNumbers) {
            this.availableNumbers = availableNumbers;
        }

        public List<BandwidthNumber> get() throws IOException {
            return availableNumbers.getLocalNumbers(params);
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
