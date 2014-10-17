/**
 * 
 */
package com.bandwidth.sdk;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import org.apache.http.HttpEntity;
import org.json.simple.JSONObject;

/**
 * @author smitchell
 * 
 */
public class RestResponse {
	protected String responseText;
	protected boolean error;
	protected String contentType;
	protected String location;
	protected int status;
	
	protected String firstLink;
	protected String lastLink;
	protected String nextLink;
	protected String previousLink;
	
	public RestResponse() {
		
	}
	
	public RestResponse(String text, int status) {
		this.responseText = text;
		this.error = (status >= 400);
		this.status = status;
	}
	
	public static RestResponse createRestResponse(HttpResponse httpResponse) {
		
		RestResponse restResponse = new RestResponse();
		
		try {

			restResponse.setStatus(httpResponse.getStatusLine().getStatusCode());
	
	        HttpEntity entity = httpResponse.getEntity();
	
	        String responseText = "";
	
	        if (entity != null) {
	            responseText = EntityUtils.toString(entity);
	        }
	        
	        if (responseText.length() == 0)	
	        	responseText = "{}";
	        
	        
	        // There are several error conditions here, this is just one. 
	        if (responseText.contains("access-denied"))
	        	restResponse.setError(true);
	        
	        restResponse.setResponseText(responseText);
	        	
	        for  (Header header : httpResponse.getHeaders("Content-Type")) {
	        	restResponse.setContentType(header.getValue());
	        }
	

	        for (Header header : httpResponse.getHeaders("Location")) {
	            restResponse.setLocation(header.getValue());
	        }
	        
	        for (Header header : httpResponse.getHeaders("Link")) {	        	
	        	restResponse.parseLinkHeader(header.getValue());
	        }
		}	
        catch (IOException e) {
        	e.printStackTrace();
        }
		
		return restResponse;
	}
	
	
	/**
	 * This parses out the Link for the first, next and prev. Link header looks like this:
	 * 
	 * <https://api.catapult.inetwork.com/v1/users/u-id/applications?page=0&size=2>; rel="first",
	 * <https://api.catapult.inetwork.com/v1/users/u-id/applications?page=0&size=2>; rel="previous",
	 * <https://api.catapult.inetwork.com/v1/users/u-id/applications?page=2&size=2>; rel="next"
	 * 
	 * 
	 * @param link
	 */
	public void parseLinkHeader(String link) {
		
		String [] links = link.split(",");
		
		for (String part : links) {
		
			String[] segments = part.split(";");
			if (segments.length < 2)
				continue;
			String linkPart = segments[0].trim();
			if (!linkPart.startsWith("<") || !linkPart.endsWith(">")) 
				continue;
			linkPart = linkPart.substring(1, linkPart.length() - 1);
			for (int i = 1; i < segments.length; i++) {
				String[] rel = segments[i].trim().split("="); 
				if (rel.length < 2 || !"rel".equals(rel[0]))
					continue;
				String relValue = rel[1];
				if (relValue.startsWith("\"") && relValue.endsWith("\"")) 
					relValue = relValue.substring(1, relValue.length() - 1);
				if ("first".equals(relValue))
					firstLink = linkPart;
				else if ("last".equals(relValue))
					lastLink = linkPart;
				else if ("next".equals(relValue))
					nextLink = linkPart;
				else if ("previous".equals(relValue))
					previousLink = linkPart;
			}
		}
	}

	public int getStatus() {
		return status;
	}

	public String getResponseText() {
		return responseText;
	}

	public boolean isError() {
		return error;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isJson() {
		return (this.contentType.toLowerCase().contains("application/json"));
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getFirstLink() {
		return firstLink;
	}

	public void setFirstLink(String firstLink) {
		this.firstLink = firstLink;
	}

	public String getLastLink() {
		return lastLink;
	}

	public void setLastLink(String lastLink) {
		this.lastLink = lastLink;
	}

	public String getNextLink() {
		return nextLink;
	}

	public void setNextLink(String nextLink) {
		this.nextLink = nextLink;
	}

	public String getPreviousLink() {
		return previousLink;
	}

	public void setPreviousLink(String previousLink) {
		this.previousLink = previousLink;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(this.getClass().getName()).append(
				this.hashCode()).append("\n");

		buf.append("status:").append(status).append("\n");
		buf.append("error:").append(error).append("\n");
		buf.append("contentType:").append(contentType).append("\n");
		buf.append("location:").append(location).append("\n");
		buf.append("responseText:").append(responseText).append("\n");
		buf.append("firstLink:").append(firstLink).append("\n");
		buf.append("lastLink:").append(lastLink).append("\n");
		buf.append("nextLink:").append(nextLink).append("\n");
		buf.append("previousLink:").append(previousLink).append("\n");

		return buf.toString();
	}
}
