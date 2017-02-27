package sdk;

import org.immutables.value.Value;
import java.util.List;

/**
 * Created by dtolb on 2/27/17.
 */
@MyStyle
@Value.Immutable
public abstract class Message {
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

    public static sdk.MessageBuilder builder(){
        return new sdk.MessageBuilder();
    }

}
