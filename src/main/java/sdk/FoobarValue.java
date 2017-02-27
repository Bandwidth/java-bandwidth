package sdk;

import java.util.List;
import java.util.Set;
import org.immutables.value.Value;

@MyStyle
@Value.Immutable
public abstract class FoobarValue {
    public abstract int foo();
    public abstract String bar();
    public abstract List<Integer> buz();
    public abstract Set<Long> crux();

    @Nullable
    public abstract String HappinessWithJava();

    public static sdk.FoobarValueBuilder builder(){
        return new sdk.FoobarValueBuilder();
    }
}