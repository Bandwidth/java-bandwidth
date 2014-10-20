package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class CallTest {

    private MockClient mockClient;

    @Before
    public void setUp(){
        mockClient = new MockClient();
    }

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");
        Call call = new Call(mockClient, jsonObject);

        assertThat(call.getId(), equalTo("c-11111111111111111111111"));
        assertThat(call.getState(), equalTo("completed"));
        assertThat(call.getEvents(), equalTo("https://api.catapult.inetwork.com/v1/users/calls/events"));
        assertThat(call.getChargeableDuration(), equalTo(300l));
        assertThat(call.getDirection(), equalTo("in"));
        assertThat(call.getFrom(), equalTo("+22222222222"));
        assertThat(call.getTo(), equalTo("+11111111111"));
        assertThat(call.isRecordingEnabled(), equalTo(true));
    }

    @Test
    public void shouldHangUp() throws ParseException, Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse
        		("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);

        Call call = new Call(mockClient, jsonObject);

        call.hangUp();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));
        assertThat(mockClient.requests.get(0).params.get("state").toString(), equalTo("completed"));
        assertThat(mockClient.requests.get(1).name, equalTo("get"));
        assertThat(mockClient.requests.get(1).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));
    }

    @Test
    public void shouldAnswerOnIncoming() throws ParseException, IOException, Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);

        Call call = new Call(mockClient, jsonObject);

        call.answerOnIncoming();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));
        assertThat(mockClient.requests.get(0).params.get("state").toString(), equalTo("active"));
        assertThat(mockClient.requests.get(1).name, equalTo("get"));
        assertThat(mockClient.requests.get(1).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));
    }

    @Test
    public void shouldRejectIncoming() throws ParseException, IOException, Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);

        Call call = new Call(mockClient, jsonObject);

        call.rejectIncoming();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));
        assertThat(mockClient.requests.get(0).params.get("state").toString(), equalTo("rejected"));
        assertThat(mockClient.requests.get(1).name, equalTo("get"));
        assertThat(mockClient.requests.get(1).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));
    }

    @Test
    public void shouldSwitchRecordingState() throws ParseException, IOException, Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);

        Call call = new Call(mockClient, jsonObject);

        call.recordingOn();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));
        assertThat(mockClient.requests.get(0).params.get("recordingEnabled").toString(), equalTo("true"));
        assertThat(mockClient.requests.get(1).name, equalTo("get"));
        assertThat(mockClient.requests.get(1).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));

        mockClient.requests.clear();
        call.recordingOff();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).params.get("recordingEnabled").toString(), equalTo("false"));
        assertThat(mockClient.requests.get(1).name, equalTo("get"));
        assertThat(mockClient.requests.get(1).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));
    }

    @Test
    public void shouldTransferCall() throws ParseException, IOException, Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);

        Call call = new Call(mockClient, jsonObject);

        call.callTransferBuilder("8917727272").callbackUrl("url").create();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));
        assertThat(mockClient.requests.get(0).params.get("state").toString(), equalTo("transferring"));
        assertThat(mockClient.requests.get(1).name, equalTo("get"));

        mockClient.requests.clear();
        call.callTransferBuilder("8917727272").create();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));
        assertThat(mockClient.requests.get(0).params.get("state").toString(), equalTo("transferring"));
        assertThat(mockClient.requests.get(1).name, equalTo("get"));

        mockClient.requests.clear();
        call.callTransferBuilder("8917727272").gender(Gender.male).locale(SentenceLocale.French).sentence("Hello").transferCallerId("callerId").create();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111"));
        assertThat(mockClient.requests.get(0).params.get("state").toString(), equalTo("transferring"));
        assertThat(mockClient.requests.get(1).name, equalTo("get"));
    }

    @Test
    public void shouldCreateAudio() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        mockClient.result = jsonObject;

        Call call = new Call(mockClient, jsonObject);
        call.newAudioBuilder().fileUrl("url").create();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111/audio"));
        assertThat(mockClient.requests.get(0).params.get("fileUrl").toString(), equalTo("url"));

        mockClient.requests.clear();
        call.stopSentence();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111/audio"));
        assertThat(mockClient.requests.get(0).params.get("sentence").toString(), equalTo(""));

        mockClient.requests.clear();
        call.stopAudioFilePlaying();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111/audio"));
        assertThat(mockClient.requests.get(0).params.get("fileUrl").toString(), equalTo(""));
    }

    @Test
    public void shouldSendDtmf() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        mockClient.result = jsonObject;

        Call call = new Call(mockClient, jsonObject);
        call.sendDtmf("1234");

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111/dtmf"));
        assertThat(mockClient.requests.get(0).params.get("dtmfOut").toString(), equalTo("1234"));
    }

    @Test
    public void shouldGetEventsList() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        JSONArray arrayResult = (JSONArray) new JSONParser().parse("[{\"id\":\"ce-hsdbdbdhd\",\"time\":1407916959116,\"name\":\"error\",\"data\":\"Call Id wasn't found on FreeSWITCH anymore\"}]");

        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);
        
        Call call = new Call(mockClient, jsonObject);
        
        response.setResponseText(arrayResult.toString());
        mockClient.setRestResponse(response);
       
        
        
        List<EventBase> eventsList = call.getEventsList();

        assertThat(eventsList.size(), equalTo(1));
        assertThat(eventsList.get(0).getId(), equalTo("ce-hsdbdbdhd"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111/events"));
    }

    @Test
    public void shouldGetEventById() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        JSONObject eventObj = (JSONObject) new JSONParser().parse("{\"id\":\"ce-hsdbdbdhd\",\"time\":1407916959116,\"name\":\"error\",\"data\":\"Call Id wasn't found on FreeSWITCH anymore\"}");

        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);
        
        
        Call call = new Call(mockClient, jsonObject);
        
        response.setResponseText(eventObj.toString());
        mockClient.setRestResponse(response);
        
        
        EventBase event = call.getEvent("id1");

        assertThat(event.getId(), equalTo("ce-hsdbdbdhd"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111/events/id1"));
    }

    @Test
    public void shouldGetRecordingsList() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        JSONArray arrayResult = (JSONArray) new JSONParser().parse("[\n" +
                "  {\n" +
                "    \"endTime\": \"2013-02-08T12:06:55Z\",\n" +
                "    \"id\": \"Id1\",\n" +
                "    \"media\": \"https://.../v1/users/.../media/{callId}-1.wav\",\n" +
                "    \"call\": \"https://.../v1/users/.../calls/{callId}\",\n" +
                "    \"startTime\": \"2013-02-08T12:05:17Z\",\n" +
                "    \"state\": \"complete\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"endTime\": \"2013-02-08T13:15:65Z\",\n" +
                "    \"id\": \"Id2\",\n" +
                "    \"media\": \"https://.../v1/users/.../media/{callId}-2.wav\",\n" +
                "    \"call\": \"https://.../v1/users/.../calls/{callId}\",\n" +
                "    \"startTime\": \"2013-02-08T13:15:55Z\",\n" +
                "    \"state\": \"complete\"\n" +
                "  }\n" +
                "]");
        
        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);       

        Call call = new Call(mockClient, jsonObject);
        
        response.setResponseText(arrayResult.toString());
        mockClient.setRestResponse(response);
        
        
        List<Recording> recordings = call.getRecordings();

        assertThat(recordings.size(), equalTo(2));
        assertThat(recordings.get(0).getId(), equalTo("Id1"));
        assertThat(recordings.get(0).getMedia(), equalTo("https://.../v1/users/.../media/{callId}-1.wav"));
        assertThat(recordings.get(0).getState(), equalTo("complete"));
        assertThat(recordings.get(0).getCall(), equalTo("https://.../v1/users/.../calls/{callId}"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111/recordings"));
    }

    @Test
    public void shouldCreateGather() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        mockClient.result = jsonObject;

        Call call = new Call(mockClient, jsonObject);
        call.callGatherBuilder().maxDigits(5).promptSentence("Hello").create();

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111/gather"));
        assertThat(mockClient.requests.get(0).params.get("maxDigits").toString(), equalTo("5"));
    }

    @Test
    public void shouldGetGatherById() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        JSONObject gatherObj = (JSONObject) new JSONParser().parse("{\n" +
                        "  \"id\": \"gtr-kj4xloaq5vbpfxyeypndgxa\",\n" +
                        "  \"state\": \"completed\",\n" +
                        "  \"reason\": \"max-digits\",\n" +
                        "  \"createdTime\": \"2014-02-12T19:33:56Z\",\n" +
                        "  \"completedTime\": \"2014-02-12T19:33:59Z\",\n" +
                        "  \"call\": \"https://api.catapult.inetwork.com/v1/users/u-xa2n3oxk6it4efbglisna6a/calls/c-isw3qup6gvr3ywcsentygnq\",\n" +
                        "  \"digits\": \"123\"\n" +
                        "}");

        
        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);   
        
        Call call = new Call(mockClient, jsonObject);
        
        response.setResponseText(gatherObj.toString());
        mockClient.setRestResponse(response);
       
        
        Gather gather = call.getGather("gtr-kj4xloaq5vbpfxyeypndgxa");

        assertThat(gather.getId(), equalTo("gtr-kj4xloaq5vbpfxyeypndgxa"));
        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/c-11111111111111111111111/gather/gtr-kj4xloaq5vbpfxyeypndgxa"));
    }
    
    @Test
    public void shouldGetCallsList() throws Exception {
        mockClient.arrayResult = (JSONArray) new JSONParser().parse
        		("[{\"to\":\"+11111111111\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id1\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"},{\"to\":\"+33333333333\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"out\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+44444444444\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id2\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"}]");

        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText
			("[{\"to\":\"+11111111111\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id1\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"},{\"to\":\"+33333333333\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"out\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+44444444444\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id2\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"}]");

        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        ResourceList<Call> callList = Call.list(mockClient, 0, 5);
       
        assertThat(callList.size(), equalTo(2));
        assertThat(callList.get(0).getId(), equalTo("id1"));
        assertThat(callList.get(0).getFrom(), equalTo("+22222222222"));
        assertThat(callList.get(1).getId(), equalTo("id2"));
        assertThat(callList.get(1).getFrom(), equalTo("+44444444444"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls"));
        assertThat(mockClient.requests.get(0).params.get("size").toString(), equalTo("5"));
    }
    
    @Test
    public void shouldGetCallById() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id1\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"}");

        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);   

        Call bandwidthCall = Call.get(mockClient, "id1");
        assertThat(bandwidthCall, notNullValue());

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls/id1"));
    }
    
    @Test
    public void shouldCreateNewCall() throws Exception {
        mockClient.result = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id1\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"}");

        //Call bandwidthCall = calls.newCallBuilder().from("from").to("to").bridgeId("bridgeId").callbackUrl("url").recordingEnabled(true).create();
        
        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText("{\"to\":\"+11111111111\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id1\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"}");
        
        restResponse.setContentType("application/json");
        String mockUri = mockClient.getUserResourceUri(BandwidthConstants.CALLS_URI_PATH) + "/id1";
        restResponse.setLocation(mockUri);
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
    	HashMap params = new HashMap();
    	params.put("to", "+11111111111");
    	params.put("from", "+11111111112");
    	params.put("callbackUrl", "https:\\/\\/myapp.myendpoint.com");
        
        
        Call bandwidthCall = Call.create(mockClient, params);
        assertThat(bandwidthCall, notNullValue());

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).params.get("from").toString(), equalTo("+11111111112"));
        assertThat(mockClient.requests.get(0).params.get("to").toString(), equalTo("+11111111111"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/calls"));
    }
    
    

}