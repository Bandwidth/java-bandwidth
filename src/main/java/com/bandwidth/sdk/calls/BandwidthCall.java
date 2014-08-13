package com.bandwidth.sdk.calls;

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
public class BandwidthCall {

    private final BandwidthRestClient client;

    private String id;

    private Direction direction;
    private State state;

    private String from;
    private String to;
    private String callbackUrl;
    private String events;

    private Date startTime;
    private Date activeTime;
    private Date endTime;

    private Long chargeableDuration;
    private boolean recordingEnabled;

    public static BandwidthCall from(BandwidthRestClient client, JSONObject jsonObject) {
        BandwidthCall call = new BandwidthCall(client);
        updateProperties(jsonObject, call);
        return call;
    }

    private static void updateProperties(JSONObject jsonObject, BandwidthCall call) {
        call.id = (String) jsonObject.get("id");
        call.direction = Direction.valueOf((String) jsonObject.get("direction"));
        call.state = State.byName((String) jsonObject.get("state"));
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

    private BandwidthCall(BandwidthRestClient client) {
        this.client = client;
    }

    public String getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }

    public State getState() {
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

    public List<BandwidthEvent> getEventsList() throws IOException {
        JSONArray array = client.getRestDriver().requestCallEvents(id);

        List<BandwidthEvent> list = new ArrayList<BandwidthEvent>();
        for (Object object : array) {
            list.add(BandwidthEvent.from((JSONObject) object));
        }
        return list;
    }

    public BandwidthEvent getEventById(String eventId) throws IOException {
        JSONObject jsonObject = client.getRestDriver().requestCallEventById(id, eventId);
        return BandwidthEvent.from(jsonObject);
    }

    public void hangUp() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", State.completed.name());

        client.getRestDriver().updateCall(id, params);

        JSONObject jsonObject = client.getRestDriver().requestCallById(id);
        updateProperties(jsonObject, this);
    }

    public void answerOnIncoming() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", State.active.name());

        client.getRestDriver().updateCall(id, params);

        JSONObject jsonObject = client.getRestDriver().requestCallById(id);
        updateProperties(jsonObject, this);
    }

    public void rejectIncoming() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", State.rejected.name());

        client.getRestDriver().updateCall(id, params);

        JSONObject jsonObject = client.getRestDriver().requestCallById(id);
        updateProperties(jsonObject, this);
    }

    public void recordingOn() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recordingEnabled", "true");

        client.getRestDriver().updateCall(id, params);

        JSONObject jsonObject = client.getRestDriver().requestCallById(id);
        updateProperties(jsonObject, this);
    }

    public void recordingOff() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recordingEnabled", "false");

        client.getRestDriver().updateCall(id, params);

        JSONObject jsonObject = client.getRestDriver().requestCallById(id);
        updateProperties(jsonObject, this);
    }

    private void transfer(Map<String, Object> params) throws IOException {
        params.put("state", State.transferring.name());
        client.getRestDriver().updateCall(id, params);

        JSONObject jsonObject = client.getRestDriver().requestCallById(id);
        updateProperties(jsonObject, this);
    }

    public TransferBuilder transfer(String transferTo) {
        return new TransferBuilder(transferTo);
    }

    public AudioBuilder createAudio() {
        return new AudioBuilder();
    }

    public void stopAudioFilePlaying() throws IOException {
        new AudioBuilder().fileUrl(StringUtils.EMPTY).commit();
    }

    public void stopSentence() throws IOException {
        new AudioBuilder().sentence(StringUtils.EMPTY).commit();
    }

    private void saveAudio(Map<String, Object> params) throws IOException {
        client.getRestDriver().createCallAudio(getId(), params);
    }

    public void sendDtmf(String dtmf) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dtmfOut", dtmf);
        client.getRestDriver().sendCallDtmf(getId(), params);
    }

    @Override
    public String toString() {
        return "BandwidthCall{" +
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

    public class TransferBuilder {

        private Map<String, Object> params = new HashMap<String, Object>();
        private Map<String, Object> whisperAudio = new HashMap<String, Object>();

        public TransferBuilder(String number) {
            params.put("transferTo", number);
        }

        public TransferBuilder callbackUrl(String callbackUrl) {
            params.put("callbackUrl", callbackUrl);
            return this;
        }

        public TransferBuilder transferCallerId(String transferCallerId) {
            params.put("transferCallerId", transferCallerId);
            return this;
        }

        public TransferBuilder sentence(String sentence) {
            whisperAudio.put("sentence", sentence);
            return this;
        }

        public TransferBuilder gender(Gender gender) {
            whisperAudio.put("gender", gender.name());
            return this;
        }

        public TransferBuilder voice(String voice) {
            whisperAudio.put("voice", voice);
            return this;
        }

        public TransferBuilder locale(SentenceLocale locale) {
            whisperAudio.put("locale", locale);
            return this;
        }

        public void commit() throws IOException {
            if (!whisperAudio.isEmpty()) {
                params.put("whisperAudio", whisperAudio);
            }
            transfer(params);
        }
    }

    public class AudioBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public AudioBuilder fileUrl(String fileUrl) {
            params.put("fileUrl", fileUrl);
            return this;
        }

        public AudioBuilder sentence(String sentence) {
            params.put("sentence", sentence);
            return this;
        }

        public AudioBuilder gender(Gender gender) {
            params.put("gender", gender.name());
            return this;
        }

        public AudioBuilder locale(SentenceLocale locale) {
            params.put("locale", locale.restValue);
            return this;
        }

        public AudioBuilder voice(String voice) {
            params.put("voice", voice);
            return this;
        }

        public AudioBuilder loopEnabled(boolean loopEnabled) {
            params.put("loopEnabled", String.valueOf(loopEnabled));
            return this;
        }

        public void commit() throws IOException {
            saveAudio(params);
        }
    }
}
