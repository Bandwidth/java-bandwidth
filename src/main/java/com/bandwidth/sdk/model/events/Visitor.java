package com.bandwidth.sdk.model.events;

public interface Visitor
{
	public void processEvent(IncomingCallEvent event );
	public void processEvent(AnswerEvent event);
	public void processEvent(Event event);
	public void processEvent(SpeakEvent event);
	public void processEvent(PlaybackEvent event);
	public void processEvent(GatherEvent event);
	public void processEvent(HangupEvent event);
	public void processEvent(DtmfEvent event);
	public void processEvent(RejectEvent event);
	public void processEvent(RecordingEvent event);
	public void processEvent(SmsEvent event);
	public void processEvent(TimeoutEvent event);
}
