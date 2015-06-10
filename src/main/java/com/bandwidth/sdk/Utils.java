package com.bandwidth.sdk;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Utils {

	public static JSONArray response2JSONArray(final RestResponse response) throws IOException {
		if (response != null && response.isJson() && response.getResponseText() != null) {
			try {
			    final Object parsedContent = new JSONParser().parse(response.getResponseText());
				if(parsedContent instanceof JSONObject) {
				    final JSONArray jsonArray = new JSONArray();
				    jsonArray.add(parsedContent);
				    return jsonArray;
				} else {
				    return (JSONArray) new JSONParser().parse(response.getResponseText());
				}
			} 
			catch (final org.json.simple.parser.ParseException e) {
				throw new IOException(e);
			}
		} 
		else {
			throw new IOException("Response is not a JSON format.");
		}
	}
}