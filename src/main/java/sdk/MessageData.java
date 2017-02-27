package sdk;

import org.immutables.value.Value;

import java.util.List;

/**
 * Created by dtolb on 2/27/17.
 */
@MyStyle
@Value.Immutable
public abstract class MessageData {
    public abstract String getId();
    public abstract String getDirection();
    public abstract String getState();
    public abstract String getTime();

    @Nullable
    public abstract String getDeliveryState();

    @Nullable
    public abstract String getDeilveryCode();

    @Nullable
    public abstract String getDeliveryDescription();

    public abstract String getToNumber();

    public abstract String getText();

    @Nullable
    public abstract String getCallbackUrl();

    @Nullable
    public abstract String getFallbackUrl();

    @Nullable
    public abstract String getTag();

    public abstract String getFromNumber();

    @Nullable
    public abstract String getCallbackTimeout();

    @Nullable
    public abstract List<String> getMedia();

    @Nullable
    public abstract String getReceiptRequested();

    @Nullable
    public abstract String getCallbackHttpMethod();

    public static sdk.MessageDataBuilder builder() {
        return new sdk.MessageDataBuilder();
    }
}
