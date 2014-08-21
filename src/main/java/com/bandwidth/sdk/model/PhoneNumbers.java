package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Point for <code>/v1/users/{userId}/phoneNumbers</code>
 *
 * @author vpotapenko
 */
public class PhoneNumbers extends BaseModelObject {

    public PhoneNumbers(IRestDriver driver, String parentUri) {
        super(driver, parentUri, null);
    }

    /**
     * Creates builder for getting a list of your numbers.
     * <br>Example:<br>
     * <code>List<PhoneNumber> list = numbers.queryNumbersBuilder().size(15).list();</code>
     *
     * @return new builder
     */
    public QueryNumbersBuilder queryNumbersBuilder() {
        return new QueryNumbersBuilder();
    }

    /**
     * Creates builder for allocating a number so you can use it to make and receive calls and send and receive messages.
     * <br>Example:<br>
     * <code>PhoneNumber number = numbers.newNumberBuilder().name("Name").number("{number}").create();</code>
     *
     * @return new builder
     */
    public NewNumberBuilder newNumberBuilder() {
        return new NewNumberBuilder();
    }

    /**
     * Gets information about one of your numbers using the number's ID.
     *
     * @param id numbers' id
     * @return number
     * @throws IOException
     */
    public PhoneNumber getNumber(String id) throws IOException {
        String numbersUri = getUri();
        String uri = StringUtils.join(new String[]{
                numbersUri,
                id
        }, '/');
        JSONObject jsonObject = driver.getObject(uri);
        return new PhoneNumber(driver, numbersUri, jsonObject);
    }

    /**
     * Gets information about one of your numbers using the E.164 number string, like "+19195551212".
     *
     * @param number your number
     * @return number
     * @throws IOException
     */
    public PhoneNumber getNumberByNumber(String number) throws IOException {
        return getNumber(URLEncoder.encode(number, "UTF-8"));
    }

    @Override
    protected String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "phoneNumbers"
        }, '/');
    }

    private List<PhoneNumber> getNumbers(Map<String, Object> params) throws IOException {
        String uri = getUri();
        JSONArray jsonArray = driver.getArray(uri, params);

        List<PhoneNumber> numbers = new ArrayList<PhoneNumber>();
        for (Object obj : jsonArray) {
            numbers.add(new PhoneNumber(driver, uri, (JSONObject) obj));
        }
        return numbers;
    }

    private PhoneNumber newNumber(Map<String, Object> params) throws IOException {
        String uri = getUri();
        JSONObject jsonObject = driver.create(uri, params);
        return new PhoneNumber(driver, uri, jsonObject);
    }

    public class QueryNumbersBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public QueryNumbersBuilder page(int page) {
            params.put("page", page);
            return this;
        }

        public QueryNumbersBuilder size(int size) {
            params.put("size", size);
            return this;
        }

        public List<PhoneNumber> list() throws IOException {
            return getNumbers(params);
        }
    }

    public class NewNumberBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public NewNumberBuilder number(String number) {
            params.put("number", number);
            return this;
        }

        public NewNumberBuilder applicationId(String applicationId) {
            params.put("applicationId", applicationId);
            return this;
        }

        public NewNumberBuilder name(String name) {
            params.put("name", name);
            return this;
        }

        public NewNumberBuilder fallbackNumber(String fallbackNumber) {
            params.put("fallbackNumber", fallbackNumber);
            return this;
        }

        public PhoneNumber create() throws IOException {
            return newNumber(params);
        }
    }
}
