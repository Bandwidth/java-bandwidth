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
public class Bridge extends BaseModelObject {

    public Bridge(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
    }

    public List<Call> getBridgeCalls() throws IOException {
        String callsPath = StringUtils.join(new String[]{
                getUri(),
                "calls"
        }, '/');
        JSONArray jsonArray = driver.getArray(callsPath, null);

        List<Call> callList = new ArrayList<Call>();
        for (Object obj : jsonArray) {
            callList.add(new Call(driver, callsPath, (JSONObject) obj));
        }
        return callList;
    }

    public void setCallIds(String[] callIds) {
        putProperty("callIds", Arrays.asList(callIds));
    }

    public void setBridgeAudio(boolean bridgeAudio) {
        putProperty("bridgeAudio", bridgeAudio);
    }

    public void commit() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("bridgeAudio", isBridgeAudio());
        String[] callIds = getCallIds();
        params.put("callIds", callIds == null ? Collections.emptyList() : Arrays.asList(callIds));

        driver.post(getUri(), params);
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public String[] getCallIds() {
        return getPropertyAsStringArray("callIds");
    }

    public String getCalls() {
        return getPropertyAsString("calls");
    }

    public boolean isBridgeAudio() {
        return getPropertyAsBoolean("bridgeAudio");
    }

    public Date getCompletedTime() {
        return getPropertyAsDate("completedTime");
    }

    public Date getCreatedTime() {
        return getPropertyAsDate("createdTime");
    }

    public Date getActivatedTime() {
        return getPropertyAsDate("activatedTime");
    }

    public NewBridgeAudioBuilder newBridgeAudioBuilder() {
        return new NewBridgeAudioBuilder();
    }

    public void stopAudioFilePlaying() throws IOException {
        new NewBridgeAudioBuilder().fileUrl(StringUtils.EMPTY).create();
    }

    public void stopSentence() throws IOException {
        new NewBridgeAudioBuilder().sentence(StringUtils.EMPTY).create();
    }

    private void createAudio(Map<String, Object> params) throws IOException {
        String audioPath = StringUtils.join(new String[]{
                getUri(),
                "audio"
        }, '/');
        driver.post(audioPath, params);
    }

    @Override
    public String toString() {
        return "Bridge{" +
                "id='" + getId() + '\'' +
                ", state=" + getState() +
                ", callIds=" + Arrays.toString(getCallIds()) +
                ", calls='" + getCalls() + '\'' +
                ", bridgeAudio=" + isBridgeAudio() +
                ", completedTime=" + getCompletedTime() +
                ", createdTime=" + getCreatedTime() +
                ", activatedTime=" + getActivatedTime() +
                '}';
    }

    public class NewBridgeAudioBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public NewBridgeAudioBuilder fileUrl(String fileUrl) {
            params.put("fileUrl", fileUrl);
            return this;
        }

        public NewBridgeAudioBuilder sentence(String sentence) {
            params.put("sentence", sentence);
            return this;
        }

        public NewBridgeAudioBuilder gender(Gender gender) {
            params.put("gender", gender.name());
            return this;
        }

        public NewBridgeAudioBuilder locale(SentenceLocale locale) {
            params.put("locale", locale.restValue);
            return this;
        }

        public NewBridgeAudioBuilder voice(String voice) {
            params.put("voice", voice);
            return this;
        }

        public NewBridgeAudioBuilder loopEnabled(boolean loopEnabled) {
            params.put("loopEnabled", String.valueOf(loopEnabled));
            return this;
        }

        public void create() throws IOException {
            createAudio(params);
        }
    }
}
