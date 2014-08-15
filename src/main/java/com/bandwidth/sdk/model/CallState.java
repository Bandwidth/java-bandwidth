package com.bandwidth.sdk.model;

/**
 * @author vpotapenko
 */
public enum CallState {

    started,
    rejected,
    active,
    completed,
    transferring,
    error,
    undefined;

    public static CallState byName(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return undefined;
        }
    }
}
