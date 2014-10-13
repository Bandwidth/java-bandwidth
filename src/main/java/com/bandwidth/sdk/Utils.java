package com.bandwidth.sdk;

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Utils {

	public static JSONArray response2JSONArray(RestResponse response) throws IOException {
		if (response.isJson() && response.getResponseText() != null) {
			
			System.out.println("responseText:" + response.getResponseText());
			
			// TODO this will throw an exception if there is only one object in the JSON.
			// need to refactor to generate an array of one if there is a single object.
			try {
				return (JSONArray) new JSONParser().parse(response
						.getResponseText());
			} 
			catch (org.json.simple.parser.ParseException e) {
				throw new IOException(e);
			}
		} 
		else {
			throw new IOException("Response is not a JSON format.");
		}

	}

}
