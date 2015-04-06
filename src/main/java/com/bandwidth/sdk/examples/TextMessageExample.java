package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.model.Message;

/**
 * This examples shows how to send a text message using the sdk
 * @author smitchell
 *
 */
public class TextMessageExample {

	/**
	 * @param args the args.
	 */
	public static void main(final String[] args) {
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
		
		try {

            final Message message = Message.create(toNumber, fromNumber, "Test, test! What up from App Platform");
			
			System.out.println("message:" + message);
		}
		catch(final Exception e) {
			e.printStackTrace();
		}
	}

}
