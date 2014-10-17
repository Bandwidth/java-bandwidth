package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ErrorTest extends BaseModelTest {
    private MockClient mockClient;

    @Before
    public void setUp(){
    	super.setUp();
        mockClient = new MockClient();
    }

    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
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
        Error error = new Error(null, "parentUri", jsonObject);

        assertThat(error.getId(), equalTo("ue-asvdtalmmhka2i63uzt66ma"));
        assertThat(error.getCategory(), equalTo("unavailable"));
        assertThat(error.getMessage(), equalTo("The callback server took too long to respond"));

        List<ErrorDetail> details = error.getDetails();
        assertThat(details.size(), equalTo(5));
        assertThat(details.get(0).getId(), equalTo("ued-fe55qxtkv73fjkf2643ocoa"));
        assertThat(details.get(0).getName(), equalTo("callId"));
        assertThat(details.get(0).getValue(), equalTo("c-bxu3tvqqde2bgavzw5awu2i"));
        assertThat(details.get(1).getValue(), equalTo("http://call/callback.json"));
        assertThat(details.get(1).getName(), equalTo("callbackUrl"));
    }
    
    @Test
    public void shouldGetErrorList() throws Exception {
        mockClient.arrayResult = (org.json.simple.JSONArray) new JSONParser().parse("[\n" +
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
        
        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText("[\n" +
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
        
        
        restResponse.setContentType("application/json");
        String mockUri = mockClient.getUserResourceUri(BandwidthConstants.ERRORS_URI_PATH) + "/id1";
        restResponse.setLocation(mockUri);
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
                
        List<Error> errorList = Error.list(mockClient, 0, 2);
        assertThat(errorList.size(), equalTo(2));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/errors"));
    }
    
    @Test
    public void shouldGetErrorById() throws Exception {
        JSONObject jsonObj = (JSONObject) new JSONParser().parse("{\n" +
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
        
        RestResponse restResponse = new RestResponse();
        restResponse.setContentType("application/json");
        String mockUri = mockClient.getUserResourceUri(BandwidthConstants.ERRORS_URI_PATH) + "/id1";
        restResponse.setLocation(mockUri);
        restResponse.setStatus(201);
        restResponse.setResponseText(jsonObj.toString());
        mockClient.setRestResponse(restResponse);
        
        Error error = Error.get(mockClient, "ue-asvdtalmmhka2i63uzt66ma");
        assertThat(error.getId(), equalTo("ue-asvdtalmmhka2i63uzt66ma"));
        assertThat(error.getCategory(), equalTo("unavailable"));
        assertThat(error.getMessage(), equalTo("The callback server took too long to respond"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/errors/ue-asvdtalmmhka2i63uzt66ma"));
    }    
    
}