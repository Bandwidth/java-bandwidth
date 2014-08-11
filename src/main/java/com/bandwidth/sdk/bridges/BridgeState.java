package com.bandwidth.sdk.bridges;

/**
 * @author vpotapenko
 */
public enum BridgeState {

    created,
    active,
    hold,
    completed,
    error;

    public static BridgeState from(String stateStr) {
        if (stateStr == null) return created;

        try {
            return valueOf(stateStr);
        } catch (Exception e) {
            return created;
        }
    }
}
