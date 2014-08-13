package com.bandwidth.sdk.calls;

/**
 * @author vpotapenko
 */
public enum SentenceLocale {

    English_US("en_US"),
    English_UK("en_UK"),
    Spain("es_MX"),
    French("fr_FR"),
    German("de_DE"),
    Italian("it_IT");

    public final String restValue;

    SentenceLocale(String restValue) {
        this.restValue = restValue;
    }
}
