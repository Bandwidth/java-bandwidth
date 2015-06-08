package com.bandwidth.sdk.model;

public enum ReceiptRequest {

    NONE("none"),
    ERROR("error"),
    ALL("all");

    private String val;

    private ReceiptRequest(final String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }

    public ReceiptRequest getEnum(final String val) {
        if (NONE.toString().equalsIgnoreCase(val)) {
            return NONE;
        } else if (ERROR.toString().equalsIgnoreCase(val)) {
            return ERROR;
        } else if (ALL.toString().equalsIgnoreCase(val)) {
            return ALL;
        }
        return null;
    }

}
