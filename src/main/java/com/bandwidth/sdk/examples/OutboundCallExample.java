package com.bandwidth.sdk.examples;

import java.util.Map;
import java.util.HashMap;

import com.bandwidth.sdk.model.Call;

/**
 * This example shows how to make an outbound call using the sdk. It dials a number and speaks a sentence. 
 * 
 * Note that this does not implement an event server. See java-bandwidth-examples for a full event server.
 * 
 * @author smitchell
 *
 */
public class OutboundCallExample {

	/**
	 * @param args the args.
	 * @throws Exception error.
	 */
	public static void main(final String[] args) throws Exception{
        // There are two ways to set your creds, e.g. your App Platform userId, api token and api secret
		// you can set these as environment variables or set them with the 
		// BandwidthClient.getInstance(userId, apiToken, apiSecret) method.
		// 
		// Use the setenv.sh script to set the env variables
    	// BANDWIDTH_USER_ID
    	// BANDWIDTH_API_TOKEN
    	// BANDWIDTH_API_SECRET
		// 
		// or uncomment this line and set them here
		// BandwidthClient.getInstance(userId, apiToken, apiSecret);
		
		// put your numbers in here
		final String toNumber = "+1";// your phone number here
		final String fromNumber = "+1";// this is a number that is allocated on the AppPlatform. You can do this
							 // via the dev console or with the SDK (see AllocateNumberExample)
		
		final Call call = Call.create(toNumber, fromNumber);
		
		System.out.println("call:" + call);
		
		//wait a few seconds here. Note that in a real application you'll handle the answer event
		// in your event server. See java-bandwidth-examples for how to do this
		try {
		    Thread.sleep(10000);                 
		} catch(final InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		final Map<String, Object>params = new HashMap<String, Object>();
		params.put("sentence", "Hey there! Welcome to the App Platform!");
		params.put("voice", "kate"); // she's one of our favorites!
		call.speakSentence(params);
		
		
		//wait a few more seconds to let the message play. Again in a real application this would be part 
		// of your event handling. See java-bandwidth-examples for how to do this
		try {
		    Thread.sleep(4000);                 
		} catch(final InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		System.out.println("Updated call:" + call);
		call.hangUp();
	}

}
