package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * @author vpotapenko
 */
public class Call extends BaseModelObject {

    public Call(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
    }

    public String getDirection() {
        return getPropertyAsString("direction");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public String getFrom() {
        return getPropertyAsString("from");
    }

    public String getTo() {
        return getPropertyAsString("to");
    }

    public String getCallbackUrl() {
        return getPropertyAsString("callbackUrl");
    }

    public String getEvents() {
        return getPropertyAsString("events");
    }

    public Date getStartTime() {
        return getPropertyAsDate("startTime");
    }

    public Date getActiveTime() {
        return getPropertyAsDate("activeTime");
    }

    public Date getEndTime() {
        return getPropertyAsDate("endTime");
    }

    public Long getChargeableDuration() {
        return getPropertyAsLong("chargeableDuration");
    }

    public boolean isRecordingEnabled() {
        return getPropertyAsBoolean("recordingEnabled");
    }

    public List<Recording> getRecordings() throws IOException {
        String recordingsPath = StringUtils.join(new String[]{
                getUri(),
                "recordings"
        }, '/');
        JSONArray array = driver.getArray(recordingsPath, null);

        List<Recording> list = new ArrayList<Recording>();
        for (Object object : array) {
            list.add(new Recording(driver, recordingsPath, (JSONObject) object));
        }
        return list;
    }

    public List<Event> getEventsList() throws IOException {
        String eventsPath = StringUtils.join(new String[]{
                getUri(),
                "events"
        }, '/');
        JSONArray array = driver.getArray(eventsPath, null);

        List<Event> list = new ArrayList<Event>();
        for (Object object : array) {
            list.add(new Event(driver, eventsPath, (JSONObject) object));
        }
        return list;
    }

    public Event getEventById(String eventId) throws IOException {
        String eventPath = StringUtils.join(new String[]{
                getUri(),
                "events",
                eventId
        }, '/');
        JSONObject jsonObject = driver.getObject(eventPath);
        String eventsPath = StringUtils.join(new String[]{
                getUri(),
                "events"
        }, '/');
        return new Event(driver, eventsPath, jsonObject);
    }

    public void hangUp() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "completed");

        String uri = getUri();
        driver.post(uri, params);

        JSONObject jsonObject = driver.getObject(uri);
        updateProperties(jsonObject);
    }

    public void answerOnIncoming() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "active");

        String uri = getUri();
        driver.post(uri, params);

        JSONObject jsonObject = driver.getObject(uri);
        updateProperties(jsonObject);
    }

    public void rejectIncoming() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "rejected");

        String uri = getUri();
        driver.post(uri, params);

        JSONObject jsonObject = driver.getObject(uri);
        updateProperties(jsonObject);
    }

    public void recordingOn() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recordingEnabled", "true");

        String uri = getUri();
        driver.post(uri, params);

        JSONObject jsonObject = driver.getObject(uri);
        updateProperties(jsonObject);
    }

    public void recordingOff() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recordingEnabled", "false");

        String uri = getUri();
        driver.post(uri, params);

        JSONObject jsonObject = driver.getObject(uri);
        updateProperties(jsonObject);
    }

    private void transfer(Map<String, Object> params) throws IOException {
        params.put("state", "transferring");

        String uri = getUri();
        driver.post(uri, params);

        JSONObject jsonObject = driver.getObject(uri);
        updateProperties(jsonObject);
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
        String audioPath = StringUtils.join(new String[]{
                getUri(),
                "audio"
        }, '/');
        driver.post(audioPath, params);
    }

    public void sendDtmf(String dtmf) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dtmfOut", dtmf);

        String uri = StringUtils.join(new String[]{
                getUri(),
                "dtmf"
        }, '/');
        driver.post(uri, params);
    }

    public CallGatherBuilder callGatherBuilder() {
        return new CallGatherBuilder();
    }

    private void createGather(Map<String, Object> params) throws IOException {
        String uri = StringUtils.join(new String[]{
                getUri(),
                "gather"
        }, '/');
        driver.post(uri, params);
    }

    public Gather getGatherById(String gatherId) throws IOException {
        String gatherPath = StringUtils.join(new String[]{
                getUri(),
                "gather",
                gatherId
        }, '/');
        JSONObject jsonObject = driver.getObject(gatherPath);
        String gathersPath = StringUtils.join(new String[]{
                        getUri(),
                        "events"
                }, '/');
        return new Gather(driver, gathersPath, jsonObject);
    }

    @Override
    public String toString() {
        return "Call{" +
                "id='" + getId() + '\'' +
                ", direction=" + getDirection() +
                ", state=" + getState() +
                ", from='" + getFrom() + '\'' +
                ", to='" + getTo() + '\'' +
                ", callbackUrl='" + getCallbackUrl() + '\'' +
                ", events='" + getEvents() + '\'' +
                ", startTime=" + getStartTime() +
                ", activeTime=" + getActiveTime() +
                ", endTime=" + getEndTime() +
                ", chargeableDuration=" + getChargeableDuration() +
                ", recordingEnabled=" + isRecordingEnabled() +
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
