package com.bandwidth.sdk.model;

/**
 * Interface for all Events. Implements the 'item' inteface of vistor pattern.
 * @author smitchell
 *
 */
public interface Event {
	public void execute(Visitor v);
    public String getProperty(String property);
    public void setProperty(String name, String value);
    public EventType getEventType();
}
