package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Information about a specific bridge
 *
 * @author vpotapenko
 */
public class Bridge extends BaseModelObject {

    public Bridge(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
    }

    /**
     * Gets list of calls that are on the bridge
     *
     * @return list of calls
     * @throws IOException
     */
    public List<Call> getBridgeCalls() throws IOException {
        String callsPath = StringUtils.join(new String[]{
                getUri(),
                "calls"
        }, '/');
        JSONArray jsonArray = client.getArray(callsPath, null);

        List<Call> callList = new ArrayList<Call>();
        for (Object obj : jsonArray) {
            callList.add(new Call(client, callsPath, (JSONObject) obj));
        }
        return callList;
    }

    /**
     * Sets call ids
     *
     * @param callIds new value
     */
    public void setCallIds(String[] callIds) {
        putProperty("callIds", Arrays.asList(callIds));
    }

    /**
     * Sets bridge audio
     * @param bridgeAudio new value
     */
    public void setBridgeAudio(boolean bridgeAudio) {
        putProperty("bridgeAudio", bridgeAudio);
    }

    /**
     * Makes changes ob the bridge.
     *
     * @throws IOException
     */
    public void commit() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("bridgeAudio", isBridgeAudio());
        String[] callIds = getCallIds();
        params.put("callIds", callIds == null ? Collections.emptyList() : Arrays.asList(callIds));

        client.post(getUri(), params);
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

    /**
     * Creates new builder for playing an audio file or speaking a sentence in a bridge.
     * <br>Example:<br>
     * <code>bridge.newBridgeAudioBuilder().sentence("Hello").create();</code>
     *
     * @return new builder
     */
    public NewBridgeAudioBuilder newBridgeAudioBuilder() {
        return new NewBridgeAudioBuilder();
    }

    /**
     * Stop an audio file playing.
     *
     * @throws IOException
     */
    public void stopAudioFilePlaying() throws IOException {
        new NewBridgeAudioBuilder().fileUrl(StringUtils.EMPTY).create();
    }

    /**
     * Stop an audio sentence.
     *
     * @throws IOException
     */
    public void stopSentence() throws IOException {
        new NewBridgeAudioBuilder().sentence(StringUtils.EMPTY).create();
    }

    private void createAudio(Map<String, Object> params) throws IOException {
        String audioPath = StringUtils.join(new String[]{
                getUri(),
                "audio"
        }, '/');
        client.post(audioPath, params);
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
