package sdk;

/**
 * Created by dtolb on 2/23/17.
 */
public class Client {
    private String _UserId;

    private String _ApiToken;

    private String _ApiSecret;

    public Client(String UserId, String ApiToken, String ApiSecret) {
        _UserId = UserId;
        _ApiToken = ApiToken;
        _ApiSecret = ApiSecret;
    }
    public static class Messages {
        public static String send(Message message) {
            return "m-123";
        }

        public static MessageData fetch(String messageId) {
            MessageData messageData = MessageData.builder()
                    .withDirection("out")
                    .withFromNumber("+19197891123")
                    .withId("m-123")
                    .withState("sent")
                    .withText("Sup, this be a test")
                    .withTime("2017-02-27T09:08:43Z")
                    .withToNumber("+19198282233")
                    .build();
            return messageData;
        }

        // public static String send(Message message) {
        //     String retMessageId = new Message.MessageBuilder();
        //     return retMessageId;
        // }

        // public static Message fetch(Sting messageId) {
        //     message.setState("sent");
        //     return message;
        // }
    }
}
