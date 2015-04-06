/**
 * 
 */
package com.bandwidth.sdk;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

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
	
	public RestResponse(final String text, final int status) {
		this.responseText = text;
		this.error = (status >= 400);
		this.status = status;
	}
	
	public static RestResponse createRestResponse(final HttpResponse httpResponse) {
		
		final RestResponse restResponse = new RestResponse();
		try {
			restResponse.setStatus(httpResponse.getStatusLine().getStatusCode());
	        final HttpEntity entity = httpResponse.getEntity();
	        String responseText = "";
	        if (entity != null) {
	            responseText = EntityUtils.toString(entity);
	        }
	        if (responseText.length() == 0) {
	            responseText = "{}";
	        }
	        
	        // TODO There are several more error conditions that should be handled. 
	        if (responseText.contains("access-denied")) {
	            restResponse.setError(true);
	        } else if (restResponse.getStatus() >= 400) {
	            restResponse.setError(true);
	        }
	        
	        restResponse.setResponseText(responseText);
	        for  (final Header header : httpResponse.getHeaders("Content-Type")) {
	        	restResponse.setContentType(header.getValue());
	        }
	        for (final Header header : httpResponse.getHeaders("Location")) {
	            restResponse.setLocation(header.getValue());
	        }
	        for (final Header header : httpResponse.getHeaders("Link")) {	        	
	        	restResponse.parseLinkHeader(header.getValue());
	        }
		}	
        catch (final IOException e) {
        	e.printStackTrace();
        }
		return restResponse;
	}
	
	/**
	 * This parses out the Link for the first, next and prev. Link header looks like this:
	 * 
	 * {@literal <https://api.catapult.inetwork.com/v1/users/u-id/applications?page=0&size=2>; rel="first",}
	 * {@literal <https://api.catapult.inetwork.com/v1/users/u-id/applications?page=0&size=2>; rel="previous",}
	 * {@literal <https://api.catapult.inetwork.com/v1/users/u-id/applications?page=2&size=2>; rel="next"}
	 * 
	 * 
	 * @param link the link.
	 */
	public void parseLinkHeader(final String link) {
		
		final String [] links = link.split(",");
		for (final String part : links) {
			final String[] segments = part.split(";");
			if (segments.length < 2)
				continue;
			String linkPart = segments[0].trim();
			if (!linkPart.startsWith("<") || !linkPart.endsWith(">")) {
			    continue;
			}
			linkPart = linkPart.substring(1, linkPart.length() - 1);
			for (int i = 1; i < segments.length; i++) {
				final String[] rel = segments[i].trim().split("="); 
				if (rel.length < 2 || !"rel".equals(rel[0]))
					continue;
				String relValue = rel[1];
				if (relValue.startsWith("\"") && relValue.endsWith("\"")) {
				    relValue = relValue.substring(1, relValue.length() - 1);
				}
				if ("first".equals(relValue)) {
				    firstLink = linkPart;
				}
				else if ("last".equals(relValue)) {
				    lastLink = linkPart;
				}
				else if ("next".equals(relValue)) {
				    nextLink = linkPart;
				}
				else if ("previous".equals(relValue)) {
				    previousLink = linkPart;
				}
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

	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	public boolean isJson() {
		return this.contentType.toLowerCase().contains("application/json");
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setResponseText(final String responseText) {
		this.responseText = responseText;
	}

	public void setError(final boolean error) {
		this.error = error;
	}

	public void setStatus(final int status) {
		this.status = status;
	}
	
	public String getFirstLink() {
		return firstLink;
	}

	public void setFirstLink(final String firstLink) {
		this.firstLink = firstLink;
	}

	public String getLastLink() {
		return lastLink;
	}

	public void setLastLink(final String lastLink) {
		this.lastLink = lastLink;
	}

	public String getNextLink() {
		return nextLink;
	}

	public void setNextLink(final String nextLink) {
		this.nextLink = nextLink;
	}

	public String getPreviousLink() {
		return previousLink;
	}

	public void setPreviousLink(final String previousLink) {
		this.previousLink = previousLink;
	}

	public String toString() {
	    return  new StringBuffer(this.getClass().getName()).append(
				this.hashCode()).append("\n")
			.append("status:").append(status).append("\n")
			.append("error:").append(error).append("\n")
			.append("contentType:").append(contentType).append("\n")
			.append("location:").append(location).append("\n")
			.append("responseText:").append(responseText).append("\n")
			.append("firstLink:").append(firstLink).append("\n")
			.append("lastLink:").append(lastLink).append("\n")
			.append("nextLink:").append(nextLink).append("\n")
			.append("previousLink:").append(previousLink).append("\n").toString();
	}
}