
        package bandwidth.com.example;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import com.fasterxml.jackson.annotation.JsonAnyGetter;
        import com.fasterxml.jackson.annotation.JsonAnySetter;
        import com.fasterxml.jackson.annotation.JsonIgnore;
        import com.fasterxml.jackson.annotation.JsonInclude;
        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "direction",
        "from",
        "id",
        "messageId",
        "state",
        "media",
        "time",
        "to",
        "skipMMSCarrierValidation"
})
public class Message {

    @JsonProperty("direction")
    private String direction;
    @JsonProperty("from")
    private String from;
    @JsonProperty("id")
    private String id;
    @JsonProperty("messageId")
    private String messageId;
    @JsonProperty("state")
    private String state;
    @JsonProperty("media")
    private List<Object> media = null;
    @JsonProperty("time")
    private String time;
    @JsonProperty("to")
    private String to;
    @JsonProperty("skipMMSCarrierValidation")
    private boolean skipMMSCarrierValidation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Message() {
    }

    /**
     *
     * @param to
     * @param id
     * @param skipMMSCarrierValidation
     * @param time
     * @param direction
     * @param state
     * @param from
     * @param messageId
     * @param media
     */
    public Message(String direction, String from, String id, String messageId, String state, List<Object> media, String time, String to, boolean skipMMSCarrierValidation) {
        super();
        this.direction = direction;
        this.from = from;
        this.id = id;
        this.messageId = messageId;
        this.state = state;
        this.media = media;
        this.time = time;
        this.to = to;
        this.skipMMSCarrierValidation = skipMMSCarrierValidation;
    }

    @JsonProperty("direction")
    public String getDirection() {
        return direction;
    }

    @JsonProperty("direction")
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Message withDirection(String direction) {
        this.direction = direction;
        return this;
    }

    @JsonProperty("from")
    public String getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(String from) {
        this.from = from;
    }

    public Message withFrom(String from) {
        this.from = from;
        return this;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Message withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("messageId")
    public String getMessageId() {
        return messageId;
    }

    @JsonProperty("messageId")
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Message withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public Message withState(String state) {
        this.state = state;
        return this;
    }

    @JsonProperty("media")
    public List<Object> getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(List<Object> media) {
        this.media = media;
    }

    public Message withMedia(List<Object> media) {
        this.media = media;
        return this;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    public Message withTime(String time) {
        this.time = time;
        return this;
    }

    @JsonProperty("to")
    public String getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(String to) {
        this.to = to;
    }

    public Message withTo(String to) {
        this.to = to;
        return this;
    }

    @JsonProperty("skipMMSCarrierValidation")
    public boolean isSkipMMSCarrierValidation() {
        return skipMMSCarrierValidation;
    }

    @JsonProperty("skipMMSCarrierValidation")
    public void setSkipMMSCarrierValidation(boolean skipMMSCarrierValidation) {
        this.skipMMSCarrierValidation = skipMMSCarrierValidation;
    }

    public Message withSkipMMSCarrierValidation(boolean skipMMSCarrierValidation) {
        this.skipMMSCarrierValidation = skipMMSCarrierValidation;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Message withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}