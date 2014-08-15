package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author vpotapenko
 */
public class Call {

    private final BandwidthRestClient client;

    private String id;

    private CallDirection direction;
    private CallState state;

    private String from;
    private String to;
    private String callbackUrl;
    private String events;

    private Date startTime;
    private Date activeTime;
    private Date endTime;

    private Long chargeableDuration;
    private boolean recordingEnabled;

    public static Call from(BandwidthRestClient client, JSONObject jsonObject) {
        Call call = new Call(client);
        updateProperties(jsonObject, call);
        return call;
    }

    private static void updateProperties(JSONObject jsonObject, Call call) {
        call.id = (String) jsonObject.get("id");
        call.direction = CallDirection.valueOf((String) jsonObject.get("direction"));
        call.state = CallState.byName((String) jsonObject.get("state"));
        call.from = (String) jsonObject.get("from");
        call.to = (String) jsonObject.get("to");
        call.callbackUrl = (String) jsonObject.get("callbackUrl");
        call.events = (String) jsonObject.get("events");
        call.chargeableDuration = (Long) jsonObject.get("chargeableDuration");
        call.recordingEnabled = Objects.equals(jsonObject.get("recordingEnabled"), Boolean.TRUE);

        SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
        try {
            String time = (String) jsonObject.get("startTime");
            if (StringUtils.isNotEmpty(time)) call.startTime = dateFormat.parse(time);

            time = (String) jsonObject.get("activeTime");
            if (StringUtils.isNotEmpty(time)) call.activeTime = dateFormat.parse(time);

            time = (String) jsonObject.get("endTime");
            if (StringUtils.isNotEmpty(time)) call.endTime = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Call(BandwidthRestClient client) {
        this.client = client;
    }

    public String getId() {
        return id;
    }

    public CallDirection getDirection() {
        return direction;
    }

    public CallState getState() {
        return state;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getEvents() {
        return events;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Long getChargeableDuration() {
        return chargeableDuration;
    }

    public boolean isRecordingEnabled() {
        return recordingEnabled;
    }

    public List<Recording> getRecordings() throws IOException {
        JSONArray array = client.requestCallRecordings(id);

        List<Recording> list = new ArrayList<Recording>();
        for (Object object : array) {
            list.add(Recording.from((JSONObject) object));
        }
        return list;
    }

    public List<Event> getEventsList() throws IOException {
        JSONArray array = client.requestCallEvents(id);

        List<Event> list = new ArrayList<Event>();
        for (Object object : array) {
            list.add(Event.from((JSONObject) object));
        }
        return list;
    }

    public Event getEventById(String eventId) throws IOException {
        JSONObject jsonObject = client.requestCallEventById(id, eventId);
        return Event.from(jsonObject);
    }

    public void hangUp() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", CallState.completed.name());

        client.updateCall(id, params);

        JSONObject jsonObject = client.requestCallById(id);
        updateProperties(jsonObject, this);
    }

    public void answerOnIncoming() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", CallState.active.name());

        client.updateCall(id, params);

        JSONObject jsonObject = client.requestCallById(id);
        updateProperties(jsonObject, this);
    }

    public void rejectIncoming() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", CallState.rejected.name());

        client.updateCall(id, params);

        JSONObject jsonObject = client.requestCallById(id);
        updateProperties(jsonObject, this);
    }

    public void recordingOn() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recordingEnabled", "true");

        client.updateCall(id, params);

        JSONObject jsonObject = client.requestCallById(id);
        updateProperties(jsonObject, this);
    }

    public void recordingOff() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recordingEnabled", "false");

        client.updateCall(id, params);

        JSONObject jsonObject = client.requestCallById(id);
        updateProperties(jsonObject, this);
    }

    private void transfer(Map<String, Object> params) throws IOException {
        params.put("state", CallState.transferring.name());
        client.updateCall(id, params);

        JSONObject jsonObject = client.requestCallById(id);
        updateProperties(jsonObject, this);
    }

    public CallTransferBuilder callTransferBuilder(String transferTo) {
        return new CallTransferBuilder(transferTo);
    }

    public CallAudioBuilder callAudioBuilder() {
        return new CallAudioBuilder();
    }

    public void stopAudioFilePlaying() throws IOException {
        new CallAudioBuilder().fileUrl(StringUtils.EMPTY).create();
    }

    public void stopSentence() throws IOException {
        new CallAudioBuilder().sentence(StringUtils.EMPTY).create();
    }

    private void createCallAudio(Map<String, Object> params) throws IOException {
        client.createCallAudio(getId(), params);
    }

    public void sendDtmf(String dtmf) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dtmfOut", dtmf);
        client.sendCallDtmf(id, params);
    }

    public CallGatherBuilder callGatherBuilder() {
        return new CallGatherBuilder();
    }

    private void createGather(Map<String, Object> params) throws IOException {
        client.createCallGather(id, params);
    }

    public Gather getGatherById(String gatherId) throws IOException{
        JSONObject jsonObject = client.requestCallGatherById(id, gatherId);
        return Gather.from(client, id, jsonObject);
    }

    @Override
    public String toString() {
        return "Call{" +
                "id='" + id + '\'' +
                ", direction=" + direction +
                ", state=" + state +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", events='" + events + '\'' +
                ", startTime=" + startTime +
                ", activeTime=" + activeTime +
                ", endTime=" + endTime +
                ", chargeableDuration=" + chargeableDuration +
                ", recordingEnabled=" + recordingEnabled +
                '}';
    }

    public class CallTransferBuilder {

        private Map<String, Object> params = new HashMap<String, Object>();
        private Map<String, Object> whisperAudio = new HashMap<String, Object>();

        public CallTransferBuilder(String number) {
            params.put("transferTo", number);
        }

        public CallTransferBuilder callbackUrl(String callbackUrl) {
            params.put("callbackUrl", callbackUrl);
            return this;
        }

        public CallTransferBuilder transferCallerId(String transferCallerId) {
            params.put("transferCallerId", transferCallerId);
            return this;
        }

        public CallTransferBuilder sentence(String sentence) {
            whisperAudio.put("sentence", sentence);
            return this;
        }

        public CallTransferBuilder gender(Gender gender) {
            whisperAudio.put("gender", gender.name());
            return this;
        }

        public CallTransferBuilder voice(String voice) {
            whisperAudio.put("voice", voice);
            return this;
        }

        public CallTransferBuilder locale(SentenceLocale locale) {
            whisperAudio.put("locale", locale);
            return this;
        }

        public void create() throws IOException {
            if (!whisperAudio.isEmpty()) {
                params.put("whisperAudio", whisperAudio);
            }
            transfer(params);
        }
    }

    public class CallAudioBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public CallAudioBuilder fileUrl(String fileUrl) {
            params.put("fileUrl", fileUrl);
            return this;
        }

        public CallAudioBuilder sentence(String sentence) {
            params.put("sentence", sentence);
            return this;
        }

        public CallAudioBuilder gender(Gender gender) {
            params.put("gender", gender.name());
            return this;
        }

        public CallAudioBuilder locale(SentenceLocale locale) {
            params.put("locale", locale.restValue);
            return this;
        }

        public CallAudioBuilder voice(String voice) {
            params.put("voice", voice);
            return this;
        }

        public CallAudioBuilder loopEnabled(boolean loopEnabled) {
            params.put("loopEnabled", String.valueOf(loopEnabled));
            return this;
        }

        public void create() throws IOException {
            createCallAudio(params);
        }
    }

    public class CallGatherBuilder {

        private Map<String, Object> params = new HashMap<String, Object>();
        private Map<String, Object> prompt = new HashMap<String, Object>();

        public void create() throws IOException {
            if (!prompt.isEmpty()) params.put("prompt", prompt);

            createGather(params);
        }

        public CallGatherBuilder maxDigits(int maxDigits) {
            params.put("maxDigits", String.valueOf(maxDigits));
            return this;
        }

        public CallGatherBuilder interDigitTimeout(int maxDigits) {
            params.put("interDigitTimeout", String.valueOf(maxDigits));
            return this;
        }

        public CallGatherBuilder terminatingDigits(String terminatingDigits) {
            params.put("terminatingDigits", terminatingDigits);
            return this;
        }

        public CallGatherBuilder suppressDtmf(boolean suppressDtmf) {
            params.put("suppressDtmf", String.valueOf(suppressDtmf));
            return this;
        }

        public CallGatherBuilder tag(String tag) {
            params.put("tag", tag);
            return this;
        }

        public CallGatherBuilder promptSentence(String promptSentence) {
            prompt.put("sentence", promptSentence);
            return this;
        }

        public CallGatherBuilder promptGender(Gender gender) {
            prompt.put("gender", gender.name());
            return this;
        }

        public CallGatherBuilder promptLocale(SentenceLocale locale) {
            prompt.put("locale", locale.restValue);
            return this;
        }

        public CallGatherBuilder promptFileUrl(String promptFileUrl) {
            prompt.put("fileUrl", promptFileUrl);
            return this;
        }

        public CallGatherBuilder promptLoopEnabled(boolean promptLoopEnabled) {
            prompt.put("loopEnabled", String.valueOf(promptLoopEnabled));
            return this;
        }

        public CallGatherBuilder promptBargeable(boolean promptBargeable) {
            prompt.put("bargeable", String.valueOf(promptBargeable));
            return this;
        }


    }
}
