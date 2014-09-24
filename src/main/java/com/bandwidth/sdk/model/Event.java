package com.bandwidth.sdk.model;

/**
 * Interface for Event. Implements the 'item' inteface of vistor pattern.
 * @author smitchell
 *
 */
public interface Event {
	public void execute(Visitor v);

}
