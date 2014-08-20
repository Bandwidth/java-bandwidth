package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CallTest {

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");
        Call call = new Call(null, null, jsonObject);

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
    public void shouldHangUp() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);

        call.hangUp();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("completed"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("getObject"));
        assertThat(mockRestDriver.requests.get(1).uri, equalTo("parentUri/c-11111111111111111111111"));
    }

    @Test
    public void shouldAnswerOnIncoming() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);

        call.answerOnIncoming();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("active"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("getObject"));
        assertThat(mockRestDriver.requests.get(1).uri, equalTo("parentUri/c-11111111111111111111111"));
    }

    @Test
    public void shouldRejectIncoming() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);

        call.rejectIncoming();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("rejected"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("getObject"));
        assertThat(mockRestDriver.requests.get(1).uri, equalTo("parentUri/c-11111111111111111111111"));
    }

    @Test
    public void shouldSwitchRecordingState() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);

        call.recordingOn();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111"));
        assertThat(mockRestDriver.requests.get(0).params.get("recordingEnabled").toString(), equalTo("true"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("getObject"));
        assertThat(mockRestDriver.requests.get(1).uri, equalTo("parentUri/c-11111111111111111111111"));

        mockRestDriver.requests.clear();
        call.recordingOff();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).params.get("recordingEnabled").toString(), equalTo("false"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("getObject"));
        assertThat(mockRestDriver.requests.get(1).uri, equalTo("parentUri/c-11111111111111111111111"));
    }

    @Test
    public void shouldTransferCall() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);

        call.callTransferBuilder("8917727272").callbackUrl("url").create();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("transferring"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("getObject"));

        mockRestDriver.requests.clear();
        call.callTransferBuilder("8917727272").create();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("transferring"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("getObject"));

        mockRestDriver.requests.clear();
        call.callTransferBuilder("8917727272").gender(Gender.male).locale(SentenceLocale.French).sentence("Hello").transferCallerId("callerId").create();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("transferring"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("getObject"));
    }

    @Test
    public void shouldCreateAudio() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);
        call.newAudioBuilder().fileUrl("url").create();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111/audio"));
        assertThat(mockRestDriver.requests.get(0).params.get("fileUrl").toString(), equalTo("url"));

        mockRestDriver.requests.clear();
        call.stopSentence();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111/audio"));
        assertThat(mockRestDriver.requests.get(0).params.get("sentence").toString(), equalTo(""));

        mockRestDriver.requests.clear();
        call.stopAudioFilePlaying();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111/audio"));
        assertThat(mockRestDriver.requests.get(0).params.get("fileUrl").toString(), equalTo(""));
    }

    @Test
    public void shouldSendDtmf() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);
        call.sendDtmf("1234");

        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111/dtmf"));
        assertThat(mockRestDriver.requests.get(0).params.get("dtmfOut").toString(), equalTo("1234"));
    }

    @Test
    public void shouldGetEventsList() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[{\"id\":\"ce-hsdbdbdhd\",\"time\":1407916959116,\"name\":\"error\",\"data\":\"Call Id wasn't found on FreeSWITCH anymore\"}]");

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);
        List<Event> eventsList = call.getEventsList();

        assertThat(eventsList.size(), equalTo(1));
        assertThat(eventsList.get(0).getId(), equalTo("ce-hsdbdbdhd"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111/events"));
    }

    @Test
    public void shouldGetEventById() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = (JSONObject) new JSONParser().parse("{\"id\":\"ce-hsdbdbdhd\",\"time\":1407916959116,\"name\":\"error\",\"data\":\"Call Id wasn't found on FreeSWITCH anymore\"}");

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);
        Event event = call.getEventById("id1");

        assertThat(event.getId(), equalTo("ce-hsdbdbdhd"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111/events/id1"));
    }

    @Test
    public void shouldGetRecordingsList() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[\n" +
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

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);
        List<Recording> recordings = call.getRecordings();

        assertThat(recordings.size(), equalTo(2));
        assertThat(recordings.get(0).getId(), equalTo("Id1"));
        assertThat(recordings.get(0).getMedia(), equalTo("https://.../v1/users/.../media/{callId}-1.wav"));
        assertThat(recordings.get(0).getState(), equalTo("complete"));
        assertThat(recordings.get(0).getCall(), equalTo("https://.../v1/users/.../calls/{callId}"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111/recordings"));
    }

    @Test
    public void shouldCreateGather() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);
        call.callGatherBuilder().maxDigits(5).promptSentence("Hello").create();

        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111/gather"));
        assertThat(mockRestDriver.requests.get(0).params.get("maxDigits").toString(), equalTo("5"));
    }

    @Test
    public void shouldGetGatherById() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = (JSONObject) new JSONParser().parse("{\n" +
                        "  \"id\": \"gtr-kj4xloaq5vbpfxyeypndgxa\",\n" +
                        "  \"state\": \"completed\",\n" +
                        "  \"reason\": \"max-digits\",\n" +
                        "  \"createdTime\": \"2014-02-12T19:33:56Z\",\n" +
                        "  \"completedTime\": \"2014-02-12T19:33:59Z\",\n" +
                        "  \"call\": \"https://api.catapult.inetwork.com/v1/users/u-xa2n3oxk6it4efbglisna6a/calls/c-isw3qup6gvr3ywcsentygnq\",\n" +
                        "  \"digits\": \"123\"\n" +
                        "}");

        Call call = new Call(mockRestDriver, "parentUri", jsonObject);
        Gather gather = call.getGatherById("gtr-kj4xloaq5vbpfxyeypndgxa");

        assertThat(gather.getId(), equalTo("gtr-kj4xloaq5vbpfxyeypndgxa"));
        assertThat(mockRestDriver.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/c-11111111111111111111111/gather/gtr-kj4xloaq5vbpfxyeypndgxa"));
    }
}