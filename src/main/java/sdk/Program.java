package sdk;

/**
 * Created by dtolb on 2/23/17.
 */
public class Program {
    public static void main(String[] args) {

        new Client("u-123", "t-213", "s");

        Message message = Message.builder()
                .withFromNumber("+19197891123")
                .withToNumber("+19198282233")
                .withText("Sup, this be a test")
                .build();

        System.out.println(message.toString());
//        Message{
//            toNumber=+19198282233,
//            text=Sup,
//            this be a test,
//            callbackUrl=null,
//            fallbackUrl=null,
//            tag=null,
//            fromNumber=+19197891123,
//            callbackTimeout=null,
//            media=null,
//            receiptRequested=null,
//            callbackHttpMethod=null
//        }

        try {
            /* Makes API Reqest to send message */
            String messageId = Client.Messages.send(message);
            /* There is probably a way to extend Message from above to fill in
            Extra information that is retrieved when `GET` vs `POST`
             */
            /* Makes API Request to get message */
            MessageData messageData = Client.Messages.fetch(messageId);
            System.out.println(messageData.toString());
//        MessageData{
//            id=m-123,
//            direction=out,
//            state=sent,
//            time=2017-02-27T09:08:43Z,
//            deliveryState=null,
//            deilveryCode=null,
//            deliveryDescription=null,
//            toNumber=+19198282233,
//            text=Sup,
//            this be a test,
//            callbackUrl=null,
//            fallbackUrl=null,
//            tag=null,
//            fromNumber=+19197891123,
//            callbackTimeout=null,
//            media=null,
//            receiptRequested=null,
//            callbackHttpMethod=null
//        }
        }
        catch(final Exception e) {
            System.out.println(e);
        }

    }
}
