package com.bandwidth.sdk.calls;

/**
 * @author vpotapenko
 */
public enum State {

    started,
    rejected,
    active,
    completed,
    transferring,
    error,
    undefined;

    public static State byName(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return undefined;
        }
    }
}
