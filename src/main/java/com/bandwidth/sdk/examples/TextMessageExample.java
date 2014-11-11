package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.model.Account;
import com.bandwidth.sdk.model.Message;

/**
 * This examples shows how to send a text message using the sdk
 * @author smitchell
 *
 */
public class TextMessageExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        // There are two ways to set your creds, e.g. your App Platform userId, api token and api secret
		// you can set these as environment variables or set them with the 
		// BandwidthClient.getInstance(userId, apiToken, apiSecret) method.
		// 
		// Use the setenv.sh script to set the env variables
    	// BANDWIDTH_APPPLATFORM_USER_ID
    	// BANDWIDTH_APPPLATFORM_API_TOKEN
    	// BANDWIDTH_APPPLATFORM_API_SECRET
		// 
		// or uncomment this line and set them here
		// BandwidthClient.getInstance(userId, apiToken, apiSecret);
		
		// put your numbers in here
		String toNumber = "+1";// your phone number here
		String fromNumber = "+1";// this is a number that is allocated on the AppPlatform. You can do this
							 // via the dev console or with the SDK (see AllocateNumberExample)
		
		try {

            Message message = Message.create(toNumber, fromNumber, "Test, test! What up from App Platform");
			
			System.out.println("message:" + message);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
