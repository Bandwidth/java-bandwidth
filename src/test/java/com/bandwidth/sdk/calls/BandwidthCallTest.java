package com.bandwidth.sdk.calls;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class BandwidthCallTest {

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");
        BandwidthCall call = BandwidthCall.from(null, jsonObject);

        assertThat(call.getId(), equalTo("c-11111111111111111111111"));
        assertThat(call.getState(), equalTo(State.completed));
        assertThat(call.getEvents(), equalTo("https://api.catapult.inetwork.com/v1/users/calls/events"));
        assertThat(call.getChargeableDuration(), equalTo(300l));
        assertThat(call.getDirection(), equalTo(Direction.in));
        assertThat(call.getFrom(), equalTo("+22222222222"));
        assertThat(call.getTo(), equalTo("+11111111111"));
        assertThat(call.isRecordingEnabled(), equalTo(true));
    }

    @Test
    public void shouldHangUp() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        BandwidthRestClient client = new BandwidthRestClient("", "", "");
        client.setRestDriver(mockRestDriver);

        BandwidthCall call = BandwidthCall.from(client, jsonObject);

        call.hangUp();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateCall"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("completed"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("requestCallById"));
    }

    @Test
    public void shouldAnswerOnIncoming() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        BandwidthRestClient client = new BandwidthRestClient("", "", "");
        client.setRestDriver(mockRestDriver);

        BandwidthCall call = BandwidthCall.from(client, jsonObject);

        call.answerOnIncoming();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateCall"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("active"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("requestCallById"));
    }

    @Test
    public void shouldRejectIncoming() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        BandwidthRestClient client = new BandwidthRestClient("", "", "");
        client.setRestDriver(mockRestDriver);

        BandwidthCall call = BandwidthCall.from(client, jsonObject);

        call.rejectIncoming();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateCall"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("rejected"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("requestCallById"));
    }

    @Test
    public void shouldSwitchRecordingState() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        BandwidthRestClient client = new BandwidthRestClient("", "", "");
        client.setRestDriver(mockRestDriver);

        BandwidthCall call = BandwidthCall.from(client, jsonObject);

        call.recordingOn();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateCall"));
        assertThat(mockRestDriver.requests.get(0).params.get("recordingEnabled").toString(), equalTo("true"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("requestCallById"));

        mockRestDriver.requests.clear();
        call.recordingOff();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateCall"));
        assertThat(mockRestDriver.requests.get(0).params.get("recordingEnabled").toString(), equalTo("false"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("requestCallById"));
    }

    @Test
    public void shouldTransferCall() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        BandwidthRestClient client = new BandwidthRestClient("", "", "");
        client.setRestDriver(mockRestDriver);

        BandwidthCall call = BandwidthCall.from(client, jsonObject);

        call.transfer("8917727272").callbackUrl("url").commit();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateCall"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("transferring"));
        assertThat(mockRestDriver.requests.get(0).params.get("transferTo").toString(), equalTo("8917727272"));
        assertThat(mockRestDriver.requests.get(0).params.get("callbackUrl").toString(), equalTo("url"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("requestCallById"));

        mockRestDriver.requests.clear();
        call.transfer("8917727272").commit();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateCall"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("transferring"));
        assertThat(mockRestDriver.requests.get(0).params.get("transferTo").toString(), equalTo("8917727272"));
        assertThat(mockRestDriver.requests.get(0).params.get("callbackUrl"), nullValue());
        assertThat(mockRestDriver.requests.get(1).name, equalTo("requestCallById"));

        mockRestDriver.requests.clear();
        call.transfer("8917727272").gender(Gender.male).locale(SentenceLocale.French).sentence("Hello").transferCallerId("callerId").commit();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateCall"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("transferring"));
        assertThat(mockRestDriver.requests.get(0).params.get("transferTo").toString(), equalTo("8917727272"));
        assertThat(mockRestDriver.requests.get(0).params.get("transferCallerId").toString(), equalTo("callerId"));
        assertThat(((Map<String, Object>) mockRestDriver.requests.get(0).params.get("whisperAudio")).get("sentence").toString(), equalTo("Hello"));
        assertThat(mockRestDriver.requests.get(1).name, equalTo("requestCallById"));
    }

    @Test
    public void shouldCreateAudio() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https://api.catapult.inetwork.com/v1/users/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https://api.catapult.inetwork.com/v1/users/calls/events\",\"chargeableDuration\":300,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:22:54Z\",\"id\":\"c-11111111111111111111111\",\"recordingEnabled\":true,\"startTime\":\"2014-08-12T10:17:54Z\",\"activeTime\":\"2014-08-12T10:17:54Z\",\"transcriptions\":\"https://api.catapult.inetwork.com/v1/users/transcriptions\"}");

        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        BandwidthRestClient client = new BandwidthRestClient("", "", "");
        client.setRestDriver(mockRestDriver);

        BandwidthCall call = BandwidthCall.from(client, jsonObject);
        call.createAudio().fileUrl("url").commit();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("createCallAudio"));
        assertThat(mockRestDriver.requests.get(0).params.get("fileUrl").toString(), equalTo("url"));

        mockRestDriver.requests.clear();
        call.stopSentence();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("createCallAudio"));
        assertThat(mockRestDriver.requests.get(0).params.get("sentence").toString(), equalTo(""));

        mockRestDriver.requests.clear();
        call.stopAudioFilePlaying();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("createCallAudio"));
        assertThat(mockRestDriver.requests.get(0).params.get("fileUrl").toString(), equalTo(""));
    }
}