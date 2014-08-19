package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ErrorsTest {

    private MockRestDriver mockRestDriver;
    private Errors errors;

    @Before
    public void setUp() throws Exception {
        mockRestDriver = new MockRestDriver();
        errors = new Errors(mockRestDriver, "parentUri");
    }

    @Test
    public void shouldGetErrorList() throws Exception {
        mockRestDriver.arrayResult = (org.json.simple.JSONArray) new JSONParser().parse("[\n" +
                "  {\n" +
                "    \"message\": \"The callback server took too long to respond\",\n" +
                "    \"id\": \"ue-asvdtalmmhka2i63uzt66ma\",\n" +
                "    \"category\": \"unavailable\",\n" +
                "    \"time\": \"2014-08-18T18:26:03Z\",\n" +
                "    \"details\": [\n" +
                "      {\n" +
                "        \"id\": \"ued-fe55qxtkv73fjkf2643ocoa\",\n" +
                "        \"name\": \"callId\",\n" +
                "        \"value\": \"c-bxu3tvqqde2bgavzw5awu2i\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"ued-5driawmrhgg4rtnwzl5dkoq\",\n" +
                "        \"name\": \"callbackUrl\",\n" +
                "        \"value\": \"http://call/callback.json\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"ued-d2suixvquntgpotdncyzqwy\",\n" +
                "        \"name\": \"callbackMethod\",\n" +
                "        \"value\": \"POST\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"ued-eq5lncjtcjrela3begu2vmi\",\n" +
                "        \"name\": \"callbackEvent\",\n" +
                "        \"value\": \"hangup\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"ued-a7am5v3s6hu57mybsgd7lyy\",\n" +
                "        \"name\": \"callbackTimeout\",\n" +
                "        \"value\": \"10000\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"code\": \"callback-server-timeout\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"message\": \"A network error ocurred during the callback\",\n" +
                "    \"id\": \"ue-sv66c2ds2amjvgtlgmb53uy\",\n" +
                "    \"category\": \"unavailable\",\n" +
                "    \"time\": \"2014-08-18T18:25:19Z\",\n" +
                "    \"details\": [\n" +
                "      {\n" +
                "        \"id\": \"ued-tyogrpreublkiszfxw5sfmi\",\n" +
                "        \"name\": \"callId\",\n" +
                "        \"value\": \"c-bxu3tvqqde2bgavzw5awu2i\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"ued-4oxz5nzjhltw3d3cu2lmaaa\",\n" +
                "        \"name\": \"callbackUrl\",\n" +
                "        \"value\": \"http://call/callback.json\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"ued-acpg3kicmejyonln337qcvi\",\n" +
                "        \"name\": \"callbackMethod\",\n" +
                "        \"value\": \"POST\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"ued-fjrwgw2lc4xwk3iqimjznrq\",\n" +
                "        \"name\": \"callbackEvent\",\n" +
                "        \"value\": \"answer\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"code\": \"callback-io-error\"\n" +
                "  }\n" +
                "]");
        List<Error> errorList = errors.getErrors();
        assertThat(errorList.size(), equalTo(2));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/errors"));
    }

    @Test
    public void shouldGetErrorById() throws Exception {
        mockRestDriver.result = (JSONObject) new JSONParser().parse("{\n" +
                "  \"message\": \"The callback server took too long to respond\",\n" +
                "  \"id\": \"ue-asvdtalmmhka2i63uzt66ma\",\n" +
                "  \"category\": \"unavailable\",\n" +
                "  \"time\": \"2014-08-18T18:26:03Z\",\n" +
                "  \"details\": [\n" +
                "    {\n" +
                "      \"id\": \"ued-fe55qxtkv73fjkf2643ocoa\",\n" +
                "      \"name\": \"callId\",\n" +
                "      \"value\": \"c-bxu3tvqqde2bgavzw5awu2i\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ued-5driawmrhgg4rtnwzl5dkoq\",\n" +
                "      \"name\": \"callbackUrl\",\n" +
                "      \"value\": \"http://call/callback.json\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ued-d2suixvquntgpotdncyzqwy\",\n" +
                "      \"name\": \"callbackMethod\",\n" +
                "      \"value\": \"POST\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ued-eq5lncjtcjrela3begu2vmi\",\n" +
                "      \"name\": \"callbackEvent\",\n" +
                "      \"value\": \"hangup\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ued-a7am5v3s6hu57mybsgd7lyy\",\n" +
                "      \"name\": \"callbackTimeout\",\n" +
                "      \"value\": \"10000\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"code\": \"callback-server-timeout\"\n" +
                "}");
        Error error = errors.getErrorById("ue-asvdtalmmhka2i63uzt66ma");
        assertThat(error.getId(), equalTo("ue-asvdtalmmhka2i63uzt66ma"));
        assertThat(error.getCategory(), equalTo("unavailable"));
        assertThat(error.getMessage(), equalTo("The callback server took too long to respond"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/errors/ue-asvdtalmmhka2i63uzt66ma"));
    }
}