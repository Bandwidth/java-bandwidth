package sdk;

/**
 * Created by dtolb on 2/24/17.
 */
import org.immutables.value.Value;

/**
 * Created by dtolb on 2/23/17.
 */
@Value.Style(
        init = "with*",
        get = {"is*", "get*"},
        visibility = Value.Style.ImplementationVisibility.PRIVATE,
        defaults = @Value.Immutable
)
public @interface MyStyle{}
